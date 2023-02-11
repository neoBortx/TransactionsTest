package com.bortxapps.data

import java.util.Date

data class TransactionPoko(
        val id: Int,
        val date: Date?,
        val amount: Double,
        val fee: Double,
        val description: String
) {
    fun isIncome() = amount > 0
    fun isExpense() = amount < 0
}