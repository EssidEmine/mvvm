package com.test.fdj.data.model

import androidx.annotation.Keep

@Keep
data class LeaguesDto(
    val leagues: List<LeagueDto>?
)

@Keep
data class LeagueDto(
    val strLeagueAlternate: String?,
    val strLeague: String,
    val strSport: String,
    val idLeague: String
)
