package com.test.fdj.ui.screens.leagues.mapper

import com.test.fdj.data.model.League
import com.test.fdj.data.model.Leagues
import com.test.fdj.ui.screens.leagues.model.LeagueUiModel
import org.junit.Assert.assertEquals
import org.junit.Test

class LeaguesUiModelMapperImplTest {

    private val mapper = LeaguesUiModelMapperImpl()

    @Test
    fun `map should return list of LeagueUiModel`() {
        // Arrange
        val leagues = Leagues(
            content = listOf(
                League(
                    id = "1",
                    sport = "Sport 1",
                    name = "League 1",
                    strLeagueAlternate = "Alternate 1"
                ),
                League(
                    id = "2",
                    sport = "Sport 2",
                    name = "League 2",
                    strLeagueAlternate = "Alternate 2"
                )
            )
        )
        val expectedUiModels = listOf(
            LeagueUiModel(
                name = "League 1"
            ),
            LeagueUiModel(
                name = "League 2"
            )
        )
        // Act
        val result = mapper.map(leagues)
        // Assert
        assertEquals(expectedUiModels, result)
    }

    @Test
    fun `map should return empty list when leagues content is null`() {
        // Arrange
        val expectedUiModels = emptyList<LeagueUiModel>()
        // Act
        val result = mapper.map(Leagues(content = null))
        // Assert
        assertEquals(expectedUiModels, result)
    }

    @Test
    fun `map should return empty list when leagues content is empty`() {
        // Arrange
        val expectedUiModels = emptyList<LeagueUiModel>()
        // Act
        val result = mapper.map(Leagues(content = emptyList()))
        // Assert
        assertEquals(expectedUiModels, result)
    }
}
