package io.tanlnm.my.weather.presentation.features.home

import io.tanlnm.my.weather.core.platform.ICommand
import io.tanlnm.my.weather.core.platform.IEffect
import io.tanlnm.my.weather.core.platform.IState

sealed class HomeContract {
    sealed interface Command : ICommand {

    }

    data class State(
        val loading: Boolean
    ) : IState

    sealed interface Effect : IEffect {

    }
}