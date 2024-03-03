@file:JvmMultifileClass
@file:JvmName("FlowExt")

package io.tanlnm.my.weather.core.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

inline fun <T> Flow<T>.collectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    crossinline action: suspend (value: T) -> Unit,
): Job = this
    .flowWithLifecycle(owner.lifecycle, minActiveState)
    .onEach {
        Timber.d("Start collecting $owner $minActiveState...")
        action.invoke(it)
    }
    .launchIn(owner.lifecycleScope)

/**
 * Launches a new coroutine and repeats `block` every time the Fragment's viewLifecycleOwner
 * is in and out of `minActiveState` lifecycle state.
 */
@Suppress("unused")
inline fun <T> Flow<T>.collectInViewLifecycle(
    fragment: Fragment,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    crossinline action: suspend (value: T) -> Unit,
): Job =
    collectIn(
        owner = fragment.viewLifecycleOwner,
        minActiveState = minActiveState,
        action = action,
    )