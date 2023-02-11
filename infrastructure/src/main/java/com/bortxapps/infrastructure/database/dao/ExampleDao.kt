package com.bortxapps.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bortxapps.infrastructure.database.entities.TransactionEntity

//TODO In a real world application common actions like insert, list all, delete elements
//will be placed in a BaseDao<T> Class generalized
@Dao
interface TransactionsDao {

    @Transaction
    suspend fun addAll(entities: List<TransactionEntity>) {
        clearTable()
        insertAll(entities)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<TransactionEntity>)

    @Query("DELETE FROM transactions")
    suspend fun clearTable()

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionEntity>

}