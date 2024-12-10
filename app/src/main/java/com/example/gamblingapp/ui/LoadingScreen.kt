package com.example.gamblingapp.ui

import android.widget.ProgressBar
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamblingapp.R
import com.example.gamblingapp.ui.theme.GamblingAppTheme


@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier)
{
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.background),
                contentScale = ContentScale.FillBounds
                ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(
            modifier = modifier
                .padding(4.dp, bottom = 96.dp),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.welcome),
            contentDescription = "welcome screen"
        )
        Image(
            modifier = modifier
                .padding(4.dp, bottom = 48.dp)
                .size(192.dp),
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo"
        )
        LinearProgressIndicator(
            progress = { 0f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .fillMaxSize(0.8f)
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