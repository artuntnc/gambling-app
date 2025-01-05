package com.example.gamblingapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamblingapp.R
import com.example.gamblingapp.ui.theme.GamblingAppTheme


@Composable
fun StoreScreen(
    onBuy1000Click: () -> Unit,
    onBuy100Click: () -> Unit,
    onBuyFreeClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.gradient_store),
                contentScale = ContentScale.FillBounds
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    )
    {
        Image(
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.money_bag),
            contentDescription = "get 1000\$",
            modifier = modifier
                .fillMaxSize()
                .clickable(onClick = onBuy1000Click)
                .padding(32.dp)
                .size(160.dp)
                .weight(1f)
        )
        Image(
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.money),
            contentDescription = "get 100\$",
            modifier = modifier
                .fillMaxSize()
                .clickable(onClick = onBuy100Click)
                .padding(32.dp)
                .size(160.dp)
                .weight(1f)
        )
        Image(
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.ad_money),
            contentDescription = "get 100\$ for free",
            modifier = modifier
                .fillMaxSize()
                .clickable(onClick = onBuyFreeClick)
                .padding(32.dp)
                .size(160.dp)
                .weight(1f)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun StoreScreenPreview()
{
    GamblingAppTheme()
    {
        Surface()
        {
            StoreScreen({},{},{})
        }
    }
}