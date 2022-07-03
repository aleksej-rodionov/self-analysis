package space.rodionov.selfanalysis.feature_self_analysis.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.AnalysisManager
import space.rodionov.selfanalysis.feature_self_analysis.domain.manager.PrefManager
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.AnalysisRepo
import space.rodionov.selfanalysis.feature_self_analysis.domain.repository.PrefRepo
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ManagerModule {

    @Provides
    @ViewModelScoped
    fun providePrefManager(prefRepo: PrefRepo): PrefManager {
        return PrefManager(prefRepo)
    }

    @Provides
    @ViewModelScoped
    fun provideAnalysisManager(analysisRepo: AnalysisRepo): AnalysisManager {
        return AnalysisManager(analysisRepo)
    }
}