package com.test.fdj.ui.screens.teams

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.fdj.domain.usecases.teams.GetTeamsUseCaseImpl
import com.test.fdj.ui.dispatchers.DispatcherProvider
import com.test.fdj.ui.screens.teams.mapper.TeamsUiModelMapper
import com.test.fdj.ui.screens.teams.model.TeamsErrorUiModel
import com.test.fdj.ui.screens.teams.model.TeamsUiModel
import com.test.fdj.ui.statehandlers.UiModelHandlerFactory
import com.test.fdj.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val getTeamsUseCaseImpl: GetTeamsUseCaseImpl,
    private val teamsUiModelMapper: TeamsUiModelMapper,
    dispatcherProvider: DispatcherProvider,
    uiModelHandlerFactory: UiModelHandlerFactory,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val uiModelHandler = uiModelHandlerFactory.buildSavedStateUiStateHandler(
        savedStateHandle = savedStateHandle,
        defaultUiModel = TeamsUiModel(),
    )
    private val leagueName: String? = savedStateHandle.get<String>("leagueName")
    val uiModelFlow get() = uiModelHandler.uiModelFlow

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            if (leagueName != null) {
                uiModelHandler.updateUiModel { uiModel ->
                    uiModel.copy(
                        isLoading = true
                    )
                }
                getTeamsUseCaseImpl.invoke(leagueName)
                    .collect {
                        when (val result = it) {
                            is Result.Error<*> -> {
                                uiModelHandler.updateUiModel { uiModel ->
                                    uiModel.copy(
                                        isLoading = false,
                                        error = TeamsErrorUiModel(
                                            result.exception.message ?: "Unknown error"
                                        )
                                    )
                                }
                            }

                            is Result.Success -> {
                                uiModelHandler.updateUiModel { uiModel ->
                                    uiModel.copy(
                                        isLoading = false,
                                        teams = teamsUiModelMapper.map(result.data)
                                    )
                                }
                            }
                        }
                    }
            } else {
                uiModelHandler.updateUiModel { uiModel ->
                    uiModel.copy(
                        isLoading = false,
                        error = TeamsErrorUiModel(
                            "leagueName null error"
                        )
                    )
                }
            }
        }
    }
}
