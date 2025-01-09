package com.example.gamblingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamblingapp.ui.theme.GamblingAppTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

class DiceGameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GamblingAppTheme {
                DiceGameScreen()
            }
        }
    }
}
@Composable
fun DiceGameScreen() {
    var playerBalance by remember { mutableStateOf(100) }
    var playerDice by remember { mutableStateOf(1) }
    var computerDice by remember { mutableStateOf(1) }
    var resultMessage by remember { mutableStateOf("Roll the dice to play!") }
    var isRolling by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Balance: $playerBalance", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = getDiceImage(playerDice)),
                contentDescription = "Player Dice",
                modifier = Modifier.size(100.dp)
            )
            Image(
                painter = painterResource(id = getDiceImage(computerDice)),
                contentDescription = "Computer Dice",
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = resultMessage, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isRolling = true
                resultMessage = "Rolling..."
            },
            enabled = playerBalance > 0 && !isRolling
        ) {
            Text(text = if (isRolling) "Rolling..." else "Roll Dice")
        }


        if (isRolling) {
            LaunchedEffect(Unit) {
                repeat(10) {
                    playerDice = rollDice()
                    computerDice = rollDice()
                    delay(100)
                }


                val finalPlayerDice = rollDice()
                val finalComputerDice = rollDice()
                playerDice = finalPlayerDice
                computerDice = finalComputerDice

                when {
                    finalPlayerDice > finalComputerDice -> {
                        playerBalance += 10
                        resultMessage = "You Win! 🎉"
                    }
                    finalPlayerDice < finalComputerDice -> {
                        playerBalance -= 10
                        resultMessage = "You Lose! 😞"
                    }
                    else -> {
                        resultMessage = "It's a Tie! 🤝"
                    }
                }

                if (playerBalance <= 0) {
                    resultMessage = "Game Over! You ran out of money."
                }

                isRolling = false
            }
        }
    }
}

fun rollDice(): Int {
    return Random.nextInt(1, 7)
}

fun getDiceImage(diceNumber: Int): Int {
    return when (diceNumber) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_1
    }
}
