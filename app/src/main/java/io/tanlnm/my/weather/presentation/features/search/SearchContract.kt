package io.tanlnm.my.weather.presentation.features.search

import io.tanlnm.my.weather.core.platform.ICommand
import io.tanlnm.my.weather.core.platform.IEffect
import io.tanlnm.my.weather.core.platform.IState

sealed class SearchContract {
    sealed interface Command : ICommand {

    }

    data class State(
        val loading: Boolean
    ) : IState

    sealed interface Effect : IEffect {

    }
}