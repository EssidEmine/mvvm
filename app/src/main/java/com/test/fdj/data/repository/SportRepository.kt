package com.test.fdj.data.repository

import com.test.fdj.data.model.Leagues
import com.test.fdj.data.model.Teams
import com.test.fdj.utils.Result
import kotlinx.coroutines.flow.Flow

interface SportRepository {

    suspend fun getAllLeagues(): Flow<Result<Leagues>>

    suspend fun getTeams(leagueName: String): Flow<Result<Teams>>
}
