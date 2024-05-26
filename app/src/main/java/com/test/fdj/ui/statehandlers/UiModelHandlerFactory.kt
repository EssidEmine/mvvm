package com.test.fdj.ui.statehandlers

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.test.fdj.ui.dispatchers.DispatcherProvider

class UiModelHandlerFactory(
    private val dispatcherProvider: DispatcherProvider,
) {

    fun <T : Parcelable> buildSavedStateUiStateHandler(
        savedStateHandle: SavedStateHandle,
        defaultUiModel: T,
    ): UiModelHandler<T> = UiModelSavedStateHandler(
        dispatcherProvider = dispatcherProvider,
        savedStateHandle = savedStateHandle,
        defaultUiModel = defaultUiModel,
    )

    fun <T : Parcelable> buildStandardUiStateHandler(
        defaultUiModel: T,
    ): UiModelHandler<T> = UiModelStandardHandler(
        defaultUiModel = defaultUiModel,
    )
}
