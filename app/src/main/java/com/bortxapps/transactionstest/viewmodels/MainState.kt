package com.bortxapps.transactionstest.viewmodels

import com.bortxapps.data.TransactionPoko

sealed class MainState {
    object Idle : MainState()
    object RequestingData : MainState()
    class ListReceived(val items: List<TransactionPoko>) : MainState()
}


