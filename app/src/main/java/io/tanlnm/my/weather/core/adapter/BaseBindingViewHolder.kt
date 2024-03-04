package io.tanlnm.my.weather.core.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import io.tanlnm.my.weather.core.annotation.BindingDSL

/**
 * A generic ViewHolder that works with a [ViewBinding].
 * @param <T> The type of the ViewBinding.
</T> */
abstract class BaseBindingViewHolder<VB : ViewBinding>(
    @BindingDSL
    protected open val binding: VB
) : RecyclerView.ViewHolder(binding.root) {

    protected fun context(): Context = itemView.context

}
