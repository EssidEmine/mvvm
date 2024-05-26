package com.test.fdj.ui.screens.leagues.mapper

import com.test.fdj.data.model.Leagues
import com.test.fdj.ui.screens.leagues.model.LeagueUiModel
import javax.inject.Inject

class LeaguesUiModelMapperImpl @Inject constructor() : LeaguesUiModelMapper {
    override fun map(leagues: Leagues): List<LeagueUiModel> {
        return leagues.content?.map { league ->
            LeagueUiModel(
                name = league.name,
            )
        } ?: emptyList()
    }
}
