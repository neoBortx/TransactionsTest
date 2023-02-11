package com.bortxapps.infrastructure.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bortxapps.data.TransactionPoko
import com.bortxapps.infrastructure.utils.toDataBaseString
import com.bortxapps.infrastructure.utils.toDate

@Entity(tableName = "transactions")
data class TransactionEntity(
        @PrimaryKey(autoGenerate = true)
        val dataBaseId: Long = 0L,
        val id: Int,
        val date: String,
        val amount: Double,
        val fee: Double,
        val description: String,
) {
    fun toPoko() = TransactionPoko(id, date.toDate(), amount, fee, description)
}

fun TransactionPoko.fromPoko() =
        TransactionEntity(
                id = this.id,
                date = this.date?.toDataBaseString() ?: "",
                amount = this.amount,
                fee = this.fee,
                description = this.description
        )



