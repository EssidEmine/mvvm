package com.test.fdj.data.repository

import com.test.fdj.data.mapper.LeaguesMapper
import com.test.fdj.data.mapper.TeamsMapper
import com.test.fdj.data.model.Leagues
import com.test.fdj.data.model.Teams
import com.test.fdj.data.network.ApiService
import com.test.fdj.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SportRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val teamsMapper: TeamsMapper,
    private val leaguesMapper: LeaguesMapper,
) : SportRepository {

    override suspend fun getAllLeagues(): Flow<Result<Leagues>> {
        val result = try {
            apiService.getAllLeagues()
        } catch (e: Exception) {
            // todo fine tune Exception
            null
        }
        return flow {
            result?.let {
                emit(leaguesMapper.map(result))
            } ?: emit(Result.Error<String>(Exception("Generic ApiService Error")))
        }

    }

    override suspend fun getTeams(leagueName: String): Flow<Result<Teams>> {
        val result = try {
            apiService.getTeams(leagueName)
        } catch (e: Exception) {
            // todo fine tune Exception
            null
        }
        return flow {
            result?.let {
                emit(teamsMapper.map(result))
            } ?: emit(Result.Error<String>(Exception("Generic ApiService Error")))
        }
    }
}
