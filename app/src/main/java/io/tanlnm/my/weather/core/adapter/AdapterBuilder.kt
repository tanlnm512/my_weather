package io.tanlnm.my.weather.core.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import io.tanlnm.my.weather.core.annotation.BuilderDSL
import io.tanlnm.my.weather.core.extension.mainThread

/**
 * This is Template Adapter for build single simple adapter
 * when work with recyclerview
 * */
open class TemplateAdapter<Data, VB : ViewBinding>(
    private val onCreate: ViewGroup.() -> VB,
    private val onBind: (RowView<Data, VB>, TemplateAdapter<Data, VB>) -> Unit,
    override val compare: (Data, Data) -> Boolean = { _: Data, _: Data -> false }
) : BaseListAdapter<Data>(compare) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TemplateViewHolder(onCreate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TemplateAdapter<*, *>.TemplateViewHolder).bind(position)
    }

    inner class TemplateViewHolder(override val binding: VB) : BaseBindingViewHolder<VB>(binding) {

        fun bind(position: Int) = mainThread {
            onBind(
                RowView(
                    row = Pair(binding, get(position)),
                    view = Triple(itemView, position, itemCount)
                ),
                this@TemplateAdapter
            )
        }

    }

}

/**
 * A RowView container
 * @property row includes: ViewBinding, Model Data
 * @property view includes: [View, position, itemCount]
 */
data class RowView<Data, VB : ViewBinding>(
    val row: Pair<VB, Data>,
    val view: Triple<View, Int, Int>
) {

    fun hasDivider() = view.second != view.third - 1

    fun context(): Context = view.first.context

}

@BuilderDSL
fun <Data, VB : ViewBinding> adapterOf(
    onCreate: ViewGroup.() -> VB,
    onBind: (RowView<Data, VB>, TemplateAdapter<Data, VB>) -> Unit,
    onCompare: (Data, Data) -> Boolean = { _: Data, _: Data -> false }
) = TemplateAdapter(onCreate, onBind, onCompare)