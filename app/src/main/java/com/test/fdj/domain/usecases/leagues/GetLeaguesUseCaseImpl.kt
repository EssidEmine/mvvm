package com.test.fdj.domain.usecases.leagues

import com.test.fdj.data.repository.SportRepository
import javax.inject.Inject

class GetLeaguesUseCaseImpl @Inject constructor(
    private val sportRepository: SportRepository
) {

    suspend operator fun invoke() = sportRepository.getAllLeagues()
}
