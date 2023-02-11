package com.bortxapps.infrastructure.remote.api

import com.bortxapps.infrastructure.remote.data.TransactionRemote
import retrofit2.Call
import retrofit2.http.GET

interface TransactionApi {
    @GET("transactions.json")
    fun getTransactions(): Call<List<TransactionRemote>>
}