package com.retrocalc.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var display by remember { mutableStateOf("0") }
    var currentNum by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    var prevNum by remember { mutableStateOf("") }
    
    fun calculate() {
        if (prevNum.isNotEmpty() && currentNum.isNotEmpty()) {
            val result = when(operator) {
                "+" -> prevNum.toDouble() + currentNum.toDouble()
                "-" -> prevNum.toDouble() - currentNum.toDouble()
                "×" -> prevNum.toDouble() * currentNum.toDouble()
                "÷" -> prevNum.toDouble() / currentNum.toDouble()
                else -> 0.0
            }
            display = if (result % 1.0 == 0.0) result.toInt().toString() else result.toString()
            currentNum = display
            prevNum = ""
            operator = ""
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC0C0C0))
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.Black)
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                display,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = Color(0xFF00FF00)
            )
        }
        
        Spacer(Modifier.height(16.dp))
        
        val buttons = listOf(
            listOf("7", "8", "9", "÷"),
            listOf("4", "5", "6", "×"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+")
        )
        
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { btn ->
                    Button(
                        onClick = {
                            when(btn) {
                                "C" -> { display = "0"; currentNum = ""; prevNum = ""; operator = "" }
                                "=" -> calculate()
                                in listOf("+", "-", "×", "÷") -> {
                                    if (currentNum.isNotEmpty()) {
                                        prevNum = currentNum
                                        currentNum = ""
                                        operator = btn
                                    }
                                }
                                else -> {
                                    currentNum = if (currentNum == "0") btn else currentNum + btn
                                    display = currentNum
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when(btn) {
                                "=" -> Color(0xFF0000AA)
                                in listOf("+", "-", "×", "÷") -> Color(0xFFFFAA00)
                                else -> Color(0xFFDFDFDF)
                            }
                        )
                    ) {
                        Text(
                            btn,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (btn == "=" || btn in listOf("+", "-", "×", "÷")) Color.White else Color.Black
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}
