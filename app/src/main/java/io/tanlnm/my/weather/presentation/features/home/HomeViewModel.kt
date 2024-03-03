package io.tanlnm.my.weather.presentation.features.home

import dagger.hilt.android.lifecycle.HiltViewModel
import io.tanlnm.my.weather.core.platform.ICommand
import io.tanlnm.my.weather.core.platform.MVIViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : MVIViewModel<HomeContract.Command, HomeContract.State, HomeContract.Effect>() {
    override suspend fun handleCommand(command: ICommand) {

    }
}