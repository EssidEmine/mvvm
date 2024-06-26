package com.test.fdj.data.mapper

import com.test.fdj.data.model.TeamDto
import com.test.fdj.data.model.TeamsDto
import com.test.fdj.domain.models.Team
import com.test.fdj.domain.models.Teams
import com.test.fdj.domain.models.TeamsError
import com.test.fdj.utils.Result
import com.test.fdj.utils.Result.Success
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import retrofit2.Response

class TeamsMapperImplTest {

    private val mapper = TeamsMapperImpl()

    @Test
    fun `map when Response IsSuccessful should Return Mapped Teams`() {
        // Arrange
        val teamDto = TeamDto(
            "123",
            "Team Name",
            "Badge URL",
            "Description EN",
            "Banner URL",
            "Country",
            "League"
        )
        val teamsDto = TeamsDto(listOf(teamDto))
        val response = Response.success(teamsDto)
        val expectedTeams = Teams(
            listOf(
                Team(
                    "123",
                    "Team Name",
                    "Badge URL",
                    "Description EN",
                    "Banner URL",
                    "Country",
                    "League"
                )
            )
        )
        val expectedResult = Success(expectedTeams)
        // Act
        val result = mapper.map(response)
        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `map when Response IsError should Return Error Result`() {
        // Arrange
        val response = Response.error<TeamsDto>(
            404,
            "Not Found".toResponseBody(null)
        )
        // Act
        val result = mapper.map(response)
        // Assert
        assertEquals(Result.Error(TeamsError.Network(response.message())), mapper.map(response))
    }
}
