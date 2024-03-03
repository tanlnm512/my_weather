package io.tanlnm.my.weather.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> constructor(
    private val bindingInflater: (LayoutInflater) -> VB
) : AppCompatActivity() {
    private var _binding: VB? = null

    protected val binding
        get() = _binding!!

    abstract fun handleView()
    abstract fun initAction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)

        initAction()
        handleView()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}