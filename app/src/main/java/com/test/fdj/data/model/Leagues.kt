package com.test.fdj.data.model

data class Leagues(
    val content: List<League>? = null
)

data class League(
    val id: String,
    val sport: String,
    val name: String,
    val strLeagueAlternate: String?
)
