package com.potaninpm.uikit.presentation.text

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith


sealed class LoanHubUiTextAnimation(
    val animation: AnimatedContentTransitionScope<String>.() -> ContentTransform
) {
    class Vertical : LoanHubUiTextAnimation(
        {
            (slideInVertically { it } + fadeIn())
                .togetherWith(slideOutVertically { -it } + fadeOut())
        }
    )

    class Horizontal : LoanHubUiTextAnimation(
        {
            (slideInHorizontally { it } + fadeIn())
                .togetherWith(slideOutHorizontally { -it } + fadeOut())
        }
    )

    class Typing : LoanHubUiTextAnimation(
        {
            (fadeIn())
                .togetherWith(fadeOut())
        }
    )
}