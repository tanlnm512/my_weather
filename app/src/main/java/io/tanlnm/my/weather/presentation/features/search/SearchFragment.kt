package io.tanlnm.my.weather.presentation.features.search

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import io.tanlnm.my.weather.core.extension.showKeyboard
import io.tanlnm.my.weather.core.platform.MVIFragment
import io.tanlnm.my.weather.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment :
    MVIFragment<SearchViewModel, FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    override val viewModel: SearchViewModel by viewModels()

    companion object{
        const val POP_BACK_WEATHER = "pop_back_weather"
    }

    override fun handleView(view: View) {
        binding.etSearch.applyInsetter { type(statusBars = true) { margin() } }
    }

    override fun initAction() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etSearch.post {
            binding.etSearch.requestFocus()
            binding.etSearch.showKeyboard()
        }
    }
}