package com.test.fdj.domain.di

import com.test.fdj.domain.usecases.leagues.GetLeaguesUseCase
import com.test.fdj.domain.usecases.leagues.GetLeaguesUseCaseImpl
import com.test.fdj.domain.usecases.teams.GetTeamsUseCase
import com.test.fdj.domain.usecases.teams.GetTeamsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {

    @Binds
    abstract fun bindGetLeaguesUseCase(impl: GetLeaguesUseCaseImpl): GetLeaguesUseCase

    @Binds
    abstract fun bindGetTeamsUseCase(impl: GetTeamsUseCaseImpl): GetTeamsUseCase
}
