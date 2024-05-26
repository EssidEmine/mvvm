package com.test.fdj.data.network

import com.test.fdj.data.model.LeaguesDto
import com.test.fdj.data.model.TeamsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EndpointApi {

    @GET("all_leagues.php")
    suspend fun getAllLeagues(): Response<LeaguesDto>

    @GET("search_all_teams.php")
    suspend fun getTeams(
        @Query("l")
        leagueName: String
    ): Response<TeamsDto>
}
