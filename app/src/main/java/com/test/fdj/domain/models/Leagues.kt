package com.test.fdj.domain.models

data class Leagues(
    val content: List<League>? = null
)

data class League(
    val id: String,
    val sport: String,
    val name: String,
    val strLeagueAlternate: String?
)

sealed class LeaguesError {
    data class Unknown(
        val error: String,
    ) : LeaguesError()

    data class Network(
        val error: String,
    ) : LeaguesError()
}
