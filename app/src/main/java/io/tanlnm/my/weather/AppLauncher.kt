package io.tanlnm.my.weather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppLauncher : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}