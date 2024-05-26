package com.test.fdj.ui.di

import com.test.fdj.ui.dispatchers.DispatcherProvider
import com.test.fdj.ui.statehandlers.UiModelHandlerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModelHandlerFactoryModule {

    @Provides
    fun provideUiModelHandlerFactory(dispatcherProvider: DispatcherProvider): UiModelHandlerFactory {
        return UiModelHandlerFactory(dispatcherProvider)
    }
}
