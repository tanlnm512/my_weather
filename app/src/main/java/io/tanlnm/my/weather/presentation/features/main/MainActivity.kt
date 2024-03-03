package io.tanlnm.my.weather.presentation.features.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.tanlnm.my.weather.R
import io.tanlnm.my.weather.core.platform.BaseActivity
import io.tanlnm.my.weather.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var splashScreen: SplashScreen
    private var keepSplashScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition(condition = { keepSplashScreen })
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    override fun handleView() {
        lifecycleScope.launch {
            delay(1_000)
            keepSplashScreen = false
        }
    }

    override fun initAction() {

    }
}