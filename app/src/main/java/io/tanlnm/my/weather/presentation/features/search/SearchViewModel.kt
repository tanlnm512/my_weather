package io.tanlnm.my.weather.presentation.features.search

import dagger.hilt.android.lifecycle.HiltViewModel
import io.tanlnm.my.weather.core.platform.ICommand
import io.tanlnm.my.weather.core.platform.MVIViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

) : MVIViewModel<SearchContract.Command, SearchContract.State, SearchContract.Effect>() {
    override suspend fun handleCommand(command: ICommand) {

    }
}