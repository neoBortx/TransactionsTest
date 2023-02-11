package com.bortxapps.transactionstest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import com.bortxapps.transactionstest.ui.theme.TransactionsTestTheme
import com.bortxapps.transactionstest.views.MainScreen
import com.bortxapps.transactionstest.views.TopAppBarCustom
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TransactionsTestTheme {
                Scaffold(
                        topBar = { TopAppBarCustom() }
                ) {
                    MainScreen()
                }
            }
        }
    }
}

