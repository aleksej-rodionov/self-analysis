package space.rodionov.selfanalysis.feature_self_analysis.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import space.rodionov.selfanalysis.data.NoteDatabase
import space.rodionov.selfanalysis.feature_self_analysis.data.local.AnalysisDatabase
import space.rodionov.selfanalysis.feature_self_analysis.data.preferences.PrefStore
import space.rodionov.selfanalysis.feature_self_analysis.data.preferences.PrefStoreJetPack
import space.rodionov.selfanalysis.feature_self_analysis.data.repository.AnalysisRepoImpl
import space.rodionov.selfanalysis.feature_self_analysis.data.repository.PrefRepoImpl
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.AnalysisManager
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.PrefManager
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.AnalysisRepo
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.PrefRepo
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePrefManager(prefRepo: PrefRepo): PrefManager {
        return PrefManager(prefRepo)
    }

    @Provides
    @Singleton
    fun provideAnalysisManager(analysisRepo: AnalysisRepo): AnalysisManager {
        return AnalysisManager(analysisRepo)
    }

    @Provides
    @Singleton
    fun providePrefRepo(prefStore: PrefStore): PrefRepo = PrefRepoImpl(prefStore)

    @Provides
    @Singleton
    fun provideAnalysisRepo(analysisDb: AnalysisDatabase): AnalysisRepo =
        AnalysisRepoImpl(analysisDb)

    @Provides
    @Singleton
    fun providePrefStore(app: Application): PrefStore = PrefStoreJetPack(app)

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: NoteDatabase.Callback
    ) = Room.databaseBuilder(app, AnalysisDatabase::class.java, "analysis_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideAnalysisDao(db: AnalysisDatabase) = db.analysisDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope