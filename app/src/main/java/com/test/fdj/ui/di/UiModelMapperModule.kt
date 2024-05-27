package com.test.fdj.ui.di

import com.test.fdj.ui.screens.leagues.mapper.LeaguesUiModelMapper
import com.test.fdj.ui.screens.leagues.mapper.LeaguesUiModelMapperImpl
import com.test.fdj.ui.screens.teams.mapper.TeamsUiModelMapper
import com.test.fdj.ui.screens.teams.mapper.TeamsUiModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UiModelMapperModule {

    @Binds
    abstract fun bindLeaguesUiModelMapper(impl: LeaguesUiModelMapperImpl): LeaguesUiModelMapper

    @Binds
    abstract fun bindTeamsUiModelMapper(impl: TeamsUiModelMapperImpl): TeamsUiModelMapper
}
