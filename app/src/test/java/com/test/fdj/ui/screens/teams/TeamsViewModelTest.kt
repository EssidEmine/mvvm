package com.test.fdj.ui.screens.teams

import androidx.lifecycle.SavedStateHandle
import com.test.fdj.domain.models.Team
import com.test.fdj.domain.models.Teams
import com.test.fdj.domain.models.TeamsError
import com.test.fdj.domain.usecases.teams.GetTeamsUseCaseImpl
import com.test.fdj.ui.dispatchers.DispatcherProviderImpl
import com.test.fdj.ui.screens.teams.mapper.TeamsUiModelMapper
import com.test.fdj.ui.screens.teams.model.TeamUiModel
import com.test.fdj.ui.screens.teams.model.TeamsErrorUiModel
import com.test.fdj.ui.screens.teams.model.TeamsErrorUiModelType
import com.test.fdj.ui.screens.teams.model.TeamsUiModel
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
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

class TeamsViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var getTeamsUseCaseImpl: GetTeamsUseCaseImpl

    @Mock
    private lateinit var teamsUiModelMapper: TeamsUiModelMapper
    private val uiModelHandler = UiModelTestHandler(
        MutableStateFlow(TeamsUiModel())
    )

    @Mock
    private lateinit var uiModelHandlerFactory: UiModelHandlerFactory
    private lateinit var viewModel: TeamsViewModel
    private lateinit var closeable: AutoCloseable

    @Before
    fun setup() {
        closeable = MockitoAnnotations.openMocks(this)

        given(
            uiModelHandlerFactory.buildSavedStateUiStateHandler<TeamsUiModel>(
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
        // Arrange
        val givenTeamUiModel = TeamUiModel(
            imageUrl = "https://example.com/team_a.png",
            imageAccessibilityLabel = "Team A logo"
        )
        val givenUiModel = TeamsUiModel(
            teams = listOf(
                givenTeamUiModel
            ),
        )
        val leagueName = "league"
        val givenTeams = Teams(
            content = listOf(
                Team(
                    idTeam = "quisque",
                    strTeam = "Team A logo",
                    strTeamBadge = "https://example.com/team_a.png",
                    strDescriptionEN = null,
                    strTeamBanner = null,
                    strCountry = "Luxembourg",
                    strLeague = "oporteat"
                )
            )
        )

        given(savedStateHandle.get<String>("leagueName")).willReturn(leagueName)
        given(getTeamsUseCaseImpl.invoke(leagueName)).willReturn(
            flow {
                emit(
                    Result.Success(
                        givenTeams
                    )
                )
            })
        given(teamsUiModelMapper.map(givenTeams)).willReturn(listOf(givenTeamUiModel))
        // Act
        initViewModel()
        // Assert
        assertEquals(
            givenUiModel.copy(
                isLoading = false,
            ),
            uiModelHandler.lastValue
        )

        assertEquals(
            givenUiModel.copy(
                isLoading = false,
                teams = listOf(
                    TeamUiModel(
                        imageUrl = "https://example.com/team_a.png",
                        imageAccessibilityLabel = "Team A logo"
                    )
                )
            ),
            uiModelHandler.lastValue
        )
    }

    @Test
    fun `loadData - Unknown Error`() = runTest {
        // Arrange
        val errorMessage = "Unknown error"
        val leagueName = "league"
        given(savedStateHandle.get<String>("leagueName")).willReturn(leagueName)
        given(getTeamsUseCaseImpl.invoke(leagueName)).willReturn(flow {
            emit(
                Result.Error(
                    TeamsError.Unknown(errorMessage)
                )
            )
        })
        // Act
        initViewModel()
        // Assert
        assertEquals(
            TeamsUiModel(
                isLoading = false,
                error = TeamsErrorUiModel(
                    label = errorMessage,
                    type = TeamsErrorUiModelType.UNKNOWN
                )
            ),
            uiModelHandler.lastValue
        )
    }

    @Test
    fun `loadData - Network Error`() = runTest {
        // Arrange
        val errorMessage = "Network error"
        val leagueName = "league"
        given(savedStateHandle.get<String>("leagueName")).willReturn(leagueName)
        given(getTeamsUseCaseImpl.invoke(leagueName)).willReturn(flow {
            emit(
                Result.Error(
                    TeamsError.Network(errorMessage)
                )
            )
        })
        // Act
        initViewModel()
        // Assert
        assertEquals(
            TeamsUiModel(
                isLoading = false,
                error = TeamsErrorUiModel(
                    label = errorMessage,
                    type = TeamsErrorUiModelType.NETWORK
                )
            ),
            uiModelHandler.lastValue
        )
    }

    @Test
    fun `loadData - leagueName Null Error`() = runTest {
        // Arrange
        given(savedStateHandle.get<String>("leagueName")).willReturn(null)
        // Act
        initViewModel()
        // Assert
        assertEquals(
            TeamsUiModel(
                isLoading = false,
                error = TeamsErrorUiModel(
                    label = "leagueName null error",
                    type = TeamsErrorUiModelType.GENERIC
                )
            ),
            uiModelHandler.lastValue
        )
    }

    private fun initViewModel() {
        viewModel = TeamsViewModel(
            getTeamsUseCaseImpl = getTeamsUseCaseImpl,
            teamsUiModelMapper = teamsUiModelMapper,
            dispatcherProvider = DispatcherProviderImpl(
                main = coroutinesTestRule.testDispatcher,
                io = coroutinesTestRule.testDispatcher,
            ),
            savedStateHandle = savedStateHandle,
            uiModelHandlerFactory = uiModelHandlerFactory,
        )
    }
}
