package com.test.fdj.domain.usecases.teams

import com.test.fdj.data.repository.SportRepository
import com.test.fdj.domain.models.Team
import com.test.fdj.domain.models.Teams
import com.test.fdj.domain.models.TeamsError
import com.test.fdj.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTeamsUseCaseImplTest {

    @Mock
    private lateinit var sportRepository: SportRepository
    private lateinit var getTeamsUseCase: GetTeamsUseCaseImpl

    @Before
    fun setUp() {
        getTeamsUseCase = GetTeamsUseCaseImpl(sportRepository)
    }

    @Suppress("LongMethod")
    @Test
    fun `invoke should return sorted and filtered teams when result is success`() = runTest {
        // Arrange
        val teams = Teams(
            content = listOf(
                Team(
                    "1",
                    "Team A",
                    "Badge A",
                    "Description A",
                    "Banner A",
                    "Country A",
                    "League A"
                ),
                Team(
                    "2",
                    "Team B",
                    "Badge B",
                    "Description B",
                    "Banner B",
                    "Country B",
                    "League B"
                ),
                Team(
                    "3",
                    "Team C",
                    "Badge C",
                    "Description C",
                    "Banner C",
                    "Country C",
                    "League C"
                ),
                Team(
                    "4",
                    "Team D",
                    "Badge D",
                    "Description D",
                    "Banner D",
                    "Country D",
                    "League D"
                ),
                Team(
                    "5",
                    "Team E",
                    "Badge E",
                    "Description E",
                    "Banner E",
                    "Country E",
                    "League E"
                ),
                Team(
                    "6",
                    "Team F",
                    "Badge F",
                    "Description F",
                    "Banner F",
                    "Country F",
                    "League F"
                ),
                Team(
                    "7",
                    "Team G",
                    "Badge G",
                    "Description G",
                    "Banner G",
                    "Country G",
                    "League G"
                ),
                Team(
                    "8",
                    "Team H",
                    "Badge H",
                    "Description H",
                    "Banner H",
                    "Country H",
                    "League H"
                )
            )
        )
        val expectedTeams = Teams(
            content = listOf(
                Team(
                    "8",
                    "Team H",
                    "Badge H",
                    "Description H",
                    "Banner H",
                    "Country H",
                    "League H"
                ),
                Team(
                    "6",
                    "Team F",
                    "Badge F",
                    "Description F",
                    "Banner F",
                    "Country F",
                    "League F"
                ),
                Team(
                    "4",
                    "Team D",
                    "Badge D",
                    "Description D",
                    "Banner D",
                    "Country D",
                    "League D"
                ),
                Team(
                    "2",
                    "Team B",
                    "Badge B",
                    "Description B",
                    "Banner B",
                    "Country B",
                    "League B"
                )
            )
        )
        val expectedResult = Result.Success(expectedTeams)

        given(sportRepository.getTeams("some league")).willReturn(
            flow {
                emit(Result.Success(teams))
            })
        // Act
        val result = getTeamsUseCase.invoke("some league").first()
        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `invoke should return error when result is Unknown error`() = runTest {
        // Arrange
        val givenLeague = "spanish league"
        val expectedResult = Result.Error(TeamsError.Unknown("Unknown"))

        given(sportRepository.getTeams(givenLeague)).willReturn(flow {
            emit(Result.Error(TeamsError.Unknown("Unknown")))
        })
        // Act
        val result = getTeamsUseCase.invoke("spanish league").first()
        // Assert
        assertEquals(expectedResult.toString(), result.toString())
    }

    @Test
    fun `invoke should return error when result is Network error`() = runTest {
        // Arrange
        val givenLeague = "spanish league"
        val expectedResult = Result.Error(TeamsError.Network("Network"))

        given(sportRepository.getTeams(givenLeague)).willReturn(flow {
            emit(Result.Error(TeamsError.Network("Network")))
        })
        // Act
        val result = getTeamsUseCase.invoke("spanish league").first()
        // Assert
        assertEquals(expectedResult.toString(), result.toString())
    }
}
