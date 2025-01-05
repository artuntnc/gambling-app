package com.example.gamblingapp.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlin.random.Random


@Composable
fun DiceScreen(
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.dice_gradient),
                contentScale = ContentScale.FillBounds
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(stringResource(R.string.roulette_last_result), color = Color.Black, fontSize = 12.sp)
            Column(
                modifier = Modifier.height(100.dp)
            )
            {
                for (result in lastResults)
                {
                    Text(stringResource(R.string.roulette_result_money, result), color = Color.Black, fontSize = 12.sp)
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
            label = { Text("Enter Your bet amount", color = Color.Black, fontSize = 24.sp) },
            onValueChange = onBetChange,
            singleLine = true,
            isError = checkTextError(betText.toString()),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp)
        )

        //animation to do - don't use this
        //var angle by remember { mutableFloatStateOf(startDegree) }
        //val rotation = remember { Animatable(angle) }
        /*LaunchedEffect(diceCast)
        {
            if(diceCast)
            {
                rotation.animateTo(
                    targetValue = targetDegree,
                    animationSpec = tween(3600, easing = LinearEasing)
                ) {
                    angle = value
                }
            }

            onDiceRollFinished()
        }*/

        Row(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .padding(24.dp, top = 64.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = playerDiceId),
                contentDescription = "Player Dice",
                modifier = Modifier.size(100.dp)
            )
            Image(
                painter = painterResource(id = aiDiceId),
                contentDescription = "Computer Dice",
                modifier = Modifier.size(100.dp)
            )
        }

        Text(
            text = resultMessage,
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
            DiceScreen({},{false},{},{},0f)
        }
    }
}
