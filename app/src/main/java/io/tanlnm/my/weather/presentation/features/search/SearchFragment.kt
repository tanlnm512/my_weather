package io.tanlnm.my.weather.presentation.features.search

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import io.tanlnm.my.weather.core.adapter.TemplateAdapter
import io.tanlnm.my.weather.core.adapter.adapterOf
import io.tanlnm.my.weather.core.extension.collectIn
import io.tanlnm.my.weather.core.extension.inflate
import io.tanlnm.my.weather.core.extension.setNavigationResult
import io.tanlnm.my.weather.core.extension.showKeyboard
import io.tanlnm.my.weather.core.extension.showToast
import io.tanlnm.my.weather.core.extension.textChanges
import io.tanlnm.my.weather.core.platform.IEffect
import io.tanlnm.my.weather.core.platform.IState
import io.tanlnm.my.weather.core.platform.MVIFragment
import io.tanlnm.my.weather.data.model.Weather
import io.tanlnm.my.weather.databinding.FragmentSearchBinding
import io.tanlnm.my.weather.databinding.ItemSearchWeatherBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment :
    MVIFragment<SearchViewModel, FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    override val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: TemplateAdapter<Weather, ItemSearchWeatherBinding>

    companion object {
        const val POP_BACK_WEATHER = "pop_back_weather"
    }

    override fun handleView(view: View) {
        binding.etSearch.applyInsetter { type(statusBars = true) { margin() } }

        adapter = adapterOf(
            onCreate = { inflate(ItemSearchWeatherBinding::inflate) },
            onBind = { rowView, _ ->
                val (itemBinding, data) = rowView.row
                itemBinding.tvCity.text = data.name

                itemBinding.root.setOnClickListener {
                    setNavigationResult(POP_BACK_WEATHER, data)
                    findNavController().popBackStack()
                }
            }
        )
    }

    override fun initAction() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etSearch.post {
            binding.etSearch.requestFocus()
            binding.etSearch.showKeyboard()
        }

        binding.etSearch.textChanges()
            .debounce(250L)
            .onEach { viewModel.sendCommand(SearchContract.Command.Search(it.toString())) }
            .launchIn(lifecycleScope)
    }

    override fun subscribeUI() {
        super.subscribeUI()
        viewModel.state.collectIn(this) { handleState(it) }
        viewModel.effect.collectIn(this) { handleEffect(it) }
    }

    override suspend fun handleState(state: IState) {
        super.handleState(state)
        when (state) {
            is SearchContract.State -> {
                if (state.weather != null)
                    adapter.submitList(listOf(state.weather))
                else
                    adapter.submitList(emptyList())
            }
        }
    }

    override suspend fun handleEffect(effect: IEffect) {
        super.handleEffect(effect)
        when (effect) {
            is SearchContract.Effect.ShowErrorMessage -> requireContext().showToast(effect.message)
        }
    }
}