package com.potaninpm.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut

object NavAnimations {
    private const val DURATION = 300

    val SlideInLeft: AnimatedContentTransitionScope<*>.() -> EnterTransition =
        {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(DURATION)
            )
        }

    val SlideOutLeft: AnimatedContentTransitionScope<*>.() -> ExitTransition =
        {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(DURATION)
            )
        }

    val ScaleIn: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
        scaleIn(
            initialScale = 0.8f,
            animationSpec = tween(DURATION)
        )
    }

    val ScaleOut: AnimatedContentTransitionScope<*>.() -> ExitTransition = {
        scaleOut(
            targetScale = 0.8f,
            animationSpec = tween(DURATION)
        )
    }

    val SlideInRight: AnimatedContentTransitionScope<*>.() -> EnterTransition =
        {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(DURATION)
            )
        }

    val SlideOutRight: AnimatedContentTransitionScope<*>.() -> ExitTransition =
        {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(DURATION)
            )
        }

    val SlideInVertically: AnimatedContentTransitionScope<*>.() -> EnterTransition =
        {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(DURATION)
            )
        }

    val SlideOutVertically: AnimatedContentTransitionScope<*>.() -> ExitTransition =
        {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(DURATION)
            )
        }
}
