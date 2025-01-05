package com.example.gamblingapp.ui

import android.graphics.drawable.shapes.OvalShape
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
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
import com.example.gamblingapp.ui.theme.GamblingAppTheme


@Composable
fun RouletteScreen(
    onBetChange: (String) -> Unit,
    checkTextError: (String) -> Boolean,
    onRedClick: () -> Unit,
    onBlackClick: () -> Unit,
    onGreenClick: () -> Unit,
    onSpinClick: () -> Unit,
    onSpinFinished: (Float) -> Unit,
    betText: Float,
    lastResults: List<Float> = listOf(100f, 0f),
    rouletteSpun: Boolean = false,
    startDegree: Float = 0f,
    targetDegree: Float = 360f,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.gradient_roulette_background),
                contentScale = ContentScale.FillBounds
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Image(
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.blue_triangle),
            contentDescription = null,
            modifier = modifier
                .size(32.dp)
        )

        var angle by remember { mutableFloatStateOf(startDegree) }
        val rotation = remember { Animatable(angle) }
        LaunchedEffect(rouletteSpun)
        {
            if(rouletteSpun)
            {
                rotation.animateTo(
                    targetValue = targetDegree,
                    animationSpec = tween(3600, easing = LinearEasing)
                ) {
                    angle = value
                }
            }

            onSpinFinished(angle)
        }
        Image(
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.roulette_table),
            contentDescription = null,
            modifier = modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
                .rotate(angle)
        )
        Row(
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onRedClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text("RED", fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = onBlackClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text("BLACK", fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = onGreenClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                modifier = modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text("GREEN", fontWeight = FontWeight.Bold)
            }
        }
        Button(
            onClick = onSpinClick,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = modifier
                .padding(32.dp)
        ) {
            Text("SPIN!", fontWeight = FontWeight.Bold)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RouletteScreenPreview()
{
    GamblingAppTheme()
    {
        Surface()
        {
            RouletteScreen({},{false},{},{}, {}, {}, {},0f)
        }
    }
}