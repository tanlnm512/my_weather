package io.tanlnm.my.weather.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.tanlnm.my.weather.data.repository.WeatherLocalRepositoryImpl
import io.tanlnm.my.weather.data.repository.WeatherRepositoryImpl
import io.tanlnm.my.weather.domain.repository.WeatherLocalRepository
import io.tanlnm.my.weather.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Singleton
    @Binds
    abstract fun bindWeatherLocalRepository(impl: WeatherLocalRepositoryImpl): WeatherLocalRepository
}