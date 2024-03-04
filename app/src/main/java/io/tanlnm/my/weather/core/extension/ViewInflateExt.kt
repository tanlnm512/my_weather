@file:JvmMultifileClass
@file:JvmName("ViewInflateExt")

package io.tanlnm.my.weather.core.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 *  Used for ViewBinding
 *  - parent: ViewGroup
 *  - attachToRoot: Boolean
 *  - inflater: LayoutInflater
 */
inline fun <V : ViewBinding> ViewGroup.inflate(
    factory: (LayoutInflater, ViewGroup, Boolean) -> V,
    attachToParent: Boolean = false
) = factory(LayoutInflater.from(context), this, attachToParent)