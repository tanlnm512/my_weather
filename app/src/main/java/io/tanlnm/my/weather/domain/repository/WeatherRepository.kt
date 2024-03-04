package io.tanlnm.my.weather.domain.repository

import io.tanlnm.my.weather.data.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun searchWeatherByCity(query: String, units: String): Flow<Result<Weather>>
    fun searchWeatherByCityId(id: String, units: String): Flow<Result<Weather>>
}