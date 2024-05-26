package com.test.fdj.data.mapper

import com.test.fdj.data.model.League
import com.test.fdj.data.model.Leagues
import com.test.fdj.data.model.LeaguesDto
import com.test.fdj.utils.Result
import retrofit2.Response
import javax.inject.Inject

class LeaguesMapperImpl @Inject constructor() : LeaguesMapper {

    override fun map(response: Response<LeaguesDto>): Result<Leagues> {
        return if (response.isSuccessful) {
            Result.Success(
                Leagues(
                    content = response.body()?.leagues?.map {
                        League(
                            id = it.idLeague,
                            sport = it.strSport,
                            name = it.strLeague,
                            strLeagueAlternate = it.strLeagueAlternate
                        )
                    }
                )
            )
        } else {
            Result.Error<String>(Exception(response.message()))
        }
    }
}
