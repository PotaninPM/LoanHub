package com.potaninpm.feature_loans_list.presentation.viewmodel

import com.potaninpm.feature_loans_list.domain.usecase.GetAllProductsUseCase
import com.potaninpm.feature_loans_list.domain.usecase.GetPopularOffersUseCase
import com.potaninpm.feature_loans_list.presentation.utils.DEFAULT_LOAN_TYPES
import com.potaninpm.feature_loans_list.utils.LoanType
import com.potaninpm.utils.dispatchers.DispatcherProvider
import io.mockk.coEvery
import io.mockk.coVerify
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoansListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testDispatcherProvider = object : DispatcherProvider {
        override val main = testDispatcher
        override val io = testDispatcher
        override val default = testDispatcher
    }

    private val getAllProducts: GetAllProductsUseCase = mockk()
    private val getPopularOffers: GetPopularOffersUseCase = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should have isLoading true`() = runTest(testDispatcher) {
        coEvery { getPopularOffers() } returns Result.success(emptyList())
        coEvery { getAllProducts() } returns Result.success(emptyList())

        val viewModel = LoansListViewModel(getAllProducts, getPopularOffers, testDispatcherProvider)

        assertTrue(viewModel.state.value.isLoading)
    }

    @Test
    fun `loadProducts should update state with products on success`() = runTest(testDispatcher) {
        val mockPopular = listOf(mockk<LoanType>())
        val mockAll = listOf(mockk<LoanType>(), mockk<LoanType>())

        coEvery { getPopularOffers() } returns Result.success(mockPopular)
        coEvery { getAllProducts() } returns Result.success(mockAll)

        val viewModel = LoansListViewModel(getAllProducts, getPopularOffers, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(mockPopular, state.popularOffers)
        assertEquals(mockAll, state.allProducts)
    }

    @Test
    fun `loadProducts should set error on failure`() = runTest(testDispatcher) {
        val exception = RuntimeException("Network error")
        coEvery { getPopularOffers() } returns Result.failure(exception)
        coEvery { getAllProducts() } returns Result.success(emptyList())

        val viewModel = LoansListViewModel(getAllProducts, getPopularOffers, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertTrue(state.error != null)
        assertEquals("Network error", state.error?.message)
    }

    @Test
    fun `loadProducts should use default products when both calls fail`() = runTest(testDispatcher) {
        coEvery { getPopularOffers() } returns Result.failure(RuntimeException("fail"))
        coEvery { getAllProducts() } returns Result.failure(RuntimeException("fail"))

        val viewModel = LoansListViewModel(getAllProducts, getPopularOffers, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(DEFAULT_LOAN_TYPES, state.popularOffers)
        assertEquals(DEFAULT_LOAN_TYPES, state.allProducts)
        assertTrue(state.error != null)
    }

    @Test
    fun `loadProducts should preserve existing data on failure after success`() = runTest(testDispatcher) {
        val mockPopular = listOf(mockk<LoanType>())
        val mockAll = listOf(mockk<LoanType>())

        coEvery { getPopularOffers() } returns Result.success(mockPopular)
        coEvery { getAllProducts() } returns Result.success(mockAll)

        val viewModel = LoansListViewModel(getAllProducts, getPopularOffers, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        coEvery { getPopularOffers() } returns Result.failure(RuntimeException("fail"))
        coEvery { getAllProducts() } returns Result.failure(RuntimeException("fail"))

        viewModel.loadProducts()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(mockPopular, state.popularOffers)
        assertEquals(mockAll, state.allProducts)
    }

    @Test
    fun `refresh should be ignored while initial load is in progress`() = runTest(testDispatcher) {
        coEvery { getPopularOffers() } returns Result.success(emptyList())
        coEvery { getAllProducts() } returns Result.success(emptyList())

        val viewModel = LoansListViewModel(getAllProducts, getPopularOffers, testDispatcherProvider)

        assertTrue(viewModel.state.value.isLoading)
        viewModel.refresh()

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { getPopularOffers() }
        coVerify(exactly = 1) { getAllProducts() }
    }

    @Test
    fun `refresh should trigger loadProducts with isRefresh=true`() = runTest(testDispatcher) {
        val mockPopular = listOf(mockk<LoanType>())
        val mockAll = listOf(mockk<LoanType>())

        coEvery { getPopularOffers() } returns Result.success(mockPopular)
        coEvery { getAllProducts() } returns Result.success(mockAll)

        val viewModel = LoansListViewModel(getAllProducts, getPopularOffers, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.refresh()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isRefreshing)
        assertEquals(mockPopular, state.popularOffers)
        assertEquals(mockAll, state.allProducts)

        coVerify(exactly = 2) { getPopularOffers() }
        coVerify(exactly = 2) { getAllProducts() }
    }

    @Test
    fun `allProducts failure should set error even if popular succeeds`() = runTest(testDispatcher) {
        coEvery { getPopularOffers() } returns Result.success(emptyList())
        coEvery { getAllProducts() } returns Result.failure(RuntimeException("all failed"))

        val viewModel = LoansListViewModel(getAllProducts, getPopularOffers, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state.error != null)
        assertEquals("all failed", state.error?.message)
    }
}
