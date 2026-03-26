package com.potaninpm.common.impl

import android.content.Context
import com.potaninpm.common.api.ComponentHolder

object AppComponentHolder : ComponentHolder<AppComponent>() {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    override fun build(): AppComponent {
        return AppComponentImpl(appContext)
    }

    // для тестов
    fun setForTests(component: AppComponent) = set(component)
}