package com.example.gamblingapp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamblingapp.ui.GamblingAppViewModel

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
    Money(title = R.string.money_screen)
}

@Composable
fun GamblingApp(
    gamblingAppViewModel: GamblingAppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
)
{
    val appState by gamblingAppViewModel.appState.collectAsState()

    Scaffold(
        //to implement topBar =
    ) { innerPadding ->

        //component responsible for navigation in the app
        NavHost(
            navController = navController,
            startDestination = AppRoutes.Loading,
            modifier = Modifier.padding(innerPadding)
        )
        {
            composable(route = AppRoutes.Login.name) {

            }
            composable(route = AppRoutes.Loading.name) {

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
        }
    }
}