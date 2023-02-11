package com.bortxapps.infrastructure.remote.data

import com.bortxapps.data.TransactionPoko
import com.bortxapps.infrastructure.utils.toDate

data class TransactionRemote(
        val id: Int,
        val date: String?,
        val amount: Double?,
        val fee: Double?,
        val description: String?,
) {
    fun toPoko() = TransactionPoko(
            id,
            date?.toDate(),
            amount ?: 0.0,
            fee ?: 0.0,
            description ?: ""
    )
}
