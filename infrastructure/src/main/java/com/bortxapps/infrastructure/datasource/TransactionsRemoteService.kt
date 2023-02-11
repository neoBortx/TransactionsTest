package com.bortxapps.infrastructure.datasource

import com.bortxapps.infrastructure.remote.api.TransactionApi
import javax.inject.Inject

class TransactionsRemoteService @Inject constructor(private val api: TransactionApi) {

    fun getAll() = try {
        api.getTransactions().execute().body()?.map { it.toPoko() } ?: emptyList()
    } catch (ex: Exception) {
        emptyList()
    }
}