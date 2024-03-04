package io.tanlnm.my.weather.presentation.features.search

import io.tanlnm.my.weather.core.platform.ICommand
import io.tanlnm.my.weather.core.platform.IEffect
import io.tanlnm.my.weather.core.platform.IState
import io.tanlnm.my.weather.data.model.Weather

sealed class SearchContract {
    sealed interface Command : ICommand {
        data class Search(val query: String) : Command
    }

    data class State(
        val weather: Weather?
    ) : IState

    sealed interface Effect : IEffect {
        data class ShowErrorMessage(val message: String) : Effect
    }
}