package com.potaninpm.feature_my_requests.domain.usecase

import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_my_requests.domain.repository.LoanApplicationRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class GetMyApplicationsUseCaseTest {

    private val repository: LoanApplicationRepository = mockk()
    private val useCase = GetMyApplicationsUseCase(repository)

    @Test
    fun `invoke with isAdmin equals false calls getMyApplications`() = runBlocking {
        // GIVEN
        val apps = listOf(mockk<LoanApplication>())
        coEvery { repository.getMyApplications() } returns apps

        // WHEN
        val result = useCase.invoke(isAdmin = false)

        // THEN
        assertTrue(result.isSuccess)
        assertEquals(apps, result.getOrNull())
    }

    @Test
    fun `invoke with isAdmin equals true calls getAllApplications`() = runBlocking {
        // GIVEN
        val apps = listOf(mockk<LoanApplication>())
        coEvery { repository.getAllApplications() } returns apps

        // WHEN
        val result = useCase.invoke(isAdmin = true)

        // THEN
        assertTrue(result.isSuccess)
        assertEquals(apps, result.getOrNull())
    }

    @Test
    fun `invoke throws exception returns failure result`() = runBlocking {
        // GIVEN
        val exception = Exception("DB error")
        coEvery { repository.getMyApplications() } throws exception

        // WHEN
        val result = useCase.invoke(isAdmin = false)

        // THEN
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
