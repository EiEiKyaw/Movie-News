package com.android.tutorial.couch_potato.util

import okhttp3.Interceptor
import okhttp3.Response

class AppKeyInterceptor(
    private val appKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val url = original.url.newBuilder()
            .addQueryParameter("appkey", appKey)
            .build()

        val newRequest = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}