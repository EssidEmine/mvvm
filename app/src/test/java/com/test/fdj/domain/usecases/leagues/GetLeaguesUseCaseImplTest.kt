package com.test.fdj.domain.usecases.leagues

import com.test.fdj.data.model.Leagues
import com.test.fdj.data.repository.SportRepository
import com.test.fdj.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetLeaguesUseCaseImplTest {

    @Mock
    private lateinit var sportRepository: SportRepository
    private lateinit var getLeaguesUseCase: GetLeaguesUseCaseImpl

    @Before
    fun setUp() {
        getLeaguesUseCase = GetLeaguesUseCaseImpl(sportRepository)
    }

    @Test
    fun `invoke should return leagues when repository return success `() = runTest {
        // Arrange
        val expectedLeagues = Leagues(emptyList())
        val expectedResult = Result.Success(expectedLeagues)

        given(sportRepository.getAllLeagues()).willReturn(flowOf(expectedResult))
        // Act
        val result = getLeaguesUseCase.invoke().first()
        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `invoke should return error when repository return error`() = runTest {
        // Arrange
        val expectedResult = Result.Error<Leagues>(Exception("Error"))

        given(sportRepository.getAllLeagues()).willReturn(flowOf(expectedResult))
        // Act
        val result = getLeaguesUseCase.invoke().first()
        // Assert
        assertEquals(expectedResult, result)
    }
}

