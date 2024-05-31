package com.test.fdj.data.mapper

import com.test.fdj.data.model.TeamsDto
import com.test.fdj.domain.models.Team
import com.test.fdj.domain.models.Teams
import com.test.fdj.domain.models.TeamsError
import com.test.fdj.utils.Result
import retrofit2.Response
import javax.inject.Inject

class TeamsMapperImpl @Inject constructor() : TeamsMapper {

    override fun map(response: Response<TeamsDto>): Result<Teams, TeamsError> {
        return if (response.isSuccessful) {
            Result.Success(
                Teams(content = response.body()?.teams?.map {
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
            Result.Error(TeamsError.Network(response.message()))
        }
    }
}
