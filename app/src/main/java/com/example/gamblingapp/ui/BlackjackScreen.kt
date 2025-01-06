package com.example.gamblingapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamblingapp.R
import com.example.gamblingapp.data.Card
import com.example.gamblingapp.ui.theme.GamblingAppTheme



@Composable
fun BlackjackScreen(
    onBetChange: (String) -> Unit,
    checkTextError: (String) -> Boolean,
    onHitClick: () -> Unit,
    onStandClick: () -> Unit,
    onPlayAgainClick: () -> Unit,
    onDrawFinished: () -> Unit,
    onBustedFinished: () -> Unit,
    betText: Float,
    lastResults: List<Float> = listOf(100f, 0f),
    playerCards: List<Card> = listOf(Card("hearts","ace"),Card("hearts","ace")),
    dealerCards: List<Card> = listOf(Card("hearts","ace"),Card("hearts","ace")),
    isPlayerTurn: Boolean = false,
    gameOver: Boolean = true,
    busted: Boolean = false,
    dealerHandTotal: Int = 0,
    playerHandTotal: Int = 0,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.blackjack_gradient),
                contentScale = ContentScale.FillBounds
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //Last five results
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(stringResource(R.string.roulette_last_result), color = Color.White, fontSize = 12.sp)
            Column(
                modifier = Modifier.height(100.dp)
            )
            {
                for (result in lastResults)
                {
                    Text(stringResource(R.string.roulette_result_money, result), color = Color.White, fontSize = 12.sp)
                }
            }
        }
        TextField(
            value = betText.toString(),
            textStyle = TextStyle(color = Color.DarkGray, fontSize = 24.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Red.copy(alpha = 0.1f)
            ),
            label = { Text("Enter Your bet amount", color = Color.LightGray, fontSize = 24.sp) },
            onValueChange = onBetChange,
            singleLine = true,
            isError = checkTextError(betText.toString()),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp)
        )

        Text(
            text = "Blackjack",
            fontSize = 36.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(top = 16.dp, bottom = 32.dp)
        )

        //Dealer's Cards
        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = "Dealer's Cards", fontSize = 24.sp, color = Color.White)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
            {
                dealerCards.forEach { card ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(120.dp)
                    ) {
                        Image(
                            painter = painterResource(id = card.getImageResource()),
                            contentDescription = "dealer card"
                        )
                    }
                }
            }
            Text(
                text = "Total: $dealerHandTotal",
                fontSize = 20.sp,
                color = Color.White,
                modifier = modifier.padding(top = 2.dp)
            )
        }


        Spacer(modifier = Modifier.height(32.dp))

        //Player's Cards
        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = "Player's Cards", fontSize = 24.sp, color = Color.White)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
            {
                playerCards.forEach { card ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(120.dp)
                    ) {
                        Image(
                            painter = painterResource(id = card.getImageResource()),
                            contentDescription = "player card"
                        )
                    }
                }
            }
            Text(
                text = "Total: $playerHandTotal",
                fontSize = 20.sp, color = Color.White,
                modifier = modifier.padding(top = 2.dp)
            )
        }

        // Player Actions
        if (isPlayerTurn && !gameOver)
        {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp))
            {
                Button(onClick = onHitClick)
                {
                    Text(text = "Hit")
                }

                Button(onClick = onStandClick)
                {
                    Text(text = "Stand")
                }
            }
        }

        if (gameOver)
        {
            Button(
                onClick = onPlayAgainClick,
                modifier = modifier.padding(top = 24.dp)
            )
            {
                Text(text = "Start Game!")
            }
        }
    }

    LaunchedEffect(busted)
    {
        if (busted)
        {
            Toast.makeText(context, "You busted! Dealer wins.", Toast.LENGTH_SHORT).show()
            onBustedFinished()
        }
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
            BlackjackScreen({},{false},{},{},{},{},{},0f,)
        }
    }
}