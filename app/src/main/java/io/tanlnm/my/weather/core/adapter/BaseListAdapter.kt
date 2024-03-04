package io.tanlnm.my.weather.core.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors
import kotlin.properties.Delegates

abstract class BaseListAdapter<T> constructor(
    protected open val compare: (T, T) -> Boolean = { _: T, _: T -> false }
) : ListAdapter<T, RecyclerView.ViewHolder>(
    AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<T>() {
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any) = compare(oldItem, newItem)
    }).setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
) {

    // region SingleSelectable

    // This keeps track of the currently selected position
    private var selectedPosition by Delegates.observable(-1) { _, oldPos, newPos ->
        if (newPos in currentList.indices) {
            notifyItemChanged(oldPos)
            notifyItemChanged(newPos)
        }
    }

    fun select(item: T?) {
        selectedPosition = currentList.indexOf(item)
    }

    fun select(index: Int) {
        selectedPosition = index
    }

    fun selectedIndex() = selectedPosition

    /**
     * @return true if this [position] is selected
     * */
    fun isSelected(position: Int): Boolean {
        return position == selectedPosition
    }

    // endregion

    open fun get(position: Int): T = super.getItem(position)

    fun atLeast(threadHold: Int) = itemCount >= threadHold

    fun isEmpty() = itemCount <= 0

}