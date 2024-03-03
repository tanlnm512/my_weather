package io.tanlnm.my.weather.core.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.tanlnm.my.weather.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L

    @Singleton
    @Provides
    fun provideOkHttpCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, (25 * 1024 * 1024).toLong())

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").i(message)
        }.apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS
            else HttpLoggingInterceptor.Level.NONE
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        cache: Cache,
        logging: HttpLoggingInterceptor,
        headerInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        interceptors().addAll(listOf(logging, headerInterceptor))
        cache(cache)
    }.build()

    @Singleton
    @Provides
    fun provideAppRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().apply {
        addConverterFactory(GsonConverterFactory.create(Gson()))
        baseUrl(BuildConfig.BASE_API_URL)
        client(okHttpClient)
    }.build()
}