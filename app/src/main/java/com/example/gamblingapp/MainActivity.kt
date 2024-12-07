package com.example.gamblingapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gamblingapp.ui.theme.GamblingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        //Getting the user balance from SharedPreferences

        val sharedPreferences: SharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val userBalance = sharedPreferences.getInt("userBalance", 0)


        setContent {
            GamblingAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    BalanceScreen(
                        balance =  userBalance,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BalanceScreen(balance:Int,modifier: Modifier = Modifier){
    Text(
        text = "Balance: $$balance",
        modifier = modifier
    )
}
@Preview
@Composable
fun BalanceScreenPreview() {
    GamblingAppTheme { BalanceScreen(1000) }
}

