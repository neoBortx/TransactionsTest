package com.bortxapps.infrastructure.datasource

import com.bortxapps.data.TransactionPoko
import com.bortxapps.infrastructure.database.database.TransactionsDataBase
import com.bortxapps.infrastructure.database.entities.fromPoko
import javax.inject.Inject

class TransactionsLocalDataSource @Inject constructor(private val dataBase: TransactionsDataBase) {

    private val dao = dataBase.transactionsDao()

    suspend fun addBatch(entities: List<TransactionPoko>) {
        dao.addAll(entities.map { it.fromPoko() })
    }

    suspend fun getAll(): List<TransactionPoko> =
            dao.getAll().map { it.toPoko() }
}