package com.test.fdj.data.mapper

import com.test.fdj.data.model.LeaguesDto
import com.test.fdj.domain.models.Leagues
import com.test.fdj.domain.models.LeaguesError
import com.test.fdj.utils.Result
import retrofit2.Response

interface LeaguesMapper {

    fun map(response: Response<LeaguesDto>): Result<Leagues, LeaguesError>
}
