package com.potaninpm.feature_application_preview.domain.usecase

import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_application_preview.domain.repository.SubmitApplicationRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class SubmitLoanUseCaseTest {

    private val repository: SubmitApplicationRepository = mockk()
    private val useCase = SubmitLoanUseCase(repository)

    @Test
    fun `invoke success`() = runBlocking {
        // GIVEN
        val app = mockk<LoanApplication>()
        coEvery { repository.submitApplication(app) } returns Result.success(Unit)

        // WHEN
        val result = useCase.invoke(app)

        // THEN
        assertTrue(result.isSuccess)
    }

    @Test
    fun `invoke failure`() = runBlocking {
        // GIVEN
        val app = mockk<LoanApplication>()
        val exception = Exception("Network error")
        coEvery { repository.submitApplication(app) } returns Result.failure(exception)

        // WHEN
        val result = useCase.invoke(app)

        // THEN
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
