package com.potaninpm.uikit.presentation.ext

object ClickDebouncer {
    private var lastClickTime = 0L
    private const val DEBOUNCE_DELAY = 350L

    fun canProceed(): Boolean {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime < DEBOUNCE_DELAY) {
            return false
        }
        lastClickTime = currentTime
        return true
    }
}

fun (() -> Unit).debounced(): () -> Unit {
    return {
        if (ClickDebouncer.canProceed()) {
            this()
        }
    }
}
