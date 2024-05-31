package com.test.fdj.data.mapper

import com.test.fdj.data.model.LeagueDto
import com.test.fdj.data.model.LeaguesDto
import com.test.fdj.domain.models.League
import com.test.fdj.domain.models.Leagues
import com.test.fdj.utils.Result.Error
import com.test.fdj.utils.Result.Success
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import retrofit2.Response

class LeaguesImplMapperTest {

    private var mapper = LeaguesMapperImpl()

    @Test
    fun `map when Response IsSuccessful should Return Mapped Leagues`() {
        // Arrange
        val leagueDto = LeagueDto(
            strLeagueAlternate = "Alt Name",
            strLeague = "League Name",
            strSport = "Sport",
            idLeague = "123"
        )
        val leaguesDto = LeaguesDto(listOf(leagueDto))
        val response = Response.success(leaguesDto)
        val expectedLeagues = Leagues(
            listOf(
                League(
                    id = "123",
                    sport = "Sport",
                    name = "League Name",
                    strLeagueAlternate = "Alt Name"
                )
            )
        )
        val expectedResult = Success(expectedLeagues)
        // Assert
        assertEquals(mapper.map(response), expectedResult)
    }

    @Test
    fun `map when Response Is Error should Return Error Result`() {
        // Arrange
        val response = Response.error<LeaguesDto>(
            404,
            "Not Found".toResponseBody(null)
        )
        // Assert
        assertTrue(mapper.map(response) is Error<*>)
    }
}

