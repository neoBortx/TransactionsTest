package com.bortxapps.transactionstest.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bortxapps.data.TransactionPoko
import com.bortxapps.transactionstest.R
import com.bortxapps.transactionstest.viewmodels.MainState
import com.bortxapps.transactionstest.viewmodels.TransactionsViewModel

@Composable
fun MainScreen(viewModel: TransactionsViewModel = hiltViewModel()) {

    Column {
        when (viewModel.state) {
            is MainState.ListReceived -> MainWindow((viewModel.state as MainState.ListReceived).items)
            else -> LoadingSpinner()
        }
    }
}

@Composable
fun LoadingSpinner() {
    Column(
            modifier = Modifier
                    .fillMaxSize()
                    .testTag("main_screen_loading_spinner"),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }

}

@Composable
fun MainWindow(list: List<TransactionPoko>) {
    if (list.any()) {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .testTag("main_screen_transactions_columns"),
        ) {

            Text(
                    text = stringResource(R.string.last_transaction),
                    modifier = Modifier.padding(top = 20.dp, bottom = 2.dp, start = 10.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
            )
            TransactionCard(transaction = list.first(), remarks = true)
            Text(
                    text = stringResource(R.string.historical),
                    modifier = Modifier.padding(top = 10.dp, bottom = 2.dp, start = 10.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
            )
            LazyColumn {
                items(items = list.drop(1)) { TransactionCard(it, false) }
            }
        }
    } else {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .testTag("main_screen_no_transactions"),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.there_aren_t_any_transaction_available))
        }
    }
}
