package com.bortxapps.usercases

import com.bortxapps.data.TransactionPoko
import com.bortxapps.respository.TransactionRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Date


class GetAllTransactionsUserCaseTest {

    @MockK
    lateinit var transactionRepository: TransactionRepository

    lateinit var getAllTransactionsUserCase: GetAllTransactionsUserCase

    @Before
    fun start() {
        MockKAnnotations.init(this)
        getAllTransactionsUserCase = GetAllTransactionsUserCase(transactionRepository)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `put non valid dates expect discarded`() = runTest {

        val transactions = listOf(
                TransactionPoko(1, null, 56.00, 4.00, "one"),
                TransactionPoko(2, Date(), 56.00, 4.00, "two"),
                TransactionPoko(3, null, 56.00, 4.00, "three"),
        )
        coEvery { transactionRepository.getAll() } returns flow { emit(transactions) }

        var result = listOf<TransactionPoko>()


        getAllTransactionsUserCase.invoke().collect {
            result = it
        }

        assertEquals(1, result.count())
        assertEquals(transactions[1], result.first())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `put repeated transactions expect filtered`() = runTest {
        val transactions = listOf(
                TransactionPoko(1, Date(2020, 0, 5, 0, 5, 6), 56.00, 4.00, "one"),
                TransactionPoko(1, Date(2022, 0, 6, 0, 5, 6), 56.00, 4.00, "three"),
                TransactionPoko(1, Date(2020, 0, 7, 0, 5, 6), 56.00, 4.00, "three"),
        )
        coEvery { transactionRepository.getAll() } returns flow { emit(transactions) }

        var result = listOf<TransactionPoko>()

        getAllTransactionsUserCase.invoke().collect {
            result = it
        }

        assertEquals(1, result.count())
        assertEquals(transactions[1], result.first())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `put empty list expect no crashes`() = runTest {
        val transactions = listOf<TransactionPoko>()
        coEvery { transactionRepository.getAll() } returns flow { emit(transactions) }

        var result = listOf<TransactionPoko>()

        getAllTransactionsUserCase.invoke().collect {
            result = it
        }

        assertTrue(result.isEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `put transactions in random order expect ordered in a descending way`() = runTest {

        val transactions = listOf(
                TransactionPoko(1, Date(2020, 0, 5, 0, 5, 6), 56.00, 4.00, "one"),
                TransactionPoko(2, Date(2022, 0, 6, 0, 5, 6), 56.00, 4.00, "three"),
                TransactionPoko(3, Date(2021, 0, 7, 0, 5, 6), 56.00, 4.00, "three"),
        )


        coEvery { transactionRepository.getAll() } returns flow { emit(transactions) }

        var result = listOf<TransactionPoko>()

        getAllTransactionsUserCase.invoke().collect {
            result = it
        }

        assertEquals(transactions[1], result[0])
        assertEquals(transactions[2], result[1])
        assertEquals(transactions[0], result[2])
    }
}