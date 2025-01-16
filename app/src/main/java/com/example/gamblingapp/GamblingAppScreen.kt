package com.example.gamblingapp

import android.content.Context
import android.media.MediaPlayer
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamblingapp.data.LocalDataStoreManager
import com.example.gamblingapp.data.UsersRepository
import com.example.gamblingapp.managers.BackgroundMusicManager
import com.example.gamblingapp.ui.BlackjackScreen
import com.example.gamblingapp.ui.DiceScreen
import com.example.gamblingapp.ui.GamblingAppViewModel
import com.example.gamblingapp.ui.GameMenuScreen
import com.example.gamblingapp.ui.LoadingScreen
import com.example.gamblingapp.ui.LoadingScreenViewModel
import com.example.gamblingapp.ui.LoginScreen
import com.example.gamblingapp.ui.RegisterScreen
import com.example.gamblingapp.ui.RouletteScreen
import com.example.gamblingapp.ui.SettingsScreen
import com.example.gamblingapp.ui.SlotsScreen
import com.example.gamblingapp.ui.StoreScreen
import com.example.gamblingapp.ui.theme.GamblingAppTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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
    Column {
        Box(
            modifier = modifier
                .height(20.dp)
                .background(Color(27, 206, 211, 255))
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.LightGray),
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
}

@Composable
fun GamblingApp(
    gamblingAppViewModel: GamblingAppViewModel = viewModel(),
    loadingScreenViewModel: LoadingScreenViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    context: Context
)
{
    //scope for managing memory
    val coroutineScope = rememberCoroutineScope()

    //app state that controls the app
    val appState by gamblingAppViewModel.appState.collectAsState()
    val loginState by gamblingAppViewModel.loginState.collectAsState()
    val registerState by gamblingAppViewModel.registerState.collectAsState()

    //media player
    BackgroundMusicManager.initialize(context, R.raw.smoke_lish_grooves)


    if(appState.showIncorrectBetDialog)
    {
        Dialog(onDismissRequest = { gamblingAppViewModel.changeIncorrectBetState() })
        {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = "Incorrect bet - value has to be larger than 0 and less than your balance!",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
    if(appState.showIncorrectInputDialog)
    {
        Dialog(onDismissRequest = { gamblingAppViewModel.changeIncorrectInputState() })
        {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = "Incorrect input - try again!",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
    if(appState.showUserExistDialog)
    {
        Dialog(onDismissRequest = { gamblingAppViewModel.changeUserExistState() })
        {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = "This user already exists!",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
    if(appState.showUserNotExistDialog)
    {
        Dialog(onDismissRequest = { gamblingAppViewModel.changeUserNotExistState() })
        {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = "User does not exist or password incorrect!",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

    //main app component with a top bar and navigation
    Scaffold(
        topBar = { if(!appState.hideTopBar){ GamblingAppBar({ navController.navigate(AppRoutes.Settings.name) }, appState.money.toString() + "$") }}
    ) { innerPadding ->
        Column(

        ){
            //component responsible for navigation in the app
            NavHost(
                navController = navController,
                startDestination = AppRoutes.Loading.name,
                modifier = Modifier.padding(innerPadding)
            )
            {
                composable(route = AppRoutes.Loading.name) {
                    LoadingScreen(
                        onLoadingEnd = {
                                            gamblingAppViewModel.setEmail(loadingScreenViewModel.getEmail())
                                            if(appState.email == "")
                                            {
                                                navController.navigate(AppRoutes.Login.name)
                                            }
                                            else
                                            {
                                                gamblingAppViewModel.getUserFromLocal()
                                                navController.navigate(AppRoutes.GameMenu.name)
                                            }
                                       },
                        loadingScreenViewModel = loadingScreenViewModel,
                        modifier = Modifier
                    )
                }
                composable(route = AppRoutes.Login.name) {
                    LoginScreen(
                        onValueChangePassword = { password -> gamblingAppViewModel.setLoginPassword(password) },
                        onValueChangeEmail = { email -> gamblingAppViewModel.setLoginEmail(email) },
                        onRegister = {
                                        gamblingAppViewModel.resetLogin()
                                        navController.navigate(AppRoutes.Register.name)
                                     },
                        onLogin = {
                                    coroutineScope.launch {
                                        if(gamblingAppViewModel.getUserData())
                                        {
                                            loadingScreenViewModel.saveUser(loginState.email)
                                            navController.navigate(AppRoutes.GameMenu.name)
                                            {
                                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                            }
                                            gamblingAppViewModel.changeTopBarState()
                                        }
                                        else
                                        {
                                            //show incorrect input message
                                        }
                                    }
                                  },
                        onPasswordReset = {  }, //to implement a password reset mechanic
                        checkEmailError = { email -> gamblingAppViewModel.checkIfInputIncorrect(email, GamblingAppViewModel.inputType.Email) },
                        checkPasswordError = { password -> gamblingAppViewModel.checkIfInputIncorrect(password, GamblingAppViewModel.inputType.Password) },
                        emailText = loginState.email,
                        passwordText = loginState.password,
                        modifier = Modifier
                    )
                }
                composable(route = AppRoutes.Register.name) {
                    RegisterScreen(
                        onValueChangeFullName = { name -> gamblingAppViewModel.setRegisterFullName(name) },
                        onValueChangePassword = { password -> gamblingAppViewModel.setRegisterPassword(password) },
                        onValueChangeEmail = { email -> gamblingAppViewModel.setRegisterEmail(email) },
                        onValueChangeDate = { date -> gamblingAppViewModel.setRegisterBirthDate(date) },
                        onValueChangePesel = { pesel -> gamblingAppViewModel.setRegisterPesel(pesel) },
                        onRegister = {
                                        coroutineScope.launch {
                                            if(gamblingAppViewModel.setUserData())
                                            {
                                                loadingScreenViewModel.saveUser(registerState.email)
                                                navController.navigate(AppRoutes.GameMenu.name)
                                                {
                                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                                }
                                                gamblingAppViewModel.changeTopBarState()
                                            }
                                            else
                                            {
                                                //show incorrect input message
                                            }
                                        }
                                     },
                        checkFullNameError = { fullName -> gamblingAppViewModel.checkIfInputIncorrect(fullName, GamblingAppViewModel.inputType.FullName) },
                        checkPasswordError = { password -> gamblingAppViewModel.checkIfInputIncorrect(password, GamblingAppViewModel.inputType.Password) },
                        checkEmailError = { email -> gamblingAppViewModel.checkIfInputIncorrect(email, GamblingAppViewModel.inputType.Email) },
                        checkDateError = { date -> gamblingAppViewModel.checkIfInputIncorrect(date, GamblingAppViewModel.inputType.Date) },
                        checkPeselError = { pesel -> gamblingAppViewModel.checkIfInputIncorrect(pesel, GamblingAppViewModel.inputType.Pesel) },
                        emailText = registerState.email,
                        passwordText = registerState.password,
                        peselText = registerState.pesel,
                        dateText = registerState.birthDate,
                        fullNameText = registerState.fullName,
                        modifier = Modifier
                    )
                }
                composable(route = AppRoutes.GameMenu.name) {
                        GameMenuScreen(
                            onRouletteClick = { navController.navigate(AppRoutes.Roulette.name) },
                            onBlackjackClick = { navController.navigate(AppRoutes.Blackjack.name) },
                            onDiceClick = { navController.navigate(AppRoutes.Dice.name) },
                            onSlotsClick = { navController.navigate(AppRoutes.Slots.name) },
                            onShopClick = { navController.navigate(AppRoutes.Money.name) },
                            onComingSoonClick = { gamblingAppViewModel.changeComingSoonState() },
                            onDismissRequest = { gamblingAppViewModel.changeComingSoonState() },
                            showComingSoon = appState.showComingSoonDialog,
                            modifier = Modifier
                        )
                }
                composable(route = AppRoutes.Settings.name) {
                    SettingsScreen(
                        onMusicVolumeChange = {
                                                    change -> gamblingAppViewModel.onMusicVolumeChange(change)
                                                    BackgroundMusicManager.setVolume(change)
                                                    loadingScreenViewModel.saveMusicVolume(change)
                                              },
                        onSoundVolumeChange = {
                                                    change -> gamblingAppViewModel.onSoundVolumeChange(change)
                                                    loadingScreenViewModel.saveSoundVolume(change)},
                        onAccountClick = { /*nav to account settings*/},
                        onNotificationsClick = {
                                                    loadingScreenViewModel.saveNotifications(gamblingAppViewModel.onNotificationsClick())
                                               },
                        onThemesClick = {
                                            loadingScreenViewModel.saveAltTheme(gamblingAppViewModel.onThemesClick())
                                        },
                        onHelpClick = { gamblingAppViewModel.onHelpClick()},
                        onSignOutClick = {
                                            gamblingAppViewModel.changeTopBarState()
                                            gamblingAppViewModel.onSignOutClick()
                                            loadingScreenViewModel.saveUser("")
                                            navController.navigate(AppRoutes.Login.name)
                                            {
                                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                            }
                                         },
                        musicVolume = appState.musicVolume,
                        soundVolume = appState.soundVolume,
                        notificationsOn = appState.areNotificationsOn,
                        altThemeOn = appState.altThemeOn,
                        modifier = Modifier
                    )
                }
                composable(route = AppRoutes.Roulette.name) {
                    RouletteScreen(
                        onBetChange = { bet -> gamblingAppViewModel.onRouletteBetChange(bet)},
                        checkTextError = { bet -> gamblingAppViewModel.checkIfInputIncorrect(bet, GamblingAppViewModel.inputType.Money) },
                        onRedClick = { gamblingAppViewModel.onRouletteButtonClick(Color.Red)},
                        onBlackClick = { gamblingAppViewModel.onRouletteButtonClick(Color.Black)},
                        onGreenClick = { gamblingAppViewModel.onRouletteButtonClick(Color.Green)},
                        onSpinClick = { gamblingAppViewModel.onRouletteSpinClick()},
                        onUpdateAngle = { angle -> gamblingAppViewModel.onUpdateAngle(angle)},
                        onSpinFinished = {
                                            coroutineScope.launch {
                                                gamblingAppViewModel.updateRouletteState()
                                            }
                                        },
                        betText = appState.chosenRouletteBet,
                        lastResults = appState.lastRouletteResults,
                        rouletteSpun = appState.rouletteSpun,
                        startDegree = appState.rouletteDegree,
                        targetDegree = appState.targetRouletteDegree,
                        rotation = appState.rotation,
                        modifier = Modifier
                    )
                }
                composable(route = AppRoutes.Dice.name) {
                    DiceScreen(
                        onBetChange = { bet -> gamblingAppViewModel.onDiceBetChange(bet) },
                        checkTextError = { bet ->
                            gamblingAppViewModel.checkIfInputIncorrect(
                                bet,
                                GamblingAppViewModel.inputType.Money
                            )
                        },
                        onDiceRollClick = { gamblingAppViewModel.onDiceRollClick() },
                        onDiceRollFinished = {
                            coroutineScope.launch {
                                gamblingAppViewModel.updateDiceState()
                            }
                        },
                        changeAIDice = { newDice -> gamblingAppViewModel.updateAIDice(newDice) },
                        changePlayerDice = { newDice -> gamblingAppViewModel.updatePlayerDice(newDice) },
                        betText = appState.chosenDiceBet,
                        lastResults = appState.lastDiceResults,
                        diceCast = appState.diceCast,
                        resultMessage = appState.diceResultMessage,
                        aiDiceId = appState.newAIDice,
                        playerDiceId = appState.newUserDice,
                        modifier = Modifier,
                    )
                }
                composable(route = AppRoutes.Blackjack.name) {
                        BlackjackScreen(
                            onBetChange = { bet -> gamblingAppViewModel.onBlackjackBetChange(bet)},
                            checkTextError = { bet -> gamblingAppViewModel.checkIfInputIncorrect(bet, GamblingAppViewModel.inputType.Money) },
                            onHitClick = { gamblingAppViewModel.onBlackjackHitClick()},
                            onStandClick = {
                                                coroutineScope.launch {
                                                    gamblingAppViewModel.onBlackjackStandClick()
                                                }
                                            },
                            onPlayAgainClick = { gamblingAppViewModel.onBlackjackPlayAgainClick()},
                            onDrawFinished = { gamblingAppViewModel.updateBlackjackState()},
                            onBustedFinished = { gamblingAppViewModel.updateBlackjackBustedState()},
                            betText = appState.chosenBlackjackBet,
                            lastResults = appState.lastBlackjackResults,
                            playerCards = appState.playerCards,
                            dealerCards = appState.dealerCards,
                            isPlayerTurn = appState.isPlayerTurn,
                            gameOver = appState.isGameOver,
                            busted = appState.isBusted,
                            dealerHandTotal = appState.dealerTotal,
                            playerHandTotal = appState.playerTotal,
                            modifier = Modifier
                        )
                }
                composable(route = AppRoutes.Slots.name) {
                    SlotsScreen(
                        onBetChange = { bet -> gamblingAppViewModel.onSlotsBetChange(bet) },
                        checkTextError = { bet ->
                            gamblingAppViewModel.checkIfInputIncorrect(
                                bet,
                                GamblingAppViewModel.inputType.Money
                            )
                        },
                        onSpinClick = { gamblingAppViewModel.onSlotsSpinClick() },
                        onSpinFinished = {
                                            coroutineScope.launch {
                                                gamblingAppViewModel.updateSlotsState()
                                            }
                                         },
                        updateSpinningSlot = { slot -> gamblingAppViewModel.onUpdateSlot(slot) },
                        betText = appState.chosenSlotsBet,
                        slotsSpun = appState.slotsSpun,
                        startSlot1 = appState.slot1Id,
                        startSlot2 = appState.slot2Id,
                        startSlot3 = appState.slot3Id,
                        endSlot1 = appState.nextSlot1Id,
                        endSlot2 = appState.nextSlot2Id,
                        endSlot3 = appState.nextSlot3Id,
                        lastResults = appState.lastSlotsResults,
                        modifier = Modifier,
                    )
                }
                composable(route = AppRoutes.Money.name) {
                    StoreScreen(
                        onBuy1000Click = { gamblingAppViewModel.get1000Coins() },
                        onBuy100Click = { gamblingAppViewModel.get100Coins() },
                        onBuyFreeClick = { gamblingAppViewModel.getFreeCoins() },
                        modifier = Modifier
                    )
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
            //GamblingApp()
        }
    }
}