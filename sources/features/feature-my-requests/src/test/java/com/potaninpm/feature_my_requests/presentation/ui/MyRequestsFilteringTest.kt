package com.potaninpm.feature_my_requests.presentation.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.test.platform.app.InstrumentationRegistry
import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_my_requests.R
import com.potaninpm.feature_my_requests.di.MyRequestsComponentHolder
import com.potaninpm.feature_my_requests.di.MyRequestsComponentStub
import com.potaninpm.feature_my_requests.domain.repository.LoanApplicationRepository
import com.potaninpm.feature_my_requests.domain.usecase.DeleteApplicationUseCase
import com.potaninpm.feature_my_requests.domain.usecase.GetMyApplicationsUseCase
import com.potaninpm.feature_my_requests.presentation.models.LoanCategory
import com.potaninpm.feature_my_requests.presentation.viewmodel.MyRequestsViewModel
import com.potaninpm.feature_my_requests.utils.MyRequestsScreenTags
import com.potaninpm.utils.dispatchers.DispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], manifest = Config.NONE, qualifiers = "w800dp-h1200dp")
class MyRequestsFilteringTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testDispatcherProvider = object : DispatcherProvider {
        override val main = testDispatcher
        override val io = testDispatcher
        override val default = testDispatcher
    }

    private val getMyApplicationsUseCase: GetMyApplicationsUseCase = mockk()
    private val deleteApplicationUseCase: DeleteApplicationUseCase = mockk()
    private val loanApplicationRepository: LoanApplicationRepository = mockk()
    
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    
    private lateinit var viewModel: MyRequestsViewModel
    private val autoApp = createStubApp(id = AUTO_ID, type = LoanCategory.AUTO.id)
    private val consumerApp = createStubApp(id = CONSUMER_ID, type = LoanCategory.CONSUMER.id)
    private val mortgageApp = createStubApp(id = MORTGAGE_ID, type = LoanCategory.MORTGAGE.id)
    private val creditCardApp = createStubApp(id = CREDIT_CARD_ID, type = LoanCategory.CREDIT_CARD.id)
    private val leasingApp = createStubApp(id = LEASING_ID, type = LoanCategory.LEASING.id)
    
    private val apps = listOf(autoApp, consumerApp, mortgageApp, creditCardApp, leasingApp)

    @Before
    fun setUp() {
        coEvery { getMyApplicationsUseCase(any()) } returns Result.success(apps)
        
        viewModel = MyRequestsViewModel(
            getMyApplicationsUseCase = getMyApplicationsUseCase,
            deleteApplicationUseCase = deleteApplicationUseCase,
            loanApplicationRepository = loanApplicationRepository,
            dispatcherProvider = testDispatcherProvider,
            isAdminFlow = flowOf(false)
        )

        val stubComponent = MyRequestsComponentStub(viewModel)
        MyRequestsComponentHolder.set(stubComponent)
    }

    @After
    fun tearDown() {
        MyRequestsComponentHolder.clear()
    }

    private fun setupContent() {
        composeTestRule.setContent {
            MyRequestsScreenView()
        }
        testDispatcher.scheduler.advanceUntilIdle()
        composeTestRule.waitForIdle()
    }


    @Ignore
    @Test
    fun `initial state shows all applications`() = runTest(testDispatcher) {
        setupContent()

        apps.forEach { app ->
            composeTestRule
                .onNodeWithTag(MyRequestsScreenTags.SUBMITTED_LIST)
                .performScrollToNode(hasTestTag(MyRequestsScreenTags.applicationCard(app.id)))
            composeTestRule
                .onNodeWithTag(MyRequestsScreenTags.applicationCard(app.id)).assertExists()
                .assertIsDisplayed()
        }
    }

    @Ignore
    @Test
    fun `clicking auto filter shows only auto applications`() = runTest(testDispatcher) {
        runFilterTest(R.string.filter_auto, AUTO_ID)
    }

    @Ignore
    @Test
    fun `clicking consumer filter shows only consumer applications`() = runTest(testDispatcher) {
        runFilterTest(R.string.filter_consumer, CONSUMER_ID)
    }

    @Ignore
    @Test
    fun `clicking mortgage filter shows only mortgage applications`() = runTest(testDispatcher) {
        runFilterTest(R.string.filter_mortgage, MORTGAGE_ID)
    }

    @Ignore
    @Test
    fun `clicking credit card filter shows only credit card applications`() = runTest(testDispatcher) {
        runFilterTest(R.string.filter_credit_card, CREDIT_CARD_ID)
    }

    @Ignore
    @Test
    fun `clicking leasing filter shows only leasing applications`() = runTest(testDispatcher) {
        runFilterTest(R.string.filter_leasing, LEASING_ID)
    }

    @Ignore
    @Test
    fun `resetting to all filter shows all applications again`() = runTest(testDispatcher) {
        setupContent()

        // Given
        val autoFilter = context.getString(R.string.filter_auto)
        composeTestRule.onNodeWithTag(MyRequestsScreenTags.filterChip(autoFilter), useUnmergedTree = true).performClick()
        testDispatcher.scheduler.advanceUntilIdle()
        
        // When
        val allFilterText = context.getString(R.string.filter_all)
        composeTestRule.onNodeWithTag(MyRequestsScreenTags.filterChip(allFilterText), useUnmergedTree = true)
            .performScrollTo()
            .performClick()
        
        testDispatcher.scheduler.advanceUntilIdle()
        composeTestRule.waitForIdle()

        // Then
        apps.forEach { app ->
            composeTestRule.onNodeWithTag(MyRequestsScreenTags.SUBMITTED_LIST)
                .performScrollToNode(hasTestTag(MyRequestsScreenTags.applicationCard(app.id)))
            composeTestRule.onNodeWithTag(MyRequestsScreenTags.applicationCard(app.id)).assertExists().assertIsDisplayed()
        }
    }

    private fun runFilterTest(filterResId: Int, expectedId: String) {
        setupContent()

        val filterLabel = context.getString(filterResId)
        composeTestRule
            .onNodeWithTag(MyRequestsScreenTags.filterChip(filterLabel), useUnmergedTree = true)
            .performScrollTo()
            .performClick()
        
        testDispatcher.scheduler.advanceUntilIdle()
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag(MyRequestsScreenTags.SUBMITTED_LIST)
            .performScrollToNode(hasTestTag(MyRequestsScreenTags.applicationCard(expectedId)))
        composeTestRule
            .onNodeWithTag(MyRequestsScreenTags.applicationCard(expectedId)).assertExists()
            .assertIsDisplayed()

        apps.filter { it.id != expectedId }.forEach { other ->
            composeTestRule.onNodeWithTag(MyRequestsScreenTags.applicationCard(other.id)).assertDoesNotExist()
        }
    }

    private fun createStubApp(id: String, type: String): LoanApplication {
        return LoanApplication(
            id = id,
            type = type,
            date = "2024-01-01",
            status = "SUBMITTED",
            fields = emptyList()
        )
    }

    private companion object {
        const val AUTO_ID = "auto_1"
        const val CONSUMER_ID = "consumer_1"
        const val MORTGAGE_ID = "mortgage_1"
        const val CREDIT_CARD_ID = "credit_card_1"
        const val LEASING_ID = "leasing_1"
    }
}