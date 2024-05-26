package com.test.fdj.data.network

import com.test.fdj.data.model.LeaguesDto
import com.test.fdj.data.model.TeamsDto
import retrofit2.Response
import javax.inject.Inject

class ApiService @Inject constructor(
    private val endpointApi: EndpointApi
) {

    suspend fun getTeams(leagueName: String): Response<TeamsDto> {
        return endpointApi.getTeams(leagueName)
    }

    suspend fun getAllLeagues(): Response<LeaguesDto> {
        return endpointApi.getAllLeagues()
    }
}
