@file:JvmMultifileClass
@file:JvmName("CoroutinesExt")

package io.tanlnm.my.weather.core.extension

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val IO_EXECUTOR: ExecutorService = Executors.newSingleThreadExecutor()
val MAIN_THREAD = MainThreadExecutor()
fun mainThread(f: () -> Unit) {
    MAIN_THREAD.execute(f)
}

fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}

class MainThreadExecutor : Executor {
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }
}