package com.android.tutorial.couch_potato.rest

import com.android.tutorial.couch_potato.util.AppKeyInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {
    companion object {
        fun getApiService(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .client(getClient())
                .addConverterFactory(getConverter())
                .build()
            return retrofit.create(ApiService::class.java)
        }

        private fun getClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val apiKeyInterceptor = AppKeyInterceptor("9494436b")

            return OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    chain.proceed(chain.request())
                }
                .callTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
        }

        private fun getConverter(): GsonConverterFactory {
            val gsonData = GsonBuilder()
                .create()

            return GsonConverterFactory.create(gsonData)
        }
    }
}