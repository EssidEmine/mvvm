package com.test.fdj.domain.usecases.leagues

import com.test.fdj.data.repository.SportRepository
import javax.inject.Inject

class GetLeaguesUseCaseImpl @Inject constructor(
    private val sportRepository: SportRepository
) : GetLeaguesUseCase {

    override fun invoke() = sportRepository.getAllLeagues()
}
