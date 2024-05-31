package com.test.fdj.ui.screens.leagues.mapper

import com.test.fdj.domain.models.Leagues
import com.test.fdj.ui.screens.leagues.model.LeagueUiModel

interface LeaguesUiModelMapper {
    fun map(leagues: Leagues): List<LeagueUiModel>
}
