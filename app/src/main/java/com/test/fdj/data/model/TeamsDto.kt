package com.test.fdj.data.model

import androidx.annotation.Keep

@Keep
data class TeamsDto(
    val teams: List<TeamDto>?
)

@Keep
data class TeamDto(
    val idTeam: String,
    val strTeam: String,
    val strTeamBadge: String,
    val strDescriptionEN: String?,
    val strTeamBanner: String?,
    val strCountry: String,
    val strLeague: String
)
