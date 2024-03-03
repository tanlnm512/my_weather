package io.tanlnm.my.weather.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Weather(
    val id: Long,
    val name: String,
    @SerializedName("coord") val coordinator: Coordinator,
    @SerializedName("weather") val weatherInfos: List<WeatherInfo>,
    val base: String,
    @SerializedName("main") val mainInfo: MainInfo,
    @SerializedName("wind") val windInfo: WindInfo,
    @SerializedName("sys") val sysInfo: SysInfo,
    val visibility: Int,
    val clouds: Clouds,
    val dt: Long,
    val timezone: Long,

    // Base Params
    val cod: Int,
    val message: String?,
) {
    val isSuccess: Boolean
        get() = cod in 200..299
}

@Keep
data class Coordinator(
    val lat: Double,
    val lon: Double,
)

@Keep
data class WeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Keep
data class MainInfo(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Double,
    val humidity: Double,
)

@Keep
data class SysInfo(
    val id: Long,
    val type: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long,
)

@Keep
data class WindInfo(
    val speed: Double,
    val deg: Int
)

@Keep
data class Clouds(
    val all: Int
)