package com.test.fdj.domain.models

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

sealed class TeamsError {
    data class Unknown(
        val error: String,
    ) : TeamsError()

    data class Network(
        val error: String,
    ) : TeamsError()
}
