package com.example.gamblingapp

import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamblingapp.ui.GamblingAppViewModel
import com.example.gamblingapp.ui.LoadingScreen
import com.example.gamblingapp.ui.LoadingScreenViewModel
import com.example.gamblingapp.ui.LoginScreen
import com.example.gamblingapp.ui.RegisterScreen
import com.example.gamblingapp.ui.theme.GamblingAppTheme

enum class AppRoutes(@StringRes val title: Int)
{
    Loading(title = R.string.loading_screen),
    Login(title = R.string.login_screen),
    Register(title = R.string.register_screen),
    GameMenu(title = R.string.game_menu_screen),
    Settings(title = R.string.settings_screen),
    Roulette(title = R.string.roulette_screen),
    Dice(title = R.string.dice_screen),
    Blackjack(title = R.string.blackjack_screen),
    Slots(title = R.string.slots_screen),
    Money(title = R.string.money_screen),
    PasswordReset(title = R.string.password_reset_screen)
}

@Composable
fun GamblingAppBar(
    onSettingClick: () -> Unit,
    moneyText: String = "1000$",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.set_ic),
            contentDescription = "roulette game",
            modifier = modifier
                .fillMaxHeight()
                .clickable(onClick = onSettingClick)
                .padding(4.dp)
        )
        Text(
            text = moneyText,
            fontSize = 32.sp,
            modifier = modifier
        )
    }
}

@Composable
fun GamblingApp(
    gamblingAppViewModel: GamblingAppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
)
{
    //app state that controls the app
    val appState by gamblingAppViewModel.appState.collectAsState()

    Scaffold(
    ) { innerPadding ->

        Column(

        ){
            //Check if bar should be hidden or not, hidden by default
            if(!appState.hideTopBar)
            {
                GamblingAppBar({}, appState.money.toString() + "$")
            }

            //component responsible for navigation in the app
            NavHost(
                navController = navController,
                startDestination = AppRoutes.Loading,
                modifier = Modifier.padding(innerPadding)
            )
            {
                composable(route = AppRoutes.Loading.name) {
                    LoadingScreen(
                        LoadingScreenViewModel(),
                        onLoadingEnd = { navController.navigate(AppRoutes.Login.name) },
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                composable(route = AppRoutes.Login.name) {
                    LoginScreen(
                        onValueChangePassword = { password -> gamblingAppViewModel.setLoginPassword(password) },
                        onValueChangeEmail = { email -> gamblingAppViewModel.setLoginPassword(email) },
                        onRegister = {
                                        navController.navigate(AppRoutes.Register.name)
                                        gamblingAppViewModel.resetLogin()
                                     },
                        onPasswordReset = {  }, //to implement a password reset mechanic
                        checkEmailError = TODO(),
                        checkPasswordError = TODO(),
                        emailText = TODO(),
                        passwordText = TODO(),
                        modifier = TODO()
                    )
                }
                composable(route = AppRoutes.Register.name) {

                }
                composable(route = AppRoutes.GameMenu.name) {

                }
                composable(route = AppRoutes.Settings.name) {

                }
                composable(route = AppRoutes.Roulette.name) {

                }
                composable(route = AppRoutes.Dice.name) {

                }
                composable(route = AppRoutes.Blackjack.name) {

                }
                composable(route = AppRoutes.Slots.name) {

                }
                composable(route = AppRoutes.Money.name) {

                }
                composable(route = AppRoutes.PasswordReset.name) {

                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GamblingAppBarPreview()
{
    GamblingAppTheme()
    {
        Surface()
        {
            GamblingAppBar({})
        }
    }
}