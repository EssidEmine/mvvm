package com.test.fdj.domain.usecases.teams

import com.test.fdj.data.model.Teams
import com.test.fdj.utils.Result
import kotlinx.coroutines.flow.Flow

interface GetTeamsUseCase {

    suspend operator fun invoke(param: String): Flow<Result<Teams>>
}
