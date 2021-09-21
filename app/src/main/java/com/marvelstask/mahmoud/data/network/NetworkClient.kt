package com.marvelstask.mahmoud.data.network

import com.marvelstask.mahmoud.BuildConfig
import com.marvelstask.mahmoud.core.Config
import com.marvelstask.mahmoud.data.auth.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient private constructor() {

    private lateinit var retrofit: Retrofit
    private val authenticator: Authenticator = Authenticator()
    private val httpClient = OkHttpClient.Builder()

    private val client = httpClient.connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(180, TimeUnit.SECONDS)
        .connectTimeout(180, TimeUnit.SECONDS)
        .writeTimeout(180, TimeUnit.SECONDS)
        .addInterceptor(authenticator)
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    level = if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE
                })
        .build()

    private fun build(){
        retrofit = Retrofit.Builder()
            .baseUrl(Config.HOST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun apiService(): ApiService {
        build()
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Retrofit single instance class with all services of the application
     */
    companion object {
        private var instance: NetworkClient? = null

        @Synchronized
        fun getInstance(): NetworkClient {

            if (instance == null) {
                instance = NetworkClient()
            }

            return instance as NetworkClient
        }
    }

}