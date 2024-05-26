package com.test.fdj.data.di

import com.test.fdj.data.mapper.LeaguesMapper
import com.test.fdj.data.mapper.LeaguesMapperImpl
import com.test.fdj.data.mapper.TeamsMapper
import com.test.fdj.data.mapper.TeamsMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    abstract fun bindLeaguesMapper(impl: LeaguesMapperImpl): LeaguesMapper

    @Binds
    abstract fun bindTeamsMapper(impl: TeamsMapperImpl): TeamsMapper
}
