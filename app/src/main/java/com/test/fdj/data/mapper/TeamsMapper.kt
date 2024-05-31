package com.test.fdj.data.mapper

import com.test.fdj.data.model.TeamsDto
import com.test.fdj.domain.models.Teams
import com.test.fdj.domain.models.TeamsError
import com.test.fdj.utils.Result
import retrofit2.Response

interface TeamsMapper {

    fun map(response: Response<TeamsDto>): Result<Teams, TeamsError>
}
