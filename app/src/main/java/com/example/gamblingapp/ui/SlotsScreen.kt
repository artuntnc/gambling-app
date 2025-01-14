package com.example.gamblingapp.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.paint
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamblingapp.R
import com.example.gamblingapp.ui.theme.GamblingAppTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

fun generateRandomSlot(): Int {
    val slotIcons = listOf(
        R.drawable.slot1cherry,
        R.drawable.slot2orange,
        R.drawable.slot3purple,
        R.drawable.slot4bell,
        R.drawable.slot5bar,
        R.drawable.slot6seven

    )
    return slotIcons.random()
}

@Composable
fun SlotsScreen(
    onBetChange: (String) -> Unit,
    checkTextError: (String) -> Boolean,
    onSpinClick: () -> Unit,
    onSpinFinished: () -> Unit,
    updateSpinningSlot: () -> Unit,
    betText: String,
    slotsSpun: Boolean = false,
    slot1BeginOffset: Animatable<Float, AnimationVector1D> = Animatable(0f),
    slot1EndOffset: Animatable<Float, AnimationVector1D> = Animatable(-300f),
    slot2BeginOffset: Animatable<Float, AnimationVector1D> = Animatable(0f),
    slot2EndOffset: Animatable<Float, AnimationVector1D> = Animatable(-300f),
    slot3BeginOffset: Animatable<Float, AnimationVector1D> = Animatable(0f),
    slot3EndOffset: Animatable<Float, AnimationVector1D> = Animatable(-300f),
    startSlot1: Int = R.drawable.slot5bar,
    startSlot2: Int = R.drawable.slot5bar,
    startSlot3: Int = R.drawable.slot5bar,
    endSlot1: Int = R.drawable.slot4bell,
    endSlot2: Int = R.drawable.slot4bell,
    endSlot3: Int = R.drawable.slot4bell,
    lastResults: List<Float> = listOf(100f, 0f),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.slots_gradient),
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

        var jobs = mutableListOf<Job>()
        val scope = rememberCoroutineScope()
        LaunchedEffect(slotsSpun) {
            if (slotsSpun) {
                val randomSlot1 = generateRandomSlot()
                val randomSlot2 = generateRandomSlot()
                val randomSlot3 = generateRandomSlot()

                jobs += scope.launch {
                    slot1BeginOffset.animateTo(
                        targetValue = 300f,
                        animationSpec = tween(durationMillis = 1000, easing = EaseOut)
                    )
                    slot1EndOffset.snapTo(0f)
                    onSpinFinished()
                    updateSpinningSlot()
                }

                jobs += scope.launch {
                    slot2BeginOffset.animateTo(
                        targetValue = 300f,
                        animationSpec = tween(durationMillis = 1000, easing = EaseOut, delayMillis = 500)
                    )
                    slot2EndOffset.snapTo(0f)
                }

                jobs += scope.launch {
                    slot3BeginOffset.animateTo(
                        targetValue = 300f,
                        animationSpec = tween(durationMillis = 1000, easing = EaseOut, delayMillis = 1000)
                    )
                    slot3EndOffset.snapTo(0f)
                }

                jobs.joinAll()


                onSpinFinished()
            }
        }

        Row(
            modifier = modifier
                .padding(4.dp, top = 96.dp)
                .fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = modifier
                    .clipToBounds()
                    .padding(8.dp)
                    .size(100.dp, 100.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = startSlot1),
                    contentDescription = "roulette game",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .size(100.dp)
                        .offset { IntOffset(0, slot1BeginOffset.value.toInt()) }
                )
                Image(
                        contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = endSlot1),
                    contentDescription = "roulette game",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .size(100.dp)
                        .offset { IntOffset(0, slot1EndOffset.value.toInt()) }
                )
            }
            Box(
                modifier = modifier
                    .clipToBounds()
                    .padding(8.dp)
                    .size(100.dp, 100.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = startSlot2),
                    contentDescription = "roulette game",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .size(100.dp)
                        .offset { IntOffset(0, slot2BeginOffset.value.toInt()) }
                )
                Image(
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = endSlot2),
                    contentDescription = "roulette game",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .size(100.dp)
                        .offset { IntOffset(0, slot2EndOffset.value.toInt()) }
                )
            }
            Box(
                modifier = modifier
                    .clipToBounds()
                    .padding(8.dp)
                    .size(100.dp, 100.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = startSlot3),
                    contentDescription = "roulette game",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .size(100.dp)
                        .offset { IntOffset(0, slot3BeginOffset.value.toInt()) }
                )
                Image(
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = endSlot3),
                    contentDescription = "roulette game",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .size(100.dp)
                        .offset { IntOffset(0, slot3EndOffset.value.toInt()) }
                )
            }
        }
        Button(
            onClick = onSpinClick,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
            modifier = modifier
                .padding(32.dp)
        ) {
            Text("SPIN!", fontWeight = FontWeight.Bold, color = Color.Green)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SlotsScreenPreview()
{
    GamblingAppTheme()
    {
        Surface()
        {
            SlotsScreen({},{false},{}, {}, {},"")
        }
    }
}