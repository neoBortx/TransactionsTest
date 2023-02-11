package com.bortxapps.transactionstest.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bortxapps.usercases.GetAllTransactionsUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

//TODO We can move the views and the activities to another module
@HiltViewModel
class TransactionsViewModel @Inject constructor(private val getAllTransactionsUserCase: GetAllTransactionsUserCase) : ViewModel() {

    //TODO in this example there isn't any user interaction, so we haven't implemented any intent
    //but in a MVI implementation over MVVM we should have a bunch of intents here
    var state by mutableStateOf<MainState>(MainState.Idle)
        private set

    init {
        loadData()
    }

    fun loadData() {
        state = MainState.RequestingData
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                runBlocking {
                    getAllTransactionsUserCase().collect {
                        state = MainState.ListReceived(it)
                    }
                }
            }
        }
    }
}