package com.test.fdj.ui.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}
