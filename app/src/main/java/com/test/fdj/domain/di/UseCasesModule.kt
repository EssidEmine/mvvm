package com.test.fdj.domain.di

import com.test.fdj.data.repository.SportRepository
import com.test.fdj.domain.usecases.leagues.GetLeaguesUseCaseImpl
import com.test.fdj.domain.usecases.teams.GetTeamsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    fun provideGetLeaguesUseCase(
        sportRepository: SportRepository
    ): GetLeaguesUseCaseImpl {
        return GetLeaguesUseCaseImpl(sportRepository)
    }

    @Provides
    fun provideGetTeamsUseCase(
        sportRepository: SportRepository
    ): GetTeamsUseCaseImpl {
        return GetTeamsUseCaseImpl(sportRepository)
    }
}
