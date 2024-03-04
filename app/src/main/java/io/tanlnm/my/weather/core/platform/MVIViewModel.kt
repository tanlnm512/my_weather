package io.tanlnm.my.weather.core.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class MVIViewModel<C : ICommand, S : IState, E : IEffect> : ViewModel() {
    protected val _state = MutableStateFlow<IState>(InitialState)
    val state: Flow<IState> = _state.asStateFlow()

    private val _command = MutableSharedFlow<C>()
    private val command: Flow<C> = _command.asSharedFlow()

    private val _effect = Channel<E>()
    val effect: Flow<E> = _effect.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            command.collect { handleCommand(it) }
        }
    }

    // region Command
    abstract suspend fun handleCommand(command: ICommand)

    fun sendCommand(command: C) {
        viewModelScope.launch { _command.emit(command) }
    }
    // endregion

    // region Effect
    protected suspend fun setEffect(effect: E) {
        _effect.send(effect)
    }
    // endregion

    // region Layout Indicator
    private val _layoutIndicator = Channel<LayoutIndicator>()
    val layoutIndicator = _layoutIndicator.receiveAsFlow()

    sealed interface LayoutIndicator {
        data class Loading(val isLoading: Boolean) : LayoutIndicator
        data object NoInternetConnection : LayoutIndicator
        data class ShowErrorMessage(val msg: String) : LayoutIndicator
    }

    fun showLoading(isLoading: Boolean) {
        viewModelScope.launch { _layoutIndicator.send(LayoutIndicator.Loading(isLoading)) }
    }
    // endregion
}