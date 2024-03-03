package io.tanlnm.my.weather.core.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.tanlnm.my.weather.core.utils.NetworkHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    @Singleton
    @Provides
    fun provideNetworkHandler(@ApplicationContext context: Context) = NetworkHandler(context)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setDateFormat(DATE_TIME_FORMAT)
        .setLenient()
        .create()
}