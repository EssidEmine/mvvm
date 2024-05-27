package com.test.fdj.data.model

data class Teams(
    val content: List<Team>?
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
