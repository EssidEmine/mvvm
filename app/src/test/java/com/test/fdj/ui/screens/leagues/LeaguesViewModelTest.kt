package com.test.fdj.ui.screens.leagues

import androidx.lifecycle.SavedStateHandle
import com.test.fdj.domain.models.League
import com.test.fdj.domain.models.Leagues
import com.test.fdj.domain.models.LeaguesError
import com.test.fdj.domain.usecases.leagues.GetLeaguesUseCaseImpl
import com.test.fdj.ui.dispatchers.DispatcherProviderImpl
import com.test.fdj.ui.screens.leagues.mapper.LeaguesUiModelMapper
import com.test.fdj.ui.screens.leagues.model.LeagueUiModel
import com.test.fdj.ui.screens.leagues.model.LeaguesErrorUiModel
import com.test.fdj.ui.screens.leagues.model.LeaguesErrorUiModelType
import com.test.fdj.ui.screens.leagues.model.LeaguesNavigation
import com.test.fdj.ui.screens.leagues.model.LeaguesUiModel
import com.test.fdj.ui.statehandlers.UiModelHandlerFactory
import com.test.fdj.ui.statehandlers.UiModelTestHandler
import com.test.fdj.utils.CoroutinesTestRule
import com.test.fdj.utils.Result
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

class LeaguesViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var getLeaguesUseCase: GetLeaguesUseCaseImpl

    @Mock
    private lateinit var leaguesUiModelMapper: LeaguesUiModelMapper
    private val uiModelHandler = UiModelTestHandler(
        MutableStateFlow(LeaguesUiModel())
    )

    @Mock
    private lateinit var uiModelHandlerFactory: UiModelHandlerFactory
    private lateinit var viewModel: LeaguesViewModel
    private lateinit var closeable: AutoCloseable

    @Before
    fun setup() {
        closeable = MockitoAnnotations.openMocks(this)

        given(
            uiModelHandlerFactory.buildSavedStateUiStateHandler<LeaguesUiModel>(
                savedStateHandle = any(),
                defaultUiModel = any(),
            )
        ).willReturn(uiModelHandler)
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun `loadData - Success`() = runTest {
        //Arrange
        val givenLeagueUiModel = LeagueUiModel(
            name = "Madeline McClain"
        )
        val givenUiModel = LeaguesUiModel(
            leagues = listOf(
                givenLeagueUiModel
            ),
        )
        val givenLeagues = Leagues(
            content = listOf(
                League(
                    id = "homero",
                    sport = "te",
                    name = "Madeline McClain",
                    strLeagueAlternate = null
                )
            )
        )
        given(getLeaguesUseCase.invoke()).willReturn(flow { emit(Result.Success(givenLeagues)) })
        given(leaguesUiModelMapper.map(givenLeagues)).willReturn(listOf(givenLeagueUiModel))
        //Act
        initViewModel()
        //Assert
        assertEquals(
            givenUiModel.copy(
                isLoading = false,
            ),
            uiModelHandler.listOfValues[uiModelHandler.listOfValues.count() - 1]
        )

        assertEquals(
            givenUiModel.copy(
                isLoading = false,
                leagues = listOf(
                    LeagueUiModel("Madeline McClain")
                )
            ),
            uiModelHandler.lastValue
        )
    }

    @Test
    fun `loadData - Network Error`() = runTest {
        // Arrange
        val errorMessage = "Network error"
        given(getLeaguesUseCase.invoke()).willReturn(flow {
            emit(
                Result.Error(
                    LeaguesError.Network(errorMessage)
                )
            )
        })
        // Act
        initViewModel()
        // Assert
        assertEquals(
            LeaguesUiModel(
                isLoading = false,
                error = LeaguesErrorUiModel(
                    label = errorMessage,
                    type = LeaguesErrorUiModelType.NETWORK
                )
            ),
            uiModelHandler.lastValue
        )
    }

    @Test
    fun `loadData - Unknown Error`() = runTest {
        // Arrange
        val errorMessage = "Unknown error"
        given(getLeaguesUseCase.invoke()).willReturn(flow {
            emit(
                Result.Error(
                    LeaguesError.Unknown(errorMessage)
                )
            )
        })
        // Act
        initViewModel()
        // Assert
        assertEquals(
            LeaguesUiModel(
                isLoading = false,
                error = LeaguesErrorUiModel(
                    label = errorMessage,
                    type = LeaguesErrorUiModelType.UNKNOWN
                )
            ),
            uiModelHandler.lastValue
        )
    }

    @Test
    fun `onSearchTextChange - Filter Leagues`() = runTest {
        // Arrange
        val givenLeagues = Leagues(
            content = listOf(
                League(
                    id = "1",
                    sport = "Soccer",
                    name = "Premier League",
                    strLeagueAlternate = null
                ),
                League(
                    id = "2",
                    sport = "Basketball",
                    name = "NBA",
                    strLeagueAlternate = null
                )
            )
        )
        given(getLeaguesUseCase.invoke()).willReturn(flow { emit(Result.Success(givenLeagues)) })
        given(leaguesUiModelMapper.map(any())).willReturn(
            listOf(LeagueUiModel("Premier League"))
        )

        initViewModel()
        // Act
        viewModel.onSearchTextChange("Premier")
        // Assert
        assertEquals(
            LeaguesUiModel(
                leagues = listOf(LeagueUiModel("Premier League"))
            ),
            uiModelHandler.lastValue
        )
    }

    @Test
    fun `onToggleSearch - Activate Search`() = runTest {
        // Arrange
        given(getLeaguesUseCase.invoke()).willReturn(mock())
        given(leaguesUiModelMapper.map(any())).willReturn(mock())
        initViewModel()
        uiModelHandler.updateUiModel {
            it.copy(
                isLoading = false,
            )
        }
        //Act
        viewModel.onToggleSearch(true)
        // Assert
        assertEquals(
            LeaguesUiModel(
                isSearching = true
            ),
            uiModelHandler.lastValue
        )
    }

    @Test
    fun `onToggleSearch - Deactivate Search`() = runTest {
        // Arrange
        given(getLeaguesUseCase.invoke()).willReturn(mock())
        given(leaguesUiModelMapper.map(any())).willReturn(mock())
        initViewModel()
        uiModelHandler.updateUiModel {
            it.copy(
                isLoading = false,
            )
        }
        //Act
        viewModel.onToggleSearch(false)
        // Assert
        assertEquals(
            LeaguesUiModel(
                isSearching = false
            ),
            uiModelHandler.lastValue
        )
    }

    @Test
    fun `showTeams - Navigate to Teams`() = runTest {
        // Arrange
        given(getLeaguesUseCase.invoke()).willReturn(mock())
        given(leaguesUiModelMapper.map(any())).willReturn(mock())
        initViewModel()
        uiModelHandler.updateUiModel {
            it.copy(
                isLoading = false,
            )
        }
        //Act
        viewModel.showTeams("TeamA")
        // Assert
        assertEquals(
            LeaguesUiModel(
                navigation = LeaguesNavigation.ShowTeams("TeamA")
            ),
            uiModelHandler.lastValue
        )
    }

    @Test
    fun `resetNavigation - Reset Navigation`() = runTest {
        // Arrange
        given(getLeaguesUseCase.invoke()).willReturn(mock())
        given(leaguesUiModelMapper.map(any())).willReturn(mock())
        initViewModel()
        uiModelHandler.updateUiModel {
            it.copy(
                isLoading = false,
            )
        }
        //Act
        viewModel.resetNavigation()
        // Assert
        assertEquals(
            LeaguesUiModel(
                navigation = LeaguesNavigation.NONE
            ),
            uiModelHandler.lastValue
        )
    }

    private fun initViewModel() {
        viewModel = LeaguesViewModel(
            getLeaguesUseCase = getLeaguesUseCase,
            leaguesUiModelMapper = leaguesUiModelMapper,
            dispatcherProvider = DispatcherProviderImpl(
                main = coroutinesTestRule.testDispatcher,
                io = coroutinesTestRule.testDispatcher,
            ),
            savedStateHandle = savedStateHandle,
            uiModelHandlerFactory = uiModelHandlerFactory,
        )
    }
}

