package com.test.fdj.data.mapper

import com.test.fdj.data.model.Team
import com.test.fdj.data.model.Teams
import com.test.fdj.data.model.TeamsDto
import com.test.fdj.utils.Result
import retrofit2.Response
import javax.inject.Inject

class TeamsMapperImpl @Inject constructor() : TeamsMapper {

    override fun map(response: Response<TeamsDto>): Result<Teams> {
        return if (response.isSuccessful) {
            Result.Success(
                Teams(teams = response.body()?.teams?.map {
                    Team(
                        idTeam = it.idTeam,
                        strTeam = it.strTeam,
                        strTeamBadge = it.strTeamBadge,
                        strDescriptionEN = it.strDescriptionEN,
                        strTeamBanner = it.strTeamBanner,
                        strCountry = it.strCountry,
                        strLeague = it.strLeague,
                    )
                })
            )
        } else {
            Result.Error<String>(Exception(response.message()))
        }
    }
}
