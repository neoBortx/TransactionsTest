package com.bortxapps.infrastructure.remote.service

import com.bortxapps.infrastructure.remote.api.TransactionApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TransactionsService {

    private const val BASE_URL = "https://code-challenge-e9f47.web.app/"

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }
    val api: TransactionApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(TransactionApi::class.java)
}