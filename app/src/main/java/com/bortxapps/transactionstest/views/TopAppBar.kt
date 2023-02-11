package com.bortxapps.transactionstest.views

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bortxapps.transactionstest.R

@Composable
fun TopAppBarCustom() {
    TopAppBar(
            title = { Text(stringResource(id = R.string.app_name)) },
    )
}
