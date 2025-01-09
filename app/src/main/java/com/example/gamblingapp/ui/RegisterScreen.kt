package com.example.gamblingapp.ui

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamblingapp.R
import com.example.gamblingapp.ui.theme.GamblingAppTheme

@Composable
fun RegisterScreen(
    onValueChangeFullName: (String) -> Unit,
    onValueChangePassword: (String) -> Unit,
    onValueChangeEmail: (String) -> Unit,
    onValueChangeDate: (String) -> Unit,
    onValueChangePesel: (String) -> Unit,
    onRegister: () -> Unit,
    checkFullNameError: (String) -> Boolean,
    checkPasswordError: (String) -> Boolean,
    checkEmailError: (String) -> Boolean,
    checkDateError: (String) -> Boolean,
    checkPeselError: (String) -> Boolean,
    emailText: String,
    passwordText: String,
    peselText: String,
    dateText: String,
    fullNameText: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.siginbackground),
                contentScale = ContentScale.FillBounds
            )
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = fullNameText,
            textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Green.copy(alpha = 0.2f),
                unfocusedContainerColor = Color.Black.copy(alpha = 0.2f),
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Red.copy(alpha = 0.2f)
            ),
            label = { Text("Full name", color = Color.LightGray, fontSize = 20.sp) },
            onValueChange = onValueChangeFullName,
            singleLine = true,
            isError = checkFullNameError(fullNameText),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(10.dp)
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
                .fillMaxWidth(0.9f)
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(10.dp)
        )
        TextField(
            value = dateText,
            textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Green.copy(alpha = 0.2f),
                unfocusedContainerColor = Color.Black.copy(alpha = 0.2f),
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Red.copy(alpha = 0.2f)
            ),
            label = { Text("Birth Date (DD/MM/YYYY)", color = Color.LightGray, fontSize = 20.sp) },
            onValueChange = onValueChangeDate,
            singleLine = true,
            isError = checkDateError(dateText),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(10.dp)
        )
        TextField(
            value = peselText,
            textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Green.copy(alpha = 0.2f),
                unfocusedContainerColor = Color.Black.copy(alpha = 0.2f),
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Red.copy(alpha = 0.2f)
            ),
            label = { Text("Pesel Number", color = Color.LightGray, fontSize = 20.sp) },
            onValueChange = onValueChangePesel,
            singleLine = true,
            isError = checkPeselError(peselText),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(10.dp)
        )
        Spacer(modifier.weight(1f))
        Button(
            onClick = { onRegister() },
            colors = ButtonColors(
                containerColor = Color.White.copy(alpha = 0.9f),
                contentColor = Color.White,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            ),
            shape = RoundedCornerShape(0),
            modifier = modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 32.dp),
        ) {
            Text("Register", color = Color.Black)
        }

    }
}



@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview()
{
    GamblingAppTheme()
    {
        Surface()
        {
            RegisterScreen({},{},{},{},{},{},{false}, {false},{false},{false},{false}, "", "", "", "", "")
        }
    }
}