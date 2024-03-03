package io.tanlnm.my.weather.core.logger

import android.util.Log
import timber.log.Timber

class DebugLoggingTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        // Display on Logcat: .loginClick(LoginActivity.kt:77):
        return String.format(
            ".%s(%s:%s)",
            element.methodName,
            element.fileName,
            element.lineNumber
        )
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

        val thread = "[🧵 ${Thread.currentThread().name}] : "
        val info = "$thread $message"

        // Workaround for devices that doesn't show lower priority logs
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)
            super.log(Log.INFO, tag, info, t)
        else
            super.log(priority, tag, info, t)
    }

}
