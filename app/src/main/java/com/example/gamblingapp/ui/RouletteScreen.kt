package com.example.gamblingapp.ui

import android.media.MediaPlayer
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.example.gamblingapp.managers.BackgroundMusicManager
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
    onUpdateAngle: (Float) -> Unit,
    onSpinFinished: () -> Unit,
    betText: String,
    lastResults: List<Float> = listOf(100f, 0f,100f, 0f,100000f),
    rouletteSpun: Boolean = false,
    startDegree: Float = 0f,
    targetDegree: Float = 360f,
    rotation: Animatable<Float,AnimationVector1D> = Animatable(0f),
    modifier: Modifier = Modifier
) {
    
    val context = LocalContext.current
    val spinSoundPlayer = remember {MediaPlayer.create(context, R.raw.roulettespin)}

    DisposableEffect(Unit) {
        onDispose {
            spinSoundPlayer.release()
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.maybethis),
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
                textStyle = TextStyle(color = Color(0xFFFFA500), fontSize = 20.sp),
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
        Image(
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.blue_triangle),
            contentDescription = null,
            modifier = modifier
                .size(32.dp)
        )

        LaunchedEffect(rouletteSpun)
        {
            if(rouletteSpun)
            {
                BackgroundMusicManager.pauseMusic()
                spinSoundPlayer.start()
                rotation.animateTo(
                    targetValue = targetDegree,
                    animationSpec = tween(3600, easing = LinearEasing)
                ) {
                    onUpdateAngle(value)
                }
                spinSoundPlayer.stop()
                spinSoundPlayer.prepare()


                onSpinFinished()
                BackgroundMusicManager.stopMusic()
            }
        }
        Image(
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.roulette_table),
            contentDescription = null,
            modifier = modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
                .rotate(startDegree)
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
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800020)),
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
                colors = ButtonDefaults.buttonColors(containerColor =Color(0xFF006400)),
                modifier = modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text("GREEN", fontWeight = FontWeight.Bold)
            }
        }
        Button(
            onClick = {
                onSpinClick()
                spinSoundPlayer.start()


            },
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
            RouletteScreen({},{false},{},{},{},{},{},{},"1")
        }
    }
}