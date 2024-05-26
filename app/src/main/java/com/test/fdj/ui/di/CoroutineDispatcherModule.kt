package com.test.fdj.ui.di

import com.test.fdj.ui.dispatchers.DispatcherProvider
import com.test.fdj.ui.dispatchers.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class CoroutineDispatcherModule {

    @Provides
    fun providesDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl(
            main = Dispatchers.Main,
            io = Dispatchers.IO,
        )
    }
}
