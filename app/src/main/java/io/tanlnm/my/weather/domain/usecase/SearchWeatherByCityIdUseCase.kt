package io.tanlnm.my.weather.domain.usecase

import io.tanlnm.my.weather.core.interactor.UseCase
import io.tanlnm.my.weather.data.model.Weather
import io.tanlnm.my.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchWeatherByCityIdUseCase @Inject constructor(
    private val repository: WeatherRepository
) : UseCase<Flow<Result<Weather>>, SearchWeatherByCityIdUseCase.Params> {

    override fun invoke(params: Params): Flow<Result<Weather>> =
        repository.searchWeatherByCityId(params.id, params.units)

    data class Params(val id: String, val units: String = "metric")
}