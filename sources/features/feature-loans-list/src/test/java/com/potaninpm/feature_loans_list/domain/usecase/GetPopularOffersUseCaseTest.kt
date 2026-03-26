package com.potaninpm.feature_loans_list.domain.usecase

import com.potaninpm.feature_loans_list.domain.repository.LoanProductsRepository
import com.potaninpm.feature_loans_list.utils.LoanType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetPopularOffersUseCaseTest {

    private val repository: LoanProductsRepository = mockk()
    private val useCase = GetPopularOffersUseCase(repository)

    @Test
    fun `invoke delegates to repository getPopularOffers`() = runTest {
        val expected = listOf(mockk<LoanType>())
        coEvery { repository.getPopularOffers() } returns Result.success(expected)

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrThrow())
        coVerify(exactly = 1) { repository.getPopularOffers() }
    }

    @Test
    fun `invoke returns failure from repository`() = runTest {
        coEvery { repository.getPopularOffers() } returns Result.failure(RuntimeException("error"))

        val result = useCase()

        assertTrue(result.isFailure)
    }
}
