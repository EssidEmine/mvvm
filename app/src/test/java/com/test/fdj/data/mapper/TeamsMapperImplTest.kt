package com.test.fdj.data.mapper

import com.test.fdj.data.model.Team
import com.test.fdj.data.model.TeamDto
import com.test.fdj.data.model.Teams
import com.test.fdj.data.model.TeamsDto
import com.test.fdj.utils.Result.Error
import com.test.fdj.utils.Result.Success
import okhttp3.ResponseBody
import org.junit.Assert
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
            ResponseBody.create(null, "Not Found")
        )
        // Act
        val result = mapper.map(response)
        // Assert
        Assert.assertTrue(result is Error<*>)
    }
}
