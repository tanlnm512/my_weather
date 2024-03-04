package io.tanlnm.my.weather.presentation.features.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.tanlnm.my.weather.core.platform.ICommand
import io.tanlnm.my.weather.core.platform.MVIViewModel
import io.tanlnm.my.weather.domain.usecase.SearchWeatherByCityUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchWeatherByCityUseCase: SearchWeatherByCityUseCase
) : MVIViewModel<SearchContract.Command, SearchContract.State, SearchContract.Effect>() {
    override suspend fun handleCommand(command: ICommand) {
        when (command) {
            is SearchContract.Command.Search -> searchWeatherByCity(command.query)
        }
    }

    private fun searchWeatherByCity(query: String) {
        searchWeatherByCityUseCase(SearchWeatherByCityUseCase.Params(query))
            .onEach { result ->
                _state.update { SearchContract.State(result.getOrNull()) }

                if (result.isFailure)
                    setEffect(
                        SearchContract.Effect.ShowErrorMessage(
                            result.exceptionOrNull()?.message ?: "Error!"
                        )
                    )
                else if (result.getOrNull()?.isSuccess == false)
                    setEffect(
                        SearchContract.Effect.ShowErrorMessage(
                            result.getOrNull()?.message ?: "Error!"
                        )
                    )
            }
            .launchIn(viewModelScope)
    }
}