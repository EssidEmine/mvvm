package com.test.fdj.ui.screens.leagues

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.fdj.domain.models.Leagues
import com.test.fdj.domain.models.LeaguesError
import com.test.fdj.domain.usecases.leagues.GetLeaguesUseCaseImpl
import com.test.fdj.ui.dispatchers.DispatcherProvider
import com.test.fdj.ui.screens.leagues.mapper.LeaguesUiModelMapper
import com.test.fdj.ui.screens.leagues.model.LeaguesErrorUiModel
import com.test.fdj.ui.screens.leagues.model.LeaguesNavigation
import com.test.fdj.ui.screens.leagues.model.LeaguesUiModel
import com.test.fdj.ui.statehandlers.UiModelHandlerFactory
import com.test.fdj.utils.Result
import com.test.fdj.utils.filterByText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaguesViewModel @Inject constructor(
    private val getLeaguesUseCase: GetLeaguesUseCaseImpl,
    private val dispatcherProvider: DispatcherProvider,
    private val leaguesUiModelMapper: LeaguesUiModelMapper,
    uiModelHandlerFactory: UiModelHandlerFactory,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val uiModelHandler = uiModelHandlerFactory.buildSavedStateUiStateHandler(
        savedStateHandle = savedStateHandle,
        defaultUiModel = LeaguesUiModel(),
    )
    val uiModelFlow get() = uiModelHandler.uiModelFlow
    var leagues = Leagues(emptyList())

    init {
        viewModelScope.launch(dispatcherProvider.io) {

            uiModelHandler.updateUiModel { uiModel ->
                uiModel.copy(
                    isLoading = true
                )
            }
            getLeaguesUseCase()
                .collect {
                    when (val result = it) {
                        is Result.Error<LeaguesError> -> {
                            handleErrorType(result.error)
                        }

                        is Result.Success -> {
                            leagues = result.data
                            uiModelHandler.updateUiModel { uiModel ->
                                uiModel.copy(
                                    leagues = leaguesUiModelMapper.map(leagues),
                                    isLoading = false,
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun handleErrorType(error: LeaguesError) {
        viewModelScope.launch(dispatcherProvider.io) {
            when (error) {
                is LeaguesError.Network -> {
                    //TODO EMINE SHOOW NETWORK ERROR UI
                    uiModelHandler.updateUiModel { uiModel ->
                        uiModel.copy(
                            isLoading = false,
                            error = LeaguesErrorUiModel(
                                error.error
                            )
                        )
                    }
                }

                is LeaguesError.Unknown -> {
                    uiModelHandler.updateUiModel { uiModel ->
                        uiModel.copy(
                            isLoading = false,
                            error = LeaguesErrorUiModel(
                                error.error,
                            )
                        )
                    }
                }
            }
        }
    }

    fun onSearchTextChange(text: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            val filteredLeagues = leagues.content?.filterByText(text) { league ->
                league.name
            } ?: leagues.content

            filteredLeagues?.let {
                uiModelHandler.updateUiModel { uiModel ->
                    uiModel.copy(
                        leagues = leaguesUiModelMapper.map(
                            Leagues(filteredLeagues)
                        )
                    )
                }
            }
        }
    }

    fun onToggleSearch(isActive: Boolean) {
        viewModelScope.launch(dispatcherProvider.io) {
            uiModelHandler.updateUiModel { uiModel ->
                uiModel.copy(
                    isSearching = isActive,
                )
            }
        }
    }

    fun showTeams(teamName: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            uiModelHandler.updateUiModel { uiModel ->
                uiModel.copy(
                    navigation = LeaguesNavigation.ShowTeams(teamName)
                )
            }
        }
    }

    fun resetNavigation() {
        viewModelScope.launch(dispatcherProvider.io) {
            uiModelHandler.updateUiModel { uiModel ->
                uiModel.copy(
                    navigation = LeaguesNavigation.NONE
                )
            }
        }
    }
}
