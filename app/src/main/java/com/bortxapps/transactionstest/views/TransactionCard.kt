package com.bortxapps.transactionstest.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bortxapps.data.TransactionPoko
import com.bortxapps.transactionstest.ui.theme.TransactionsTestTheme
import java.text.DateFormat
import java.util.Date

@Composable
fun TransactionCard(transaction: TransactionPoko, remarks: Boolean) {
    Card(
            border = if (remarks) BorderStroke(2.dp, Color.Gray) else null,
            elevation = 5.dp,
            modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp, 10.dp)
                    .testTag("main_screen_transactions_columns_card")
    ) {
        ConstraintLayout {

            val (dateIcon, dateText, amountText, feeText) = createRefs()

            Icon(
                    Icons.Rounded.DateRange,
                    contentDescription = "Date",
                    modifier = Modifier
                            .height(16.dp)
                            .width(16.dp)
                            .constrainAs(dateIcon) {
                                top.linkTo(parent.top, margin = 6.dp)
                                start.linkTo(parent.start, margin = 5.dp)
                            })

            Text(
                    text = DateFormat.getDateInstance(DateFormat.SHORT).format(transaction.date!!),
                    fontSize = 14.sp,
                    modifier = Modifier.constrainAs(dateText) {
                        top.linkTo(parent.top, margin = 5.dp)
                        start.linkTo(dateIcon.end, margin = 5.dp)
                    }
            )


            Row(modifier = Modifier.constrainAs(amountText) {
                top.linkTo(dateText.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 20.dp)
            }) {
                Image(
                        modifier = Modifier
                                .padding(top = 3.dp)
                                .height(20.dp)
                                .width(20.dp)
                                .drawBehind {
                                    if (transaction.isIncome())
                                        drawCircle(Color.Green)
                                    else
                                        drawCircle(Color.Red)
                                }
                                .testTag(
                                        if (transaction.isIncome())
                                            "income_icon"
                                        else
                                            "expense_icon"
                                ),
                        imageVector = if (transaction.isIncome())
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,
                        contentDescription = "Income"
                )

                Text(
                        modifier = Modifier.padding(start = 8.dp, bottom = 5.dp),
                        text = "${transaction.amount}€",
                        fontSize = 18.sp
                )
            }

            Text(text = "Fee: ${transaction.fee}€",
                    modifier = Modifier
                            .padding(bottom = 5.dp)
                            .constrainAs(feeText) {
                                top.linkTo(dateText.bottom, margin = 8.dp)
                                end.linkTo(parent.end, margin = 20.dp)
                            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TransactionsTestTheme {
        TransactionCard(
                TransactionPoko(
                        id = 10,
                        date = Date(),
                        amount = 193.38,
                        fee = -3.18,
                        description = "Lorem ipsum dolor sit amet"
                ), true
        )
    }
}