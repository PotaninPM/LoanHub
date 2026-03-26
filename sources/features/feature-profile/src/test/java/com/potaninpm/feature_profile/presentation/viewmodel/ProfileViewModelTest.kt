package com.potaninpm.feature_profile.presentation.viewmodel

import com.potaninpm.feature_profile.domain.repository.ProfileRepository
import com.potaninpm.feature_profile.utils.Profile
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testDispatcherProvider = object : DispatcherProvider {
        override val main = testDispatcher
        override val io = testDispatcher
        override val default = testDispatcher
    }

    private val repository: ProfileRepository = mockk()
    
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProfile should update state with profile on success`() = runTest(testDispatcher) {
        // Given
        val mockProfile = Profile(firstName = "Ivan", lastName = "Ivanov")
        coEvery { repository.getProfile() } returns Result.success(mockProfile)

        // When
        val viewModel = ProfileViewModel(repository, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
        assertNull(state.error)
        assertEquals(mockProfile, state.profile)
    }

    @Test
    fun `loadProfile should set error on failure`() = runTest(testDispatcher) {
        // Given
        val errorText = "Network error"
        val exception = RuntimeException(errorText)
        coEvery { repository.getProfile() } returns Result.failure(exception)

        // When
        val viewModel = ProfileViewModel(repository, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
        assertEquals(errorText, state.error)
    }

    @Test
    fun `refresh should triggers loadProfile with isRefresh=true`() = runTest(testDispatcher) {
         // Given
        val mockProfile = Profile(firstName = "Ivan", lastName = "Ivanov")
        coEvery { repository.getProfile() } returns Result.success(mockProfile)

        val viewModel = ProfileViewModel(repository, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // When
        viewModel.refresh()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(mockProfile, state.profile)
        assertFalse(state.isRefreshing)
        
        coVerify(exactly = 2) { repository.getProfile() }
    }

    @Test
    fun `logout should call repository logout`() = runTest(testDispatcher) {
        // Given
        coEvery { repository.getProfile() } returns Result.success(Profile())
        coEvery { repository.logout() } returns Unit
        
        val viewModel = ProfileViewModel(repository, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.logout()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { repository.logout() }
    }
}
