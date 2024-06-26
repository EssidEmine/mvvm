package com.test.fdj.domain.usecases.teams

import com.test.fdj.data.repository.SportRepository
import com.test.fdj.domain.models.Teams
import com.test.fdj.domain.models.TeamsError
import com.test.fdj.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTeamsUseCaseImpl @Inject constructor(
    private val sportRepository: SportRepository
) {

    suspend fun invoke(param: String): Flow<Result<Teams, TeamsError>> {
        return flow {
            sportRepository.getTeams(param)
                .collect { result ->
                    when (result) {
                        is Result.Error<TeamsError> -> {
                            emit(result)
                        }

                        is Result.Success -> {
                            val sortedFilteredTeams = result.data.content?.sortedByDescending {
                                it.strTeam
                            }?.filterIndexed { index, _ ->
                                index % 2 == 0
                            }

                            emit(Result.Success(Teams(content = sortedFilteredTeams)))
                        }
                    }
                }
        }
    }
}
