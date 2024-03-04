package io.tanlnm.my.weather.presentation.features.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import io.tanlnm.my.weather.R
import io.tanlnm.my.weather.core.platform.MVIFragment
import io.tanlnm.my.weather.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : MVIFragment<HomeViewModel, FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeViewModel by viewModels()

    override fun handleView(view: View) {
        binding.textView1.applyInsetter { type(statusBars = true) { margin() } }
    }

    override fun initAction() {
        binding.tvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }
}