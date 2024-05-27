package com.test.fdj.ui.screens.teams.mapper

import com.test.fdj.data.model.Teams
import com.test.fdj.ui.screens.teams.model.TeamUiModel
import javax.inject.Inject

class TeamsUiModelMapperImpl @Inject constructor() : TeamsUiModelMapper {

    override fun map(teams: Teams): List<TeamUiModel> {
        return teams.content?.map { team ->
            TeamUiModel(
                imageUrl = team.strTeamBadge,
                imageAccessibilityLabel = team.strDescriptionEN
            )
        } ?: emptyList()
    }
}
