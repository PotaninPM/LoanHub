package com.potaninpm.feature_loan_details.presentation.viewmodel

import com.potaninpm.feature_loan_details.domain.usecase.GetLoanDetailsUseCase
import com.potaninpm.feature_loan_details.utils.LoanDetails
import com.potaninpm.utils.dispatchers.DispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class LoanDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testDispatcherProvider = object : DispatcherProvider {
        override val main = testDispatcher
        override val io = testDispatcher
        override val default = testDispatcher
    }

    private val getLoanDetails: GetLoanDetailsUseCase = mockk()
    private val loanId = "123"

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadDetails should update state with details on success`() = runTest(testDispatcher) {
        // Given
        val mockDetails = mockk<LoanDetails>()
        coEvery { getLoanDetails(loanId) } returns Result.success(mockDetails)

        // When
        val viewModel = LoanDetailsViewModel(loanId, getLoanDetails, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(mockDetails, state.loanDetails)
    }

    @Test
    fun `loadDetails should set error on failure`() = runTest(testDispatcher) {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { getLoanDetails(loanId) } returns Result.failure(exception)

        // When
        val viewModel = LoanDetailsViewModel(loanId, getLoanDetails, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals("Network error", state.error)
    }
}
