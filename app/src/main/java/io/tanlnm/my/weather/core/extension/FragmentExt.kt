@file:JvmMultifileClass
@file:JvmName("FragmentExt")

package io.tanlnm.my.weather.core.extension

import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController

fun <T> Fragment.setNavigationResult(key: String, value: T) {
    findNavController()
        .previousBackStackEntry
        ?.savedStateHandle
        ?.set(key, value)
}

/**
 * id : current fragment id
 */
fun <T> Fragment.getNavigationResult(@IdRes id: Int, key: String, onResult: (result: T?) -> Unit) {
    val navBackStackEntry = findNavController().getBackStackEntry(id)

    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_RESUME && navBackStackEntry.savedStateHandle.contains(key)) {
            val result = navBackStackEntry.savedStateHandle.get<T>(key)
            onResult.invoke(result)
            navBackStackEntry.savedStateHandle.remove<T>(key)
        }
    }
    navBackStackEntry.lifecycle.addObserver(observer)

    viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            navBackStackEntry.lifecycle.removeObserver(observer)
        }
    })
}

fun Fragment.onBackPressed(callback: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(this) {
        callback.invoke()
    }
}