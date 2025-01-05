package com.example.gamblingapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamblingapp.R
import com.example.gamblingapp.ui.theme.GamblingAppTheme

data class Card(val suit: String, val value: String) {
    fun getImageResource(): Int {
        return when ("${value}_of_${suit}") {
            "ace_of_hearts" -> R.drawable.ace_of_hearts
            "ace_of_clubs" -> R.drawable.ace_of_clubs
            "ace_of_diamonds" -> R.drawable.ace_of_diamonds
            "ace_of_spades" -> R.drawable.ace_of_spades2
            "2_of_hearts" -> R.drawable.two_of_hearts
            "8_of_clubs" -> R.drawable.eight_of_clubs
            "8_of_diamonds"-> R.drawable.eight_of_diamonds
            "5_of_clubs" -> R.drawable.five_of_clubs
            "5_of_diamonds" -> R.drawable.five_of_diamonds
            "4_of_clubs" -> R.drawable.four_of_clubs
            "4_of_diamonds" -> R.drawable.four_of_diamonds
            "jack_of_clubs" -> R.drawable.jack_of_clubs2
            "jack_of_diamonds" -> R.drawable.jack_of_diamonds2
            "king_of_clubs" -> R.drawable.king_of_clubs2
            "king_of_diamonds" -> R.drawable.king_of_diamonds2
            "9_of_clubs" -> R.drawable.nine_of_clubs
            "9_of_diamonds" -> R.drawable.nine_of_diamonds
            "queen_of_clubs" -> R.drawable.queen_of_clubs2
            "queen_of_diamonds" -> R.drawable.queen_of_diamonds2
            "7_of_clubs" -> R.drawable.seven_of_clubs
            "7_of_diamond" -> R.drawable.seven_of_diamonds
            "6_of_clubs" -> R.drawable.six_of_clubs
            "6_of_diamonds" -> R.drawable.six_of_diamonds
            "10_of_clubs" -> R.drawable.ten_of_clubs
            "10_of_diamonds" -> R.drawable.ten_of_diamonds
            "3_of_clubs" -> R.drawable.three_of_clubs
            "3_of_diamonds" -> R.drawable.three_of_diamonds
            "2_of_clubs" -> R.drawable.two_of_clubs
            "2_of_diamonds" -> R.drawable.two_of_diamonds

            "3_of_hearts" -> R.drawable.three_of_hearts
            "4_of_hearts" -> R.drawable.four_of_hearts
            "5_of_hearts" -> R.drawable.five_of_hearts
            "6_of_hearts" -> R.drawable.six_of_hearts
            "7_of_hearts" -> R.drawable.seven_of_hearts
            "8_of_hearts" -> R.drawable.eight_of_hearts
            "9_of_hearts" -> R.drawable.nine_of_hearts
            "10_of_hearts" -> R.drawable.ten_of_hearts
            "jack_of_hearts" -> R.drawable.jack_of_hearts2
            "queen_of_hearts" -> R.drawable.queen_of_hearts2
            "king_of_hearts" -> R.drawable.king_of_hearts2

            "2_of_spades" -> R.drawable.two_of_spades
            "3_of_spades" -> R.drawable.three_of_spades
            "4_of_spades" -> R.drawable.four_of_spades
            "5_of_spades" -> R.drawable.five_of_spades
            "6_of_spades" -> R.drawable.six_of_spades
            "7_of_spades" -> R.drawable.seven_of_spades
            "8_of_spades" -> R.drawable.eight_of_spades
            "9_of_spades" -> R.drawable.nine_of_spades
            "10_of_spades" -> R.drawable.ten_of_spades
            "jack_of_spades" -> R.drawable.jack_of_spades2
            "queen_of_spades" -> R.drawable.queen_of_spades2
            "king_of_spades" -> R.drawable.king_of_spades2
            else -> R.drawable.cardbacksite
        }
    }
}

fun drawCard(): Card {
    val suits = listOf("hearts", "spades", "diamonds", "clubs")
    val values = listOf("ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king")
    return Card(suits.random(), values.random())
}

@Composable
fun BlackjackScreen(
    onBetChange: (String) -> Unit,
    checkTextError: (String) -> Boolean,
    onDiceRollClick: () -> Unit,
    onDiceRollFinished: () -> Unit,
    betText: Float,
    lastResults: List<Float> = listOf(100f, 0f),
    diceCast: Boolean = false,
    resultMessage: String = "Roll the dice to play!",
    aiDiceId: Int = R.drawable.dice_1,
    playerDiceId: Int = R.drawable.dice_1,
    modifier: Modifier = Modifier
) {
    val playerCards = remember { mutableStateListOf<Card>() }
    val dealerCards = remember { mutableStateListOf<Card>() }
    val balance = remember { mutableIntStateOf(1000) }
    var isPlayerTurn by remember { mutableStateOf(true) }
    var gameOver by remember { mutableStateOf(false) }
    var busted by remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (playerCards.isEmpty() && dealerCards.isEmpty()) {
        // Start the game
        playerCards.add(drawCard())
        playerCards.add(drawCard())
        dealerCards.add(drawCard())
        dealerCards.add(drawCard())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.blackjack_gradient),
                contentScale = ContentScale.FillBounds
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Blackjack", fontSize = 36.sp)


        // Dealer's Cards
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Dealer's Cards", fontSize = 24.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                dealerCards.forEach { card ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(120.dp)
                    ) {
                        Image(
                            painter = painterResource(id = card.getImageResource()),
                            contentDescription = null
                        )
                    }
                }
            }
            Text(text = "Total: ${calculateHandTotal(dealerCards)}", fontSize = 20.sp)
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Player's Cards
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Player's Cards", fontSize = 24.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                playerCards.forEach { card ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(120.dp)
                    ) {
                        Image(
                            painter = painterResource(id = card.getImageResource()),
                            contentDescription = null
                        )
                    }
                }
            }
            Text(text = "Total: ${calculateHandTotal(playerCards)}", fontSize = 20.sp)
        }

        // Player Actions
        if (isPlayerTurn && !gameOver) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {
                    playerCards.add(drawCard())
                    if (calculateHandTotal(playerCards) > 21) {
                        gameOver = true
                        busted = true
                    }
                }) {
                    Text(text = "Hit")
                }

                Button(onClick = {
                    isPlayerTurn = false
                    while (calculateHandTotal(dealerCards) < 17) {
                        dealerCards.add(drawCard())
                    }
                    evaluateGameOutcome(playerCards, dealerCards, balance)
                    gameOver = true
                }) {
                    Text(text = "Stand")
                }
            }
        }

        if (gameOver) {
            Button(onClick = {
                playerCards.clear()
                dealerCards.clear()
                isPlayerTurn = true
                gameOver = false
            }) {
                Text(text = "Play Again")
            }
        }
    }

    LaunchedEffect(busted) {
        if (busted) {
            Toast.makeText(context, "You busted! Dealer wins.", Toast.LENGTH_SHORT).show()
            busted = false
        }
    }
}

fun calculateHandTotal(cards: List<Card>): Int {
    var total = 0
    var aceCount = 0
    cards.forEach { card ->
        when (card.value) {
            "ace" -> {
                total += 11
                aceCount++
            }
            "jack", "queen", "king" -> total += 10
            else -> total += card.value.toInt()
        }
    }
    while (total > 21 && aceCount > 0) {
        total -= 10
        aceCount--
    }
    return total
}

fun evaluateGameOutcome(
    playerCards: List<Card>,
    dealerCards: List<Card>,
    balance: MutableState<Int>
) {
    val playerTotal = calculateHandTotal(playerCards)
    val dealerTotal = calculateHandTotal(dealerCards)

    when {
        dealerTotal > 21 -> balance.value += 200
        playerTotal > dealerTotal -> balance.value += 200
        playerTotal == dealerTotal -> {} // Draw, no change in balance
        else -> balance.value -=200
    }
}

@Preview(showBackground = true)
@Composable
fun BlackjackScreenPreview()
{
    GamblingAppTheme()
    {
        Surface()
        {
            BlackjackScreen({},{false},{},{},0f)
        }
    }
}