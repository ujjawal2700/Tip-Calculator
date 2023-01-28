package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TIpCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TIpCalculatorTheme {
                TipCalc()
            }
        }
    }
}

@Composable
fun TipCalc() {

    var valueForBill by remember {
        mutableStateOf("")
    }
    var valueForTip by remember {
        mutableStateOf("")
    }
    var roundUp by remember {
        mutableStateOf(false)
    }
    val amount = valueForBill.toDoubleOrNull() ?: 0.0
    val tip = valueForTip.toDoubleOrNull() ?: 0.0
    val focusManager = LocalFocusManager.current



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(R.string.Calculate_Tip), fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = valueForBill,
            onValueChange = { valueForBill = it },
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.Bill_Amount))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = valueForTip,
            onValueChange = { valueForTip = it },
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.Tip_Amount))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )


        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {

            Text(text = "Round up tip?", fontSize = 15.sp, modifier = Modifier.padding(start = 57.dp))

            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it },

                modifier = Modifier.padding(end = 55.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End),
            )

        }
        Spacer(modifier = Modifier.height(20.dp))

        val finalTip = calculatingTip(billAmount = amount, tipPercentage = tip, roundUp)

        Text(text = stringResource(id = R.string.Tip_Amount) + " $finalTip", fontSize = 25.sp, fontWeight = FontWeight.Bold)


    }
}

private fun calculatingTip(billAmount: Double, tipPercentage: Double, roundUp : Boolean): String {
    var tip =  billAmount / 100 * tipPercentage
    if(roundUp){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}


@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview() {
    TipCalc()
}