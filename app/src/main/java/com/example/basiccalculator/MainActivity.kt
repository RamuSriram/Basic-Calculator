package com.example.basiccalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basiccalculator.ui.theme.BasicCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    var curVal by remember { mutableStateOf("") }
    var curResult by remember { mutableStateOf(0.0) }
    var temp1 by remember { mutableStateOf("") }
    var temp2 by remember { mutableStateOf("") }
    var clickedOperator by remember { mutableStateOf(false) }
    var operator by remember { mutableStateOf(' ') }
    val hapticFeedback = LocalHapticFeedback.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "My Calculator", fontWeight = FontWeight.Bold, modifier = Modifier.padding(50.dp), fontSize = 32.sp)
        Column {
            Text(text = "Expression: $curVal", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Result: $curResult", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(50.dp))

        Row {
            Button(onClick = {
                curVal = ""
                curResult = 0.0
                temp1 = ""
                temp2 = ""
                clickedOperator = false
                operator = ' '
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            }) {
                Text(text = "Reset")
            }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = {
                if (curVal.isNotEmpty()) {
                    if (clickedOperator) {
                        if (temp2.isNotEmpty()) {
                            temp2 = temp2.dropLast(1)
                        } else {
                            clickedOperator = false
                            operator = ' '
                        }
                    } else {
                        temp1 = temp1.dropLast(1)
                    }
                    curVal = curVal.dropLast(1)
                }
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            }) {
                Text(text = "BackSpace")
            }
        }

        fun appendNumber(digit: String) {
            curVal += digit
            if (clickedOperator) {
                temp2 += digit
            } else {
                temp1 += digit
            }
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        }

        Row {
            Button(onClick = { appendNumber("1") }) { Text(text = "1") }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = { appendNumber("2") }) { Text(text = "2") }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = { appendNumber("3") }) { Text(text = "3") }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = {
                if (!clickedOperator && temp1.isNotEmpty()) {
                    curVal += "+"
                    clickedOperator = true
                    operator = '+'
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }) { Text(text = "+") }
        }

        Row {
            Button(onClick = { appendNumber("4") }) { Text(text = "4") }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = { appendNumber("5") }) { Text(text = "5") }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = { appendNumber("6") }) { Text(text = "6") }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = {
                if (!clickedOperator && temp1.isNotEmpty()) {
                    curVal += "-"
                    clickedOperator = true
                    operator = '-'
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }) { Text(text = "-") }
        }

        Row {
            Button(onClick = { appendNumber("7") }) { Text(text = "7") }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = { appendNumber("8") }) { Text(text = "8") }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = { appendNumber("9") }) { Text(text = "9") }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = {
                if (!clickedOperator && temp1.isNotEmpty()) {
                    curVal += "*"
                    clickedOperator = true
                    operator = '*'
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }) { Text(text = "X") }
        }

        Row {
            Button(onClick = {
                if (!curVal.endsWith('.') && !clickedOperator) {
                    if (temp1.isEmpty()) {
                        curVal += "0."
                        temp1 += "0."
                    } else {
                        curVal += "."
                        temp1 += "."
                    }
                } else if (!curVal.endsWith('.') && clickedOperator) {
                    if (temp2.isEmpty()) {
                        curVal += "0."
                        temp2 += "0."
                    } else {
                        curVal += "."
                        temp2 += "."
                    }
                }
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            }) {
                Text(text = ".")
            }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = { appendNumber("0") }) { Text(text = "0") }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = {
                if (clickedOperator && temp2.isNotEmpty()) {
                    when (operator) {
                        '+' -> curResult = temp1.toDouble() + temp2.toDouble()
                        '-' -> curResult = temp1.toDouble() - temp2.toDouble()
                        '*' -> curResult = temp1.toDouble() * temp2.toDouble()
                        '/' -> curResult = if (temp2.toDouble() != 0.0) temp1.toDouble() / temp2.toDouble() else Double.NaN
                    }
                    curVal = curResult.toString()
                    temp1 = curResult.toString()
                    temp2 = ""
                    clickedOperator = false
                    operator = ' '
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }, colors = ButtonDefaults.buttonColors(Color.Red)) {
                Text(text = "=")
            }
            Spacer(modifier = Modifier.width(15.dp))
            Button(onClick = {
                if (!clickedOperator && temp1.isNotEmpty()) {
                    curVal += "/"
                    clickedOperator = true
                    operator = '/'
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }) { Text(text = "/") }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalculatorPreview() {
    Calculator()
}