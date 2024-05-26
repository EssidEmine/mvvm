package com.test.fdj.domain.usecases.leagues

import com.test.fdj.data.model.Leagues
import com.test.fdj.utils.Result
import kotlinx.coroutines.flow.Flow

interface GetLeaguesUseCase {

    operator fun invoke(): Flow<Result<Leagues>>
}
