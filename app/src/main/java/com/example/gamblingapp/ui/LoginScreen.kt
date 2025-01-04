package com.example.gamblingapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamblingapp.R
import com.example.gamblingapp.ui.theme.GamblingAppTheme

@Composable
fun LoginScreen(
    onValueChangePassword: (String) -> Unit,
    onValueChangeEmail: (String) -> Unit,
    onRegister: () -> Unit,
    onLogin: () -> Unit,
    onPasswordReset: () -> Unit,
    checkEmailError: (String) -> Boolean,
    checkPasswordError: (String) -> Boolean,
    emailText: String,
    passwordText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.siginbackground),
                contentScale = ContentScale.FillBounds)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier
                .padding(4.dp, bottom = 48.dp, top = 96.dp)
                .size(196.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo"
        )
        TextField(
            value = emailText,
            label = { Text("Email", color = Color.LightGray, fontSize = 20.sp) },
            textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Green.copy(alpha = 0.2f),
                unfocusedContainerColor = Color.Black.copy(alpha = 0.2f),
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Red.copy(alpha = 0.2f)
            ),
            onValueChange = onValueChangeEmail,
            singleLine = true,
            isError = checkEmailError(emailText),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp)
        )
        TextField(
            value = passwordText,
            textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Green.copy(alpha = 0.2f),
                unfocusedContainerColor = Color.Black.copy(alpha = 0.2f),
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Red.copy(alpha = 0.2f)
            ),
            label = { Text("Password", color = Color.LightGray, fontSize = 20.sp) },
            onValueChange = onValueChangePassword,
            singleLine = true,
            isError = checkPasswordError(passwordText),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp)
        )
        Spacer(modifier = modifier.padding(4.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(
                onClick = { onPasswordReset() },
                colors = ButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.2f),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                ),
                modifier = modifier

            ) {
                Text("Forgot Password?", textDecoration = TextDecoration.Underline)
            }
            Button(
                onClick = { onLogin() },
                colors = ButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.2f),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                ),
                modifier = modifier

            ) {
                Text("Login", textDecoration = TextDecoration.Underline)
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { onRegister() },
                colors = ButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.2f),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                ),
                modifier = modifier

            ) {
                Text("Register", textDecoration = TextDecoration.Underline)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview()
{
    GamblingAppTheme()
    {
        Surface()
        {
            LoginScreen({},{},{},{}, {}, {false}, {false}, "", "")
        }
    }
}