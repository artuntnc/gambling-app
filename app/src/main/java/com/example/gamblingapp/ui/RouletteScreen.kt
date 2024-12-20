package com.example.gamblingapp.ui

import android.graphics.drawable.shapes.OvalShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamblingapp.R
import com.example.gamblingapp.ui.theme.GamblingAppTheme
import org.intellij.lang.annotations.JdkConstants.FontStyle
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun RouletteScreen(
    onBetChange: (String) -> Unit,
    checkTextError: (String) -> Boolean,
    onRedClick: () -> Unit,
    onBlackClick: () -> Unit,
    onGreenClick: () -> Unit,
    onSpinClick: (Int) -> Unit,
    betText: String,
    lastResultText: String = "100",
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
            Text(stringResource(R.string.roulette_last_result, lastResultText), color = Color.White)
        }
        TextField(
            value = betText,
            label = { Text("Enter Your bet amount", color = Color.LightGray, fontSize = 24.sp) },
            textStyle = TextStyle(color = Color.DarkGray, fontSize = 24.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Red.copy(alpha = 0.1f)
            ),
            onValueChange = onBetChange,
            singleLine = true,
            isError = checkTextError(betText),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(40.dp)
        )
        Image(
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.blue_triangle),
            contentDescription = null,
            modifier = modifier
                .size(32.dp)
        )
        Image(
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.roulette_table),
            contentDescription = null,
            modifier = modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
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
            onClick = onGreenClick,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = modifier
                .padding(64.dp)
        ) {
            Text("SPIN", fontWeight = FontWeight.Bold)
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
            RouletteScreen({},{false},{},{}, {}, {}, "")
        }
    }
}