package io.tanlnm.my.weather.core.utils

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder().apply {
            header("cache-control", "no-cache")
            header("accept", "application/json")
        }

        return chain.proceed(requestBuilder.build())
    }
}