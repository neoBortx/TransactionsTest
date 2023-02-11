package com.bortxapps.transactionstest

import com.bortxapps.data.TransactionPoko
import com.bortxapps.transactionstest.viewmodels.MainState
import com.bortxapps.transactionstest.viewmodels.TransactionsViewModel
import com.bortxapps.usercases.GetAllTransactionsUserCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Date
import kotlin.random.Random

class TransactionsViewModelTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @MockK
    lateinit var getAllTransactionsUserCase: GetAllTransactionsUserCase
    lateinit var transactionsViewModel: TransactionsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun start() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `test initial estate expect IDLE`() {
        transactionsViewModel = spyk(TransactionsViewModel(getAllTransactionsUserCase))

        assertTrue(transactionsViewModel.state is MainState.Idle)
    }

    @Test
    fun `no data expect empty estate`() {
        coEvery { getAllTransactionsUserCase() } returns flow {
            emit(emptyList())
        }

        transactionsViewModel = spyk(TransactionsViewModel(getAllTransactionsUserCase))
        transactionsViewModel.loadData()
        assertTrue(transactionsViewModel.state is MainState.RequestingData)
        Thread.sleep(2000)
        assertTrue(transactionsViewModel.state is MainState.ListReceived)
        assertTrue((transactionsViewModel.state as MainState.ListReceived).items.isEmpty())
    }

    @Test
    fun `test initial state`() {
        coEvery { getAllTransactionsUserCase() } returns flow {
            emit(generateList())
        }

        transactionsViewModel = spyk(TransactionsViewModel(getAllTransactionsUserCase))
        transactionsViewModel.loadData()
        assertTrue(transactionsViewModel.state is MainState.RequestingData)
        Thread.sleep(2000)
        assertTrue(transactionsViewModel.state is MainState.ListReceived)
        assertEquals(49, (transactionsViewModel.state as MainState.ListReceived).items.count())
    }

    private fun generateList(): List<TransactionPoko> {
        return (1..49).map {
            TransactionPoko(it, Date(), Random.nextDouble(), Random.nextDouble(), "Description $it")
        }.toList()
    }
}