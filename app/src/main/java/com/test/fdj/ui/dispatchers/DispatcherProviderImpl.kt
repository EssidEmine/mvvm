package com.test.fdj.ui.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

class DispatcherProviderImpl(
    override val main: CoroutineDispatcher,
    override val io: CoroutineDispatcher,
) : DispatcherProvider
