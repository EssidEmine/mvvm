package com.test.fdj.data.repository

import com.test.fdj.domain.models.Leagues
import com.test.fdj.domain.models.LeaguesError
import com.test.fdj.domain.models.Teams
import com.test.fdj.domain.models.TeamsError
import com.test.fdj.utils.Result
import kotlinx.coroutines.flow.Flow

interface SportRepository {

    suspend fun getAllLeagues(): Flow<Result<Leagues, LeaguesError>>

    suspend fun getTeams(leagueName: String): Flow<Result<Teams, TeamsError>>
}
