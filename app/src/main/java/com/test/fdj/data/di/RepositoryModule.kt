package com.test.fdj.data.di

import com.test.fdj.data.repository.SportRepository
import com.test.fdj.data.repository.SportRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSportRepository(impl: SportRepositoryImpl): SportRepository
}
