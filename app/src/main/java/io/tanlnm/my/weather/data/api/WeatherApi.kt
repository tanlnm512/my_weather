package io.tanlnm.my.weather.data.api

import io.tanlnm.my.weather.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    /**
     * units: standard , metric , imperial
     */
    @GET("data/2.5/weather")
    suspend fun searchWeatherByCity(
        @Query("q") query: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String,
    ): Weather

    @GET("data/2.5/weather")
    suspend fun searchWeatherByCityId(
        @Query("id") cityId: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String,
    ): Weather
}