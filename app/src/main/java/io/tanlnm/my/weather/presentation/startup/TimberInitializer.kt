package io.tanlnm.my.weather.presentation.startup

import android.content.Context
import androidx.startup.Initializer
import io.tanlnm.my.weather.BuildConfig
import io.tanlnm.my.weather.core.logger.DebugLoggingTree
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Timber.tag("Nature Sounds")
        if (BuildConfig.DEBUG) Timber.plant(DebugLoggingTree())
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}