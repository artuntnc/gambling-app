package com.example.gamblingapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.gamblingapp.R
import com.example.gamblingapp.ui.theme.GamblingAppTheme

@Composable
fun GameMenuScreen(
    onRouletteClick: () -> Unit,
    onBlackjackClick: () -> Unit,
    onDiceClick: () -> Unit,
    onSlotsClick: () -> Unit,
    onShopClick: () -> Unit,
    onComingSoonClick: () -> Unit,
    onDismissRequest: () -> Unit,
    showComingSoon: Boolean = false,
    modifier: Modifier = Modifier
) {
    if(showComingSoon)
    {
        Dialog(onDismissRequest = { onDismissRequest() })
        {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = "Updates coming soon!",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .padding(top = 96.dp, bottom = 164.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                contentScale = ContentScale.Fit,
                painter = painterResource(R.drawable.roulet),
                contentDescription = "roulette game",
                modifier = modifier
                    .fillMaxSize()
                    .clickable(onClick = onRouletteClick)
                    .padding(32.dp)
                    .size(160.dp)
                    .weight(1f)
            )
            Image(
                contentScale = ContentScale.Fit,
                painter = painterResource(R.drawable.blackjack),
                contentDescription = "blackjack game",
                modifier = modifier
                    .fillMaxSize()
                    .clickable(onClick = onBlackjackClick)
                    .padding(32.dp)
                    .size(160.dp)
                    .weight(1f)
            )
        }
        Row(
            modifier = modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                contentScale = ContentScale.Fit,
                painter = painterResource(R.drawable.dice),
                contentDescription = "dice game",
                modifier = modifier
                    .fillMaxSize()
                    .clickable(onClick = onDiceClick)
                    .padding(32.dp)
                    .size(160.dp)
                    .weight(1f)
            )
            Image(
                contentScale = ContentScale.Fit,
                painter = painterResource(R.drawable.slotmachinenew),
                contentDescription = "slots game",
                modifier = modifier
                    .fillMaxSize()
                    .clickable(onClick = onSlotsClick)
                    .padding(32.dp)
                    .size(160.dp)
                    .weight(1f)
            )
        }
        Row(
            modifier = modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(R.drawable.comingsoon),
                contentDescription = "coming soon",
                modifier = modifier
                    .fillMaxSize()
                    .clickable(onClick = onComingSoonClick)
                    .padding(32.dp)
                    .size(160.dp)
                    .weight(1f)
            )
            Image(
                contentScale = ContentScale.Fit,
                painter = painterResource(R.drawable.dollar),
                contentDescription = "shop",
                modifier = modifier
                    .fillMaxSize()
                    .clickable(onClick = onShopClick)
                    .padding(32.dp)
                    .size(160.dp)
                    .weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameMenuScreenPreview()
{
    GamblingAppTheme()
    {
        Surface()
        {
            GameMenuScreen({},{},{},{},{},{},{}, true)
        }
    }
}