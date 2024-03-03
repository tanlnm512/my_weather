package io.tanlnm.my.weather.core.interactor

interface UseCase<out Output, in Params> where Output : Any {
    operator fun invoke(params: Params): Output
    class None
}