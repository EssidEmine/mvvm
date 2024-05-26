package com.test.fdj.data.repository

import com.test.fdj.data.model.Leagues
import com.test.fdj.data.model.Teams
import com.test.fdj.utils.Result
import kotlinx.coroutines.flow.Flow

interface SportRepository {

    fun getAllLeagues(): Flow<Result<Leagues>>

    fun getTeams(leagueName: String): Flow<Result<Teams>>
}
