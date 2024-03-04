package io.tanlnm.my.weather.presentation.features.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import io.tanlnm.my.weather.R
import io.tanlnm.my.weather.core.adapter.TemplateAdapter
import io.tanlnm.my.weather.core.adapter.adapterOf
import io.tanlnm.my.weather.core.extension.getNavigationResult
import io.tanlnm.my.weather.core.extension.inflate
import io.tanlnm.my.weather.core.platform.MVIFragment
import io.tanlnm.my.weather.data.model.Weather
import io.tanlnm.my.weather.databinding.FragmentHomeBinding
import io.tanlnm.my.weather.databinding.ItemWeatherBinding
import io.tanlnm.my.weather.presentation.features.search.SearchFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class HomeFragment : MVIFragment<HomeViewModel, FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: TemplateAdapter<Weather, ItemWeatherBinding>
    private val weathers = mutableListOf<Weather>()

    override fun handleView(view: View) {
        binding.textView1.applyInsetter { type(statusBars = true) { margin() } }

        adapter = adapterOf(
            onCreate = { inflate(ItemWeatherBinding::inflate) },
            onBind = { rowView, _ ->
                val (itemBinding, data) = rowView.row
                itemBinding.tvCity.text = data.name
                itemBinding.tvTemp.text = "Temp: %d".format(data.mainInfo.temp.toInt())
                itemBinding.tvFeels.text = "Real feels: %d".format(data.mainInfo.feelsLike.toInt())
            }
        )
        binding.recyclerView.adapter = adapter
    }

    override fun initAction() {
        binding.tvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    override fun subscribeUI() {
        super.subscribeUI()
        getNavigationResult<Weather?>(R.id.homeFragment, SearchFragment.POP_BACK_WEATHER) {
            it?.let { weather ->
                binding.emptyView.visibility = View.GONE
                weathers.add(weather)
                adapter.submitList(weathers)
            }
        }
    }
}