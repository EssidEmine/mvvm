package com.test.fdj.ui.screens.teams.mapper

import com.test.fdj.data.model.Team
import com.test.fdj.data.model.Teams
import com.test.fdj.ui.screens.teams.model.TeamUiModel
import org.junit.Assert.assertEquals
import org.junit.Test

class TeamsUiModelMapperImplTest {

    private var mapper = TeamsUiModelMapperImpl()

    @Test
    fun `map should return list of TeamUiModel`() {
        // Arrange
        val teams = Teams(
            content = listOf(
                Team(
                    idTeam = "1",
                    strTeam = "Team 1",
                    strTeamBadge = "Badge 1",
                    strDescriptionEN = "Description 1",
                    strTeamBanner = "Banner 1",
                    strCountry = "Country 1",
                    strLeague = "League 1"
                ),
                Team(
                    idTeam = "2",
                    strTeam = "Team 2",
                    strTeamBadge = "Badge 2",
                    strDescriptionEN = "Description 2",
                    strTeamBanner = "Banner 2",
                    strCountry = "Country 2",
                    strLeague = "League 2"
                )
            )
        )
        val expectedUiModels = listOf(
            TeamUiModel(
                imageUrl = "Badge 1",
                imageAccessibilityLabel = "Description 1"
            ),
            TeamUiModel(
                imageUrl = "Badge 2",
                imageAccessibilityLabel = "Description 2"
            )
        )
        // Act
        val result = mapper.map(teams)
        // Assert
        assertEquals(expectedUiModels, result)
    }

    @Test
    fun `map should return empty list when teams content is null`() {
        // Arrange
        val teams = Teams(content = null)
        val expectedUiModels = emptyList<TeamUiModel>()
        // Act
        val result = mapper.map(teams)
        // Assert
        assertEquals(expectedUiModels, result)
    }

    @Test
    fun `map should return empty list when teams content is empty`() {
        // Arrange
        val teams = Teams(content = emptyList())
        val expectedUiModels = emptyList<TeamUiModel>()
        // Act
        val result = mapper.map(teams)
        // Assert
        assertEquals(expectedUiModels, result)
    }
}
