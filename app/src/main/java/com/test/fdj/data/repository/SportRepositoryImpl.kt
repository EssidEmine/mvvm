package com.test.fdj.data.repository

import com.test.fdj.data.mapper.LeaguesMapper
import com.test.fdj.data.mapper.TeamsMapper
import com.test.fdj.data.network.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SportRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val teamsMapper: TeamsMapper,
    private val leaguesMapper: LeaguesMapper,
) : SportRepository {

    override fun getAllLeagues() = flow {
        emit(leaguesMapper.map(apiService.getAllLeagues()))
    }

    override fun getTeams(leagueName: String) =
        flow { emit(teamsMapper.map(apiService.getTeams(leagueName))) }
}
