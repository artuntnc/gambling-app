package com.example.gamblingapp.ui

import android.content.res.Resources
import android.util.DisplayMetrics
import android.widget.ProgressBar
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.example.gamblingapp.R
import com.example.gamblingapp.ui.theme.GamblingAppTheme
import kotlin.math.round


@Composable
@OptIn(ExperimentalAnimationGraphicsApi::class)
fun LoadingScreen(
    modifier: Modifier = Modifier)
{
    var progress = 0f
    val colors = listOf(colorResource(R.color.loading_screen_gradient_start),
        colorResource(R.color.loading_screen_gradient_center),
        colorResource(R.color.loading_screen_gradient_center2),
        colorResource(R.color.loading_screen_gradient_end))

    val displayMetrics: DisplayMetrics by lazy { Resources.getSystem().displayMetrics }
    val screenHeight = displayMetrics.heightPixels

    val offset by rememberInfiniteTransition(label = "offset").animateFloat(
        initialValue = 0f,
        targetValue = 3200f,
        animationSpec = infiniteRepeatable(tween(10000, easing = LinearEasing)),
        label = "offset"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .drawWithCache {
                val brushSize = 1600f
                val brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset(offset, offset),
                    end = Offset(offset + brushSize, offset + brushSize),
                    tileMode = TileMode.Mirror
                )
                onDrawBehind { drawRect(brush = brush) }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier
                .padding(4.dp, bottom = 80.dp),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.welcome),
            contentDescription = "welcome screen"
        )
        Image(
            modifier = modifier
                .padding(4.dp, bottom = 64.dp)
                .size(192.dp),
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo"
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .height(6.dp)
                .fillMaxWidth(0.95f)
        )
        Text(
            text = stringResource(R.string.loading_screen_progress, progress.toString()),
            modifier = modifier
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview()
{
    GamblingAppTheme()
    {
        Surface()
        {
            LoadingScreen()
        }
    }
}