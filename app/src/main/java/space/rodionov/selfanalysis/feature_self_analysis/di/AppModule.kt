package space.rodionov.selfanalysis.feature_self_analysis.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import space.rodionov.selfanalysis.feature_self_analysis.data.local.AnalysisDao
import space.rodionov.selfanalysis.feature_self_analysis.data.local.AnalysisDatabase
import space.rodionov.selfanalysis.feature_self_analysis.data.preferences.PrefStore
import space.rodionov.selfanalysis.feature_self_analysis.data.preferences.PrefStoreJetPack
import space.rodionov.selfanalysis.feature_self_analysis.data.repository.AnalysisRepoImpl
import space.rodionov.selfanalysis.feature_self_analysis.data.repository.PrefRepoImpl
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.AnalysisRepo
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.PrefRepo
import space.rodionov.selfanalysis.feature_self_analysis.domain.util.ListToStringConverter
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePrefRepo(prefStore: PrefStore): PrefRepo = PrefRepoImpl(prefStore)

    @Provides
    @Singleton
    fun provideAnalysisRepo(analysisDao: AnalysisDao/*, converter: ListToStringConverter*/): AnalysisRepo =
        AnalysisRepoImpl(analysisDao/*, converter*/)

//    @Provides
//    @Singleton
//    fun provideAnalysisRepo(): AnalysisRepo =
//        AnalysisRepoFakeImpl()

    @Provides
    @Singleton
    fun providePrefStore(app: Application): PrefStore = PrefStoreJetPack(app)

//    @Provides
//    @Singleton
//    fun provideListToStringConverter(): ListToStringConverter = ListToStringConverterImpl()

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: AnalysisDatabase.Callback
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