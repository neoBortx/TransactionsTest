package com.bortxapps.infrastructure.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bortxapps.infrastructure.database.dao.TransactionsDao
import com.bortxapps.infrastructure.database.entities.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1)
abstract class TransactionsDataBase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao

    companion object {
        const val DATABASE_NAME = "transactionsTest.db"
    }
}