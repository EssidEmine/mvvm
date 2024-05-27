package com.test.fdj.data.repository

import com.test.fdj.data.mapper.LeaguesMapper
import com.test.fdj.data.mapper.TeamsMapper
import com.test.fdj.data.model.Leagues
import com.test.fdj.data.model.LeaguesDto
import com.test.fdj.data.model.Teams
import com.test.fdj.data.model.TeamsDto
import com.test.fdj.data.network.ApiService
import com.test.fdj.utils.Result
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class SportRepositoryImplTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var teamsMapper: TeamsMapper

    @Mock
    private lateinit var leaguesMapper: LeaguesMapper
    private lateinit var sportRepository: SportRepositoryImpl

    @Before
    fun setUp() {
        sportRepository = SportRepositoryImpl(apiService, teamsMapper, leaguesMapper)
    }

    @Test
    fun `getAllLeagues Success should return Result Success of leagues`() = runTest {
        // Arrange
        val leaguesDto = LeaguesDto(emptyList())
        val response = Response.success(leaguesDto)
        val expectedLeagues = Leagues(emptyList())
        val expectedResult = Result.Success(expectedLeagues)

        given(apiService.getAllLeagues()).willReturn(response)
        given(leaguesMapper.map(response)).willReturn(expectedResult)
        // Act
        val result = sportRepository.getAllLeagues().first()
        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getTeams Success should return Result Success of teams`() = runTest {
        // Arrange
        val leagueName = "some league"
        val teamsDto = TeamsDto(emptyList())
        val response = Response.success(teamsDto)
        val expectedTeams = Teams(emptyList())
        val expectedResult = Result.Success(expectedTeams)

        given(apiService.getTeams(leagueName)).willReturn(response)
        given(teamsMapper.map(response)).willReturn(expectedResult)
        // Act
        val result = sportRepository.getTeams(leagueName).first()
        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getAllLeagues Error should return Result Error`() = runTest {
        // Arrange
        val errorMessage = "Not Found"
        val response = Response.error<LeaguesDto>(
            404,
            errorMessage.toResponseBody(null)
        )
        val expectedResult = Result.Error<Leagues>(Exception(errorMessage))

        given(apiService.getAllLeagues()).willReturn(response)
        given(leaguesMapper.map(response)).willReturn(expectedResult)
        // Act
        val result = sportRepository.getAllLeagues().first()
        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getTeams Error should return Result Error`() = runTest {
        // Arrange
        val leagueName = "some league"
        val errorMessage = "Not Found"
        val response = Response.error<TeamsDto>(
            404,
            errorMessage.toResponseBody(null)
        )
        val expectedResult = Result.Error<Teams>(Exception(errorMessage))

        given(apiService.getTeams(leagueName)).willReturn(response)
        given(teamsMapper.map(response)).willReturn(expectedResult)
        // Act
        val result = sportRepository.getTeams(leagueName).first()
        // Assert
        assertEquals(expectedResult, result)
    }
}
