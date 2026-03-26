package com.potaninpm.feature_edit_profile.presentation.viewmodel

import com.potaninpm.feature_edit_profile.domain.usecase.GetProfileUseCase
import com.potaninpm.feature_edit_profile.domain.usecase.UpdateProfileUseCase
import com.potaninpm.feature_edit_profile.utils.UserProfile
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
internal class EditProfileViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testDispatcherProvider = object : DispatcherProvider {
        override val main = testDispatcher
        override val io = testDispatcher
        override val default = testDispatcher
    }

    private val getProfileUseCase: GetProfileUseCase = mockk()
    private val updateProfileUseCase: UpdateProfileUseCase = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProfile should update state with profile and fioInput on success`() = runTest(testDispatcher) {
        // Given
        val mockProfile = UserProfile(
            firstName = "Ivan",
            lastName = "Ivanov",
            patronymic = "Ivanovich"
        )
        coEvery { getProfileUseCase() } returns Result.success(mockProfile)

        // When
        val viewModel = EditProfileViewModel(getProfileUseCase, updateProfileUseCase, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(mockProfile, state.profile)
        assertEquals("Ivanov Ivan Ivanovich", state.fioInput)
    }

    @Test
    fun `updateFio should update profile name fields`() = runTest(testDispatcher) {
         // Given
        val mockProfile = UserProfile()
        coEvery { getProfileUseCase() } returns Result.success(mockProfile)
        val viewModel = EditProfileViewModel(getProfileUseCase, updateProfileUseCase, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.updateFio("Petrov Petr Petrovich")

        // Then
        val state = viewModel.state.value
        assertEquals("Petrov Petr Petrovich", state.fioInput)
        assertEquals("Petrov", state.profile.lastName)
        assertEquals("Petr", state.profile.firstName)
        assertEquals("Petrovich", state.profile.patronymic)
    }

    @Test
    fun `saveProfile should call updateProfileUseCase and set success state`() = runTest(testDispatcher) {
        // Given
        val mockProfile = UserProfile(id = "123")
        coEvery { getProfileUseCase() } returns Result.success(mockProfile)
        coEvery { updateProfileUseCase(any()) } returns Result.success(Unit)

        val viewModel = EditProfileViewModel(getProfileUseCase, updateProfileUseCase, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.saveProfile()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isSaving)
        assertTrue(state.saveSuccess)
        assertNull(state.error)
        coVerify(exactly = 1) { updateProfileUseCase(mockProfile) }
    }

    @Test
    fun `saveProfile should set error on failure`() = runTest(testDispatcher) {
        // Given
        val errorText = "Save error"
        val exception = RuntimeException(errorText)
        coEvery { getProfileUseCase() } returns Result.success(UserProfile())
        coEvery { updateProfileUseCase(any()) } returns Result.failure(exception)

        val viewModel = EditProfileViewModel(getProfileUseCase, updateProfileUseCase, testDispatcherProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.saveProfile()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isSaving)
        assertFalse(state.saveSuccess)
        assertEquals(errorText, state.error)
    }
}
