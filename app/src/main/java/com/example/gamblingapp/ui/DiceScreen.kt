package com.example.gamblingapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamblingapp.R
import com.example.gamblingapp.ui.theme.GamblingAppTheme
import kotlinx.coroutines.delay
import kotlin.random.Random


@Composable
fun DiceScreen(
    onBetChange: (String) -> Unit,
    checkTextError: (String) -> Boolean,
    onDiceRollClick: () -> Unit,
    onDiceRollFinished: () -> Unit,
    changeAIDice: (Int) -> Unit,
    changePlayerDice: (Int) -> Unit,
    betText: String,
    lastResults: List<Float> = listOf(100f, 0f),
    diceCast: Boolean = false,
    resultMessage: String = "Roll the dice to play!",
    aiDiceId: Int = R.drawable.dice_1,
    playerDiceId: Int = R.drawable.dice_1,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.dice_gradient),
                contentScale = ContentScale.FillBounds
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextField(
                value = betText,
                textStyle = TextStyle(color = Color.DarkGray, fontSize = 20.sp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Red.copy(alpha = 0.1f)
                ),
                label = { Text(stringResource(R.string.enter_bet), color = Color.LightGray, fontSize = 24.sp) },
                onValueChange = onBetChange,
                singleLine = true,
                isError = checkTextError(betText),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(2.dp)
                    .weight(0.75f)
            )
            VerticalDivider(
                color = Color.DarkGray,
                thickness = 1.dp,
                modifier = modifier
                    .height(120.dp)
                    .padding(2.dp))
            Text(
                stringResource(R.string.last_results),
                color = Color.White,
                fontSize = 14.sp,
                modifier = modifier
                    .weight(0.3f)
            )
            Column(
                modifier = Modifier
                    .height(120.dp)
                    .weight(0.3f)
            )
            {
                for (result in lastResults)
                {
                    Text(stringResource(R.string.result_money, result), color = Color.White, fontSize = 12.sp)
                }
            }
        }

        //animation to do
        LaunchedEffect(diceCast)
        {
            if(diceCast)
            {
                val listOfAIDice = listOf(Random.nextInt(6), Random.nextInt(6), Random.nextInt(6), Random.nextInt(6), Random.nextInt(6))
                val listOfPlayerDice = listOf(Random.nextInt(6), Random.nextInt(6), Random.nextInt(6), Random.nextInt(6), Random.nextInt(6))

                var i: Int = 0
                repeat(5)
                {
                    changeAIDice(i)
                    delay(200)
                    changePlayerDice(i)
                    delay(200)
                    i++
                }

                onDiceRollFinished()
            }

        }

        Row(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 10.dp, top = 64.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Player dice",
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = modifier
                )
                Image(
                    painter = painterResource(id = playerDiceId),
                    contentDescription = "Player Dice",
                    modifier = Modifier.size(100.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "AI dice",
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = modifier
                )
                Image(
                    painter = painterResource(id = aiDiceId),
                    contentDescription = "Computer Dice",
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        Text(
            text = resultMessage,
            color = Color.Black,
            fontSize = 18.sp,
            modifier = modifier
                .padding(24.dp))

        Button(
            onClick = onDiceRollClick,
            modifier = modifier
                .padding(24.dp)
        ) {
            Text(text = "Roll Dice")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceScreenPreview()
{
    GamblingAppTheme()
    {
        Surface()
        {
            DiceScreen({},{false},{},{}, {},{},"")
        }
    }
}
