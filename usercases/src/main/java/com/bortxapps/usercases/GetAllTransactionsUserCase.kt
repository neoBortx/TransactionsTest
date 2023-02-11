package com.bortxapps.usercases

import com.bortxapps.data.TransactionPoko
import com.bortxapps.respository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTransactionsUserCase @Inject constructor(private val transactionsRepository: TransactionRepository) {

    /**
     * Gets the whole list of transactions, then applies the business rules like
     * - Not show invalid dates
     * - Order by date
     * - When two elements have the same ID, get the latest
     */
    suspend operator fun invoke(): Flow<List<TransactionPoko>> = transactionsRepository.getAll().map {
        it.filter { transaction -> transaction.date != null }
                .sortedByDescending { transaction -> transaction.date }
                .groupBy { transaction -> transaction.id }
                .map { (_, v) -> v.first() }
    }
}