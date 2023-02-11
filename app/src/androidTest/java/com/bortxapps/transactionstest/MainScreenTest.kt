package com.bortxapps.transactionstest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bortxapps.data.TransactionPoko
import com.bortxapps.transactionstest.ui.theme.TransactionsTestTheme
import com.bortxapps.transactionstest.viewmodels.MainState
import com.bortxapps.transactionstest.viewmodels.TransactionsViewModel
import com.bortxapps.transactionstest.views.MainScreen
import com.bortxapps.transactionstest.views.TransactionCard
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import kotlin.random.Random

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainScreenTest {

    @BindValue
    var transactionsViewModel = mockk<TransactionsViewModel>(relaxed = true)

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun home_state_loading_expect_spinner_shown() {
        every { transactionsViewModel.state } returns MainState.RequestingData

        composeTestRule.setContent {
            TransactionsTestTheme() {
                MainScreen(viewModel = transactionsViewModel)
            }
        }

        composeTestRule.onNodeWithTag("main_screen_loading_spinner").assertIsDisplayed()
    }

    @Test
    fun home_state_idle_expect_spinner_shown() {
        every { transactionsViewModel.state } returns MainState.Idle

        composeTestRule.setContent {
            TransactionsTestTheme() {
                MainScreen(viewModel = transactionsViewModel)
            }
        }

        composeTestRule.onNodeWithTag("main_screen_loading_spinner").assertIsDisplayed()
    }

    @Test
    fun home_state_received_data_with_empty_data_expect_empty_list() {
        every { transactionsViewModel.state } returns MainState.ListReceived(emptyList())

        composeTestRule.setContent {
            TransactionsTestTheme() {
                MainScreen(viewModel = transactionsViewModel)
            }
        }

        composeTestRule.waitUntil(2000) {
            composeTestRule.onAllNodesWithTag("main_screen_no_transactions")
                    .fetchSemanticsNodes().size == 1
        }
    }

    @Test
    fun home_state_received_data_with_data_expect_list() {
        val testList = generateList()

        every { transactionsViewModel.state } returns MainState.ListReceived(testList)

        composeTestRule.setContent {
            TransactionsTestTheme() {
                MainScreen(viewModel = transactionsViewModel)
            }
        }

        composeTestRule.waitUntil(2000) {
            composeTestRule.onAllNodesWithTag("main_screen_transactions_columns")
                    .fetchSemanticsNodes().size == 1
        }

    }

    @Test
    fun card_with_an_income_expect_income_icon() {
        val testList = generateList()

        every { transactionsViewModel.state } returns MainState.ListReceived(testList)

        composeTestRule.setContent {
            TransactionsTestTheme() {
                TransactionCard(TransactionPoko(1, Date(), 5.00, 5.00, ""), false)
            }
        }

        composeTestRule.waitUntil(2000) {
            composeTestRule.onAllNodesWithTag("income_icon")
                    .fetchSemanticsNodes().size == 1
        }

    }

    @Test
    fun card_with_an_expense_expect_expense_icon() {
        val testList = generateList()

        every { transactionsViewModel.state } returns MainState.ListReceived(testList)

        composeTestRule.setContent {
            TransactionsTestTheme() {
                TransactionCard(TransactionPoko(1, Date(), -5.00, 5.00, ""), false)
            }
        }

        composeTestRule.waitUntil(2000) {
            composeTestRule.onAllNodesWithTag("expense_icon")
                    .fetchSemanticsNodes().size == 1
        }

    }

    private fun generateList(): List<TransactionPoko> {
        return (1..50).map {
            TransactionPoko(it, Date(), Random.nextDouble(), Random.nextDouble(), "Description $it")
        }.toList()
    }
}