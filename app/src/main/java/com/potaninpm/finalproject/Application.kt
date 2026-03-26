package com.potaninpm.finalproject

import android.app.Application
import android.content.Intent
import com.potaninpm.common.impl.AppComponentHolder
import com.potaninpm.finalproject.presentation.CrashActivity
import kotlin.system.exitProcess

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponentHolder.init(this)

        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            val intent = Intent(this, CrashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra(ERROR_MESSAGE, throwable.localizedMessage)
            startActivity(intent)
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(10)
        }
    }

    companion object {
        const val ERROR_MESSAGE = "error_message"
    }
}