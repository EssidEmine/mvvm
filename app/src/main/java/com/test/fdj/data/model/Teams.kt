package com.test.fdj.data.model

data class Teams(
    val teams: List<Team>?
)

data class Team(
    val idTeam: String,
    val strTeam: String,
    val strTeamBadge: String,
    val strDescriptionEN: String?,
    val strTeamBanner: String?,
    val strCountry: String,
    val strLeague: String
)
