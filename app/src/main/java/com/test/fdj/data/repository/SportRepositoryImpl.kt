package com.test.fdj.data.repository

import com.test.fdj.data.mapper.LeaguesMapper
import com.test.fdj.data.mapper.TeamsMapper
import com.test.fdj.data.network.ApiService
import com.test.fdj.domain.models.Leagues
import com.test.fdj.domain.models.LeaguesError
import com.test.fdj.domain.models.Teams
import com.test.fdj.domain.models.TeamsError
import com.test.fdj.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SportRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val teamsMapper: TeamsMapper,
    private val leaguesMapper: LeaguesMapper,
) : SportRepository {

    override suspend fun getAllLeagues(): Flow<Result<Leagues, LeaguesError>> {
        return try {
            val leagues = apiService.getAllLeagues()
            flow { emit(leaguesMapper.map(leagues)) }
        } catch (e: Exception) {
            flow { emit(Result.Error(LeaguesError.Unknown(e.message.toString()))) }
        }
    }

    override suspend fun getTeams(leagueName: String): Flow<Result<Teams, TeamsError>> {
        return try {
            val teams = apiService.getTeams(leagueName)
            flow { emit(teamsMapper.map(teams)) }
        } catch (e: Exception) {
            flow { emit(Result.Error(TeamsError.Unknown(e.message.toString()))) }
        }
    }
}
