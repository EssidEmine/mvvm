package com.test.fdj.ui.screens.teams.mapper

import com.test.fdj.domain.models.Teams
import com.test.fdj.ui.screens.teams.model.TeamUiModel

interface TeamsUiModelMapper {

    fun map(teams: Teams): List<TeamUiModel>
}
