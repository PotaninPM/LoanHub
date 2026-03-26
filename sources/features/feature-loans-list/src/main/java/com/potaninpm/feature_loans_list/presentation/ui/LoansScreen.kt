package com.potaninpm.feature_loans_list.presentation.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potaninpm.feature_loans_list.R
import com.potaninpm.feature_loans_list.di.LoansListComponentHolder
import com.potaninpm.feature_loans_list.presentation.components.LoanListItem
import com.potaninpm.feature_loans_list.presentation.components.PagerIndicator
import com.potaninpm.feature_loans_list.presentation.components.PopularOfferCard
import com.potaninpm.feature_loans_list.presentation.ui.components.LoansListSkeleton
import com.potaninpm.feature_loans_list.presentation.viewmodel.LoansListViewModel
import com.potaninpm.uikit.presentation.DefaultErrorView
import com.potaninpm.uikit.presentation.text.LoanHubUiText
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoansScreen(
    onLoanClick: (String, Color, Color) -> Unit
) {
    val component = LoansListComponentHolder.get()
    
    val vm: LoansListViewModel = viewModel(
        factory = component.provideViewModelFactory()
    )

    val state by vm.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { LoanHubUiText(style = MaterialTheme.typography.titleMedium, text = stringResource(R.string.loans_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { vm.refresh() },
            modifier = Modifier.padding(padding)
        ) {
            if (state.isLoading) {
                LoansListSkeleton(modifier = Modifier.fillMaxSize())
            } else if (state.error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DefaultErrorView(
                        onRetry = { vm.loadProducts() }
                    )
                }
            } else {
                val popular = state.popularOffers
                val all = state.allProducts

                val pagerState = rememberPagerState(
                    initialPage = 0,
                    pageCount = { popular.size }
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (popular.isNotEmpty()) {
                        item {
                            Column {
                                LoanHubUiText(
                                    text = stringResource(R.string.title_popular_offers),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                HorizontalPager(
                                    state = pagerState,
                                    contentPadding = PaddingValues(start = 32.dp, end = 48.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                ) { page ->
                                    val loan = popular[page]
                                    val pageOffset = (
                                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                                    ).absoluteValue

                                    val scale by animateFloatAsState(
                                        targetValue = 1f - (pageOffset * 0.1f).coerceIn(0f, 0.1f),
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessMedium
                                        ),
                                        label = "scale"
                                    )

                                    PopularOfferCard(
                                        loan = loan,
                                        onClick = { onLoanClick(loan.id, loan.gradientStart, loan.gradientEnd) },
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .graphicsLayer {
                                                scaleX = scale
                                                scaleY = scale
                                            }
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                PagerIndicator(
                                    pageCount = popular.size,
                                    currentPage = pagerState.currentPage,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    if (all.isNotEmpty()) {
                        item {
                            LoanHubUiText(
                                text = stringResource(R.string.title_all_products),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }

                        items(all) { loan ->
                            LoanListItem(
                                loan = loan,
                                onClick = {
                                    onLoanClick(loan.id, loan.gradientStart, loan.gradientEnd)
                                },
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}
