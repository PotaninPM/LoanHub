package com.potaninpm.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.potaninpm.common.impl.AppComponentHolder
import com.potaninpm.feature_application_details.presentation.ui.ApplicationDetailsScreen
import com.potaninpm.feature_application_preview.presentation.ui.LoanApplicationPreviewScreen
import com.potaninpm.feature_auth.presentation.screens.LoginScreen
import com.potaninpm.feature_edit_profile.presentation.ui.EditProfileScreen
import com.potaninpm.feature_loan_application.presentation.ui.LoanApplicationScreen
import com.potaninpm.feature_loan_details.presentation.ui.LoanDetailsScreen
import com.potaninpm.feature_loans_list.presentation.ui.LoansScreen
import com.potaninpm.feature_my_requests.presentation.ui.MyRequestsScreenView
import com.potaninpm.feature_profile.presentation.ui.ProfileScreen
import com.potaninpm.navigation.bottombar.BottomNavBar
import com.potaninpm.uikit.utils.toHexString
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun RootNavHost() {
    val navController = rememberNavController()

    val appComponent = AppComponentHolder.get()
    val startDestination = if (appComponent.sessionManager.isAuthenticated()) {
        RootNavDestinations.MainFlow
    } else {
        RootNavDestinations.LoginScreen
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<RootNavDestinations.LoginScreen>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { NavAnimations.SlideOutRight(this) },
            popEnterTransition = { NavAnimations.SlideInRight(this) },
            popExitTransition = { NavAnimations.SlideOutRight(this) }
        ) {
            LoginScreen(
                onAuthSuccess = {
                    navController.navigate(RootNavDestinations.MainFlow) {
                        popUpTo(RootNavDestinations.LoginScreen) { inclusive = true }
                    }
                },
            )
        }

        composable<RootNavDestinations.MainFlow>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { NavAnimations.SlideOutRight(this) }
        ) { backStackEntry ->
            val refreshTrigger = backStackEntry.savedStateHandle.get<Long>("refresh_profile") ?: 0L
            val refreshRequestsTrigger = backStackEntry.savedStateHandle.get<Long>("refresh_requests") ?: 0L

            MainFlow(
                refreshTrigger = refreshTrigger,
                refreshRequestsTrigger = refreshRequestsTrigger,
                onOfferClick = { id ->
                    navController.navigate(
                        RootNavDestinations.ApplicationDetailsScreen(applicationId = id)
                    )
                },
                onDraftClick = { id, type ->
                    navController.navigate(
                        RootNavDestinations.LoanApplicationScreen(
                            loanType = type,
                            draftId = id
                        )
                    )
                },
                onNavigateToApplication = { loanType, fieldsJson ->
                    navController.navigate(
                        RootNavDestinations.LoanApplicationScreen(
                            loanType = loanType,
                            importedFieldsJson = fieldsJson
                        )
                    )
                },
                onLoanClick = { type, startColor, endColor ->
                    navController.navigate(
                        RootNavDestinations.LoanDetailsScreen(
                            type,
                            startColor.toHexString(),
                            endColor.toHexString()
                        )
                    )
                },
                onEditClick = {
                    navController.navigate(RootNavDestinations.EditProfileScreen)
                },
                onLogout = {
                    navController.navigate(RootNavDestinations.LoginScreen) {
                        popUpTo(RootNavDestinations.MainFlow) { inclusive = true }
                    }
                }
            )
        }

        composable<RootNavDestinations.EditProfileScreen>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { NavAnimations.SlideOutRight(this) },
            popEnterTransition = { NavAnimations.SlideInRight(this) },
            popExitTransition = { NavAnimations.SlideOutRight(this) }
        ) {
            EditProfileScreen(
                onBack = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("refresh_profile", System.currentTimeMillis())
                }
            )
        }

        composable<RootNavDestinations.LoanDetailsScreen>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { NavAnimations.SlideOutRight(this) },
            popEnterTransition = { NavAnimations.SlideInRight(this) },
            popExitTransition = { NavAnimations.SlideOutRight(this) }
        ) { backStackEntry ->
            val destination: RootNavDestinations.LoanDetailsScreen = backStackEntry.toRoute()
            LoanDetailsScreen(
                loanId = destination.loanId,
                startColor = destination.startColor,
                endColor = destination.endColor,
                onBack = { navController.popBackStack() },
                onApplyClick = {
                    navController.navigate(
                        RootNavDestinations.LoanApplicationScreen(destination.loanId)
                    )
                }
            )
        }

        composable<RootNavDestinations.LoanApplicationScreen>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { NavAnimations.SlideOutRight(this) },
            popEnterTransition = { NavAnimations.SlideInRight(this) },
            popExitTransition = { NavAnimations.SlideOutRight(this) }
        ) { backStackEntry ->
            val destination: RootNavDestinations.LoanApplicationScreen = backStackEntry.toRoute()
            LoanApplicationScreen(
                loanType = destination.loanType,
                draftId = destination.draftId,
                importedFieldsJson = destination.importedFieldsJson,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPreview = { fieldsJson, incomeCurrency, amountCurrency, loanType ->
                    navController.navigate(
                        RootNavDestinations.LoanApplicationPreviewScreen(
                            fieldsJson,
                            incomeCurrency,
                            amountCurrency,
                            loanType = loanType
                        )
                    )
                }
            )
        }

        composable<RootNavDestinations.LoanApplicationPreviewScreen>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { NavAnimations.SlideOutRight(this) },
            popEnterTransition = { NavAnimations.SlideInRight(this) },
            popExitTransition = { NavAnimations.SlideOutRight(this) }
        ) { backStackEntry ->
            val destination: RootNavDestinations.LoanApplicationPreviewScreen = backStackEntry.toRoute()
            LoanApplicationPreviewScreen(
                fieldsJson = destination.fieldsJson,
                incomeCurrency = destination.incomeCurrency,
                amountCurrency = destination.amountCurrency,
                loanType = destination.loanType,
                onBack = { navController.popBackStack() },
                onConfirmAndGoHome = {
                    navController.popBackStack(
                        route = RootNavDestinations.MainFlow,
                        inclusive = false
                    )
                }
            )
        }

        composable<RootNavDestinations.ApplicationDetailsScreen>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { NavAnimations.SlideOutRight(this) },
            popEnterTransition = { NavAnimations.SlideInRight(this) },
            popExitTransition = { NavAnimations.SlideOutRight(this) }
        ) { backStackEntry ->
            val destination: RootNavDestinations.ApplicationDetailsScreen = backStackEntry.toRoute()
            ApplicationDetailsScreen(
                applicationId = destination.applicationId,
                onBack = { navController.popBackStack() },
                onDeleted = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("refresh_requests", System.currentTimeMillis())
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
private fun MainFlow(
    refreshTrigger: Long = 0,
    refreshRequestsTrigger: Long = 0,
    onOfferClick: (String) -> Unit,
    onDraftClick: (String, String) -> Unit,
    onNavigateToApplication: (loanType: String, fieldsJson: String) -> Unit,
    onLoanClick: (String, Color, Color) -> Unit,
    onEditClick: () -> Unit,
    onLogout: () -> Unit
) {
    var selectedBottomNavItem by rememberSaveable {
        mutableStateOf(MainMenuBottomNavItems.MY_REQUESTS)
    }

    val hazeState = rememberHazeState()

    val navItems = remember {
        listOf(
            NavItemData(
                item = MainMenuBottomNavItems.MY_REQUESTS,
                label = "Мои заявки",
                selectedIcon = R.drawable.home_filled,
                unselectedIcon = R.drawable.home_not_filled
            ),
            NavItemData(
                item = MainMenuBottomNavItems.LOANS,
                label = "Кредиты",
                selectedIcon = R.drawable.loans_filled,
                unselectedIcon = R.drawable.loans_not_filled
            ),
            NavItemData(
                item = MainMenuBottomNavItems.PROFILE,
                label = "Профиль",
                selectedIcon = R.drawable.profile_filled,
                unselectedIcon = R.drawable.profile_not_filled
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState)
        ) {
            AnimatedContent(
                targetState = selectedBottomNavItem,
                transitionSpec = {
                    val direction = targetState.ordinal - initialState.ordinal
                    val enterOffset = if (direction > 0) { { w: Int -> w } } else { { w: Int -> -w } }
                    val exitOffset = if (direction > 0) { { w: Int -> -w } } else { { w: Int -> w } }

                    (slideInHorizontally(initialOffsetX = enterOffset) + fadeIn())
                        .togetherWith(slideOutHorizontally(targetOffsetX = exitOffset) + fadeOut())
                        .using(SizeTransform(clip = false))
                },
                label = "TabContent"
            ) { tab ->
                when (tab) {
                    MainMenuBottomNavItems.MY_REQUESTS -> {
                        MyRequestsScreenView(
                            refreshTrigger = refreshRequestsTrigger,
                            onOfferClick = { id, _ ->
                                onOfferClick(id)
                            },
                            onDraftClick = { id, type ->
                                onDraftClick(id, type)
                            },
                            onNavigateToApplication = onNavigateToApplication
                        )
                    }
                    MainMenuBottomNavItems.LOANS -> {
                        LoansScreen(onLoanClick = onLoanClick)
                    }
                    MainMenuBottomNavItems.PROFILE -> {
                        ProfileScreen(
                            refreshTrigger = refreshTrigger,
                            onEditClick = { onEditClick() },
                            onLogout = { onLogout() }
                        )
                    }
                }
            }
        }

        BottomNavBar(
            selectedItem = selectedBottomNavItem,
            onItemSelected = { selectedBottomNavItem = it },
            navItems = navItems,
            hazeState = hazeState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp, start = 8.dp, end = 8.dp)
        )
    }
}