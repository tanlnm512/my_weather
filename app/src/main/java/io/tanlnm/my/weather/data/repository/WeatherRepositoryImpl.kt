package io.tanlnm.my.weather.data.repository

import io.tanlnm.my.weather.BuildConfig
import io.tanlnm.my.weather.data.api.WeatherApi
import io.tanlnm.my.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override fun searchWeatherByCity(query: String, units: String) = flow {
        try {
            val result = weatherApi.searchWeatherByCity(query, units, BuildConfig.API_KEY)
            emit(Result.success(result))
        } catch (e: Exception) {
            Timber.e(e)
            emit(Result.failure(e))
        }
    }

    override fun searchWeatherByCityId(id: String, units: String) = flow {
        try {
            val result = weatherApi.searchWeatherByCityId(id, units, BuildConfig.API_KEY)
            emit(Result.success(result))
        } catch (e: Exception) {
            Timber.e(e)
            emit(Result.failure(e))
        }
    }

}