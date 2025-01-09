package com.example.gamblingapp.ui

import android.text.InputType
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamblingapp.R
import com.example.gamblingapp.data.Card
import com.example.gamblingapp.data.GamblingAppState
import com.example.gamblingapp.data.LoginState
import com.example.gamblingapp.data.RegisterState
import com.example.gamblingapp.data.User
import com.example.gamblingapp.data.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class GamblingAppViewModel(private val usersRepository: UsersRepository) : ViewModel()
{
    private val _appState = MutableStateFlow(GamblingAppState())
    val appState: StateFlow<GamblingAppState> = _appState.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    enum class inputType
    {
        Email,
        Password,
        Date,
        FullName,
        Pesel,
        Money
    }

    fun changeTopBarState()
    {
        val hideTopBarCopy = _appState.value.hideTopBar

        _appState.update { currentState ->
            currentState.copy(hideTopBar = !hideTopBarCopy)
        }
    }

    fun changeComingSoonState()
    {
        val comingSoonCopy = _appState.value.showComingSoonDialog

        _appState.update { currentState ->
            currentState.copy(hideTopBar = !comingSoonCopy)
        }
    }

    fun setLoginPassword(newPassword: String)
    {
        _loginState.update { currentState ->
            currentState.copy(password = newPassword)
        }
    }

    fun setLoginEmail(newEmail: String)
    {
        _loginState.update { currentState ->
            currentState.copy(email = newEmail)
        }
    }

    fun setRegisterPassword(newPassword: String)
    {
        _registerState.update { currentState ->
            currentState.copy(password = newPassword)
        }
    }

    fun setRegisterFullName(newFullName: String)
    {
        _registerState.update { currentState ->
            currentState.copy(fullName = newFullName)
        }
    }

    fun setRegisterBirthDate(newBirthDate: String)
    {
        _registerState.update { currentState ->
            currentState.copy(birthDate = newBirthDate)
        }
    }

    fun setRegisterEmail(newEmail: String)
    {
        _registerState.update { currentState ->
            currentState.copy(email = newEmail)
        }
    }

    fun setRegisterPesel(newPesel: String)
    {
        _registerState.update { currentState ->
            currentState.copy(pesel = newPesel)
        }
    }

    fun checkIfInputIncorrect(text: String, input: inputType): Boolean
    {
        val regex: Regex = when(input) {
            inputType.Email -> """^[a-zA-Z0-9]+([._-][0-9a-zA-Z]+)*@[a-zA-Z0-9]+([.-][0-9a-zA-Z]+)*\.[a-zA-Z]{2,}$""".toRegex()
            inputType.Password -> """^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$""".toRegex()
            inputType.Date -> """^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$""".toRegex()
            inputType.FullName -> """^[a-z]([-']?[a-z]+)*( [a-z]([-']?[a-z]+)*)+$""".toRegex()
            inputType.Pesel -> """^[0-9]{2}([02468]1|[13579][012])(0[1-9]|1[0-9]|2[0-9]|3[01])[0-9]{5}$""".toRegex()
            inputType.Money -> """^\d+(?:\.\d{1,2})?$""".toRegex()
        }

        if(input == inputType.Money)
        {
            return !(regex.matches(text) && _appState.value.money >= text.toFloat())
        }

        return !regex.matches(text)
    }

    //gets user data from the database, fails for incorrect data or if specified user doesn't exist
    suspend fun getUserData(): Boolean
    {
        val email = _loginState.value.email
        val password = _loginState.value.password

        if(!checkIfInputIncorrect(email, inputType.Email) || !checkIfInputIncorrect(password, inputType.Password))
            return false

        val userFlow = usersRepository.getUserStream(email,password)

        if(userFlow.count() == 0)
            return false

        val user = userFlow.first()

        _appState.update { currentState ->
            currentState.copy(username = user.fullName, email = user.email, money = user.balance)
        }

        return true
    }

    suspend fun setUserData(): Boolean
    {
        val email = _registerState.value.email
        val password = _registerState.value.email
        val birthDate = _registerState.value.email
        val pesel = _registerState.value.email
        val fullName = _registerState.value.email

        if(!checkIfInputIncorrect(email, inputType.Email) || !checkIfInputIncorrect(password, inputType.Password) || !checkIfInputIncorrect(birthDate, inputType.Date) || !checkIfInputIncorrect(pesel, inputType.Pesel) || !checkIfInputIncorrect(fullName, inputType.FullName))
            return false

        val userEmailCheck = usersRepository.getUserEmailStream(email)
        val userPeselCheck = usersRepository.getUserPeselStream(pesel)

        if(userEmailCheck.count() != 0 || userPeselCheck.count() != 0)
            return false

        _appState.update { currentState ->
            currentState.copy(username = fullName, email = email, money = 1000f)
        }

        val currentUser = User(
            fullName = fullName,
            balance = 1000f,
            password = password,
            email = email,
            birthDate = birthDate,
            pesel = pesel
        )

        usersRepository.insert(currentUser)
        _appState.update { currentState ->
            currentState.copy(user = currentUser)
        }

        return true
    }

    private suspend fun saveBalance()
    {
        val currentUser = _appState.value.user

        usersRepository.update(currentUser.copy(balance = _appState.value.money))
    }

    suspend fun savePassword()
    {
        val currentUser = _appState.value.user

        usersRepository.update(currentUser.copy(password = _appState.value.password))
    }

    fun resetLogin()
    {
        _loginState.value = LoginState()
    }

    //ROULETTE FUNCTIONS

    fun onRouletteBetChange(bet: String)
    {
        if (!_appState.value.rouletteSpun)
        {
            _appState.update { currentState ->
                currentState.copy(chosenRouletteBet = bet)
            }
        }
    }

    fun onRouletteButtonClick(color: Color)
    {
        if (!_appState.value.rouletteSpun)
        {
            _appState.update { currentState ->
                currentState.copy(chosenRouletteColor = color)
            }
        }
    }

    fun onUpdateAngle(newAngle: Float)
    {
        _appState.update { currentState ->
            currentState.copy(rouletteDegree = newAngle)
        }
    }

    fun onRouletteSpinClick()
    {
        val bet = _appState.value.chosenRouletteBet

        if (!_appState.value.rouletteSpun && !checkIfInputIncorrect(bet, inputType.Money))
        {
            val betAmount = bet.toFloat()
            val currentBalance = _appState.value.money

            val startDegree = _appState.value.rouletteDegree

            // Validate bet amount
            if (betAmount <= 0) {
                _appState.update { currentState ->
                    currentState.copy(rouletteError = "Enter a valid numeric bet amount greater than 0.")
                }
            }
            if (betAmount > currentBalance) {
                _appState.update { currentState ->
                    currentState.copy(rouletteError = "Bet amount exceeds your current balance.")
                }
            }

            _appState.update { currentState ->
                currentState.copy(rouletteSpun = true)
            }
            _appState.update { currentState ->
                currentState.copy(targetRouletteDegree = startDegree + Random.nextFloat() * 360f + 720f)
            }
            _appState.update { currentState ->
                currentState.copy(rotation = Animatable(startDegree))
            }
            _appState.update { currentState ->
                currentState.copy(winningSector = getSector(_appState.value.targetRouletteDegree%360))
            }
            _appState.update { currentState ->
                currentState.copy(money = currentBalance - betAmount)
            }
        }
    }

    suspend fun updateRouletteState()
    {
        val betAmount = _appState.value.chosenRouletteBet.toFloat()
        val currentBalance = _appState.value.money

        val targetDegree = _appState.value.targetRouletteDegree

        val (number, color) = _appState.value.winningSector
        val winnings = when {
            _appState.value.chosenRouletteColor == color && (color == Color.Green || number == 0) -> betAmount * 14f
            (_appState.value.chosenRouletteColor == color) || (number == _appState.value.chosenRouletteNumber)-> betAmount * 2f
            else -> 0f
        }

        _appState.update { currentState ->
            currentState.copy(money = currentBalance + winnings - betAmount)
        }
        saveBalance()

        _appState.update { currentState ->
            currentState.copy(rouletteDegree = targetDegree%360)
        }

        _appState.update { currentState ->
            currentState.copy(rouletteSpun = false)
        }

        val lastFiveResults: MutableList<Float> = _appState.value.lastRouletteResults.toMutableList()
        lastFiveResults.add(0, winnings)
        if (lastFiveResults.size > 5) lastFiveResults.removeAt(lastFiveResults.size - 1)
        _appState.update { currentState ->
            currentState.copy(lastRouletteResults = lastFiveResults)
        }
    }

    private fun getSector(degrees: Float): Pair<Int,Color> {
        val sectors = arrayOf(
            Pair(32, Color.Red), Pair(15, Color.Black), Pair(19, Color.Red), Pair(4, Color.Black), Pair(21, Color.Red), Pair(2, Color.Black), Pair(25, Color.Red), Pair(17, Color.Black), Pair(34, Color.Red), Pair(6, Color.Black), Pair(27, Color.Red), Pair(13, Color.Black), Pair(36, Color.Red), Pair(11, Color.Black), Pair(30, Color.Red), Pair(8, Color.Black), Pair(23, Color.Red), Pair(10, Color.Black), Pair(5, Color.Red), Pair(24, Color.Black), Pair(16, Color.Red), Pair(33, Color.Black), Pair(1, Color.Red), Pair(20, Color.Black), Pair(14, Color.Red), Pair(31, Color.Black), Pair(9, Color.Red), Pair(22, Color.Black), Pair(18, Color.Red), Pair(29, Color.Black), Pair(7, Color.Red), Pair(28, Color.Black), Pair(12, Color.Red), Pair(35, Color.Black), Pair(3, Color.Red), Pair(26, Color.Black), Pair(0, Color.Green)
        )

        for (i in sectors.indices) {
            val start = i * (360f / sectors.size) + sectors.size/2
            val end = start + (360f / sectors.size) + sectors.size/2
            if (degrees > start.toInt() && degrees < end.toInt()) {
                return sectors[i]
            }
        }
        return Pair(0,Color.Green)
    }

    //SLOTS FUNCTIONS

    fun onSlotsBetChange(bet: String)
    {
        if (!_appState.value.slotsSpun)
        {
            _appState.update { currentState ->
                currentState.copy(chosenSlotsBet = bet)
            }
        }
    }

    fun onUpdateSlot()
    {
        val newSlot = (_appState.value.currentSlotSpinning+1)%3

        _appState.update { currentState ->
            currentState.copy(currentSlotSpinning = newSlot)
        }
    }

    fun onSlotsSpinClick()
    {
        val bet = _appState.value.chosenSlotsBet

        if (!_appState.value.slotsSpun && !checkIfInputIncorrect(bet, inputType.Money))
        {
            val betAmount = bet.toFloat()
            val currentBalance = _appState.value.money

            // Validate bet amount
            if (betAmount <= 0) {
                _appState.update { currentState ->
                    currentState.copy(slotsError = "Enter a valid numeric bet amount greater than 0.")
                }
            }
            if (betAmount > currentBalance) {
                _appState.update { currentState ->
                    currentState.copy(slotsError = "Bet amount exceeds your current balance.")
                }
            }

            _appState.update { currentState ->
                currentState.copy(currentSlotSpinning = 0)
            }

            _appState.update { currentState ->
                currentState.copy(slotsSpun = true)
            }

            val id1 = when(Random.nextInt(6))
            {
                0 -> R.drawable.slot1cherry
                1 -> R.drawable.slot2orange
                2 -> R.drawable.slot3purple
                3 -> R.drawable.slot4bell
                4 -> R.drawable.slot5bar
                5 -> R.drawable.slot6seven
                else -> R.drawable.slot1cherry
            }

            _appState.update { currentState ->
                currentState.copy(nextSlot1Id = id1)
            }

            val id2 = when(Random.nextInt(6))
            {
                0 -> R.drawable.slot1cherry
                1 -> R.drawable.slot2orange
                2 -> R.drawable.slot3purple
                3 -> R.drawable.slot4bell
                4 -> R.drawable.slot5bar
                5 -> R.drawable.slot6seven
                else -> R.drawable.slot1cherry
            }

            _appState.update { currentState ->
                currentState.copy(nextSlot2Id = id2)
            }

            val id3 = when(Random.nextInt(6))
            {
                0 -> R.drawable.slot1cherry
                1 -> R.drawable.slot2orange
                2 -> R.drawable.slot3purple
                3 -> R.drawable.slot4bell
                4 -> R.drawable.slot5bar
                5 -> R.drawable.slot6seven
                else -> R.drawable.slot1cherry
            }

            _appState.update { currentState ->
                currentState.copy(nextSlot3Id = id3)
            }
        }
    }

    suspend fun updateSlotsState()
    {
        val betAmount = _appState.value.chosenSlotsBet.toFloat()
        val currentBalance = _appState.value.money

        val result1 = _appState.value.nextSlot1Id
        val result2 = _appState.value.nextSlot2Id
        val result3 = _appState.value.nextSlot3Id

        var reward = betAmount

        when {
            result1 == result2 && result2 == result3 && result1 == R.drawable.slot6seven -> {
                reward *= 7
            }
            result1 == result2 && result2 == result3 -> {
                reward *= 4
            }
            result1 == result2 || result2 == result3 || result1 == result3 -> {
                reward *= 2
            }
            else -> {
                reward *= 0
            }
        }

        _appState.update { currentState ->
            currentState.copy(money = currentBalance + reward - betAmount)
        }
        saveBalance()

        _appState.update { currentState ->
            currentState.copy(slotsSpun = false)
        }

        _appState.update { currentState ->
            currentState.copy(slot1Id = _appState.value.nextSlot1Id)
        }

        _appState.update { currentState ->
            currentState.copy(slot2Id = _appState.value.nextSlot2Id)
        }

        _appState.update { currentState ->
            currentState.copy(slot3Id = _appState.value.nextSlot3Id)
        }

        _appState.update { currentState ->
            currentState.copy(slot1BeginOffset = Animatable(0f))
        }
        _appState.update { currentState ->
            currentState.copy(slot1EndOffset = Animatable(-300f))
        }
        _appState.update { currentState ->
            currentState.copy(slot2BeginOffset = Animatable(0f))
        }
        _appState.update { currentState ->
            currentState.copy(slot2EndOffset = Animatable(-300f))
        }
        _appState.update { currentState ->
            currentState.copy(slot3BeginOffset = Animatable(0f))
        }
        _appState.update { currentState ->
            currentState.copy(slot3EndOffset = Animatable(-300f))
        }

        val lastFiveResults: MutableList<Float> = _appState.value.lastSlotsResults.toMutableList()
        lastFiveResults.add(0, reward)
        if (lastFiveResults.size > 5) lastFiveResults.removeAt(lastFiveResults.size - 1)
        _appState.update { currentState ->
            currentState.copy(lastSlotsResults = lastFiveResults)
        }
    }

    //DICE FUNCTIONS

    fun onDiceBetChange(bet: String)
    {
        if (!_appState.value.diceCast)
        {
            _appState.update { currentState ->
                currentState.copy(chosenDiceBet = bet)
            }
        }
    }

    fun onDiceRollClick()
    {
        val bet = _appState.value.chosenDiceBet

        if (!_appState.value.diceCast && !checkIfInputIncorrect(bet, inputType.Money))
        {
            val betAmount = bet.toFloat()
            val currentBalance = _appState.value.money

            // Validate bet amount
            if (betAmount <= 0) {
                _appState.update { currentState ->
                    currentState.copy(diceError = "Enter a valid numeric bet amount greater than 0.")
                }
            }
            if (betAmount > currentBalance) {
                _appState.update { currentState ->
                    currentState.copy(diceError = "Bet amount exceeds your current balance.")
                }
            }

            _appState.update { currentState ->
                currentState.copy(diceCast = true)
            }

            val id1 = when(Random.nextInt(6))
            {
                0 -> R.drawable.dice_1
                1 -> R.drawable.dice_2
                2 -> R.drawable.dice_3
                3 -> R.drawable.dice_4
                4 -> R.drawable.dice_5
                5 -> R.drawable.dice_6
                else -> R.drawable.dice_1
            }

            _appState.update { currentState ->
                currentState.copy(newAIDice = id1)
            }

            val id2 = when(Random.nextInt(6))
            {
                0 -> R.drawable.dice_1
                1 -> R.drawable.dice_2
                2 -> R.drawable.dice_3
                3 -> R.drawable.dice_4
                4 -> R.drawable.dice_5
                5 -> R.drawable.dice_6
                else -> R.drawable.dice_1
            }

            _appState.update { currentState ->
                currentState.copy(newUserDice = id2)
            }
        }
    }

    suspend fun updateDiceState()
    {
        val betAmount = _appState.value.chosenRouletteBet.toFloat()
        val currentBalance = _appState.value.money

        val newPlayerDice = _appState.value.newUserDice
        val newComputerDice = _appState.value.newAIDice

        var winnings = betAmount
        val resultMessage: String
        when {
            newPlayerDice > newComputerDice ->
            {
                winnings *= 2
                resultMessage = "You Win! 🎉"
            }
            newPlayerDice < newComputerDice -> {
                winnings *= 0
                resultMessage = "You Lose! 😞"
            }
            else -> {
                winnings *= 1
                resultMessage = "It's a Tie! 🤝"
            }
        }

        _appState.update { currentState ->
            currentState.copy(money = currentBalance + winnings - betAmount)
        }
        saveBalance()

        _appState.update { currentState ->
            currentState.copy(diceCast = false)
        }

        _appState.update { currentState ->
            currentState.copy(diceResultMessage = resultMessage)
        }

        val lastFiveResults: MutableList<Float> = _appState.value.lastDiceResults.toMutableList()
        lastFiveResults.add(0, winnings)
        if (lastFiveResults.size > 5) lastFiveResults.removeAt(lastFiveResults.size - 1)
        _appState.update { currentState ->
            currentState.copy(lastDiceResults = lastFiveResults)
        }
    }

    //BLACKJACK FUNCTIONS

    fun onBlackjackBetChange(bet: String)
    {
        if (_appState.value.isGameOver && !_appState.value.blackjackPlayed)
        {
            _appState.update { currentState ->
                currentState.copy(chosenBlackjackBet = bet)
            }
        }
    }

    fun onBlackjackHitClick()
    {
        if (!_appState.value.blackjackPlayed)
        {
            val playerCards = _appState.value.playerCards.toMutableList()

            playerCards.add(drawCard())

            _appState.update { currentState ->
                currentState.copy(playerCards = playerCards)
            }

            val playerHandTotal = calculateHandTotal(playerCards)

            if (playerHandTotal > 21) {
                _appState.update { currentState ->
                    currentState.copy(isGameOver = true)
                }
                _appState.update { currentState ->
                    currentState.copy(isBusted = true)
                }
            }

            _appState.update { currentState ->
                currentState.copy(playerTotal = playerHandTotal)
            }
        }
    }

    suspend fun onBlackjackStandClick()
    {
        if (!_appState.value.blackjackPlayed)
        {
            val dealerCards = _appState.value.dealerCards.toMutableList()

            _appState.update { currentState ->
                currentState.copy(isPlayerTurn = false)
            }

            var dealerHandTotal = calculateHandTotal(dealerCards)

            while (dealerHandTotal < 17) {
                dealerCards.add(drawCard())
                _appState.update { currentState ->
                    currentState.copy(dealerCards = dealerCards)
                }
                dealerHandTotal = calculateHandTotal(dealerCards)
            }

            _appState.update { currentState ->
                currentState.copy(dealerTotal = dealerHandTotal)
            }

            evaluateGameOutcome()

            _appState.update { currentState ->
                currentState.copy(isGameOver = true)
            }
        }

    }

    fun onBlackjackPlayAgainClick()
    {
        val bet = _appState.value.chosenBlackjackBet

        if (!checkIfInputIncorrect(bet, inputType.Money))
        {
            val playerCards = listOf(drawCard(), drawCard())
            val dealerCards = listOf(drawCard(), drawCard())

            _appState.update { currentState ->
                currentState.copy(playerCards = playerCards)
            }
            _appState.update { currentState ->
                currentState.copy(dealerCards = dealerCards)
            }

            _appState.update { currentState ->
                currentState.copy(isGameOver = false)
            }
            _appState.update { currentState ->
                currentState.copy(isPlayerTurn = true)
            }

            _appState.update { currentState ->
                currentState.copy(playerTotal = calculateHandTotal(_appState.value.playerCards))
            }
            _appState.update { currentState ->
                currentState.copy(dealerTotal = calculateHandTotal(_appState.value.dealerCards))
            }
        }
    }

    fun updateBlackjackState()
    {
        //class to run on animation end, to do when doing animations, make more for every animation
    }

    fun updateBlackjackBustedState()
    {
        _appState.update { currentState ->
            currentState.copy(isBusted = false)
        }
    }

    private fun drawCard(): Card
    {
        val suits = listOf("hearts", "spades", "diamonds", "clubs")
        val values = listOf("ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king")
        return Card(suits.random(), values.random())
    }

    private fun calculateHandTotal(cards: List<Card>): Int
    {
        var total = 0
        var aceCount = 0
        cards.forEach { card ->
            when (card.value) {
                "ace" -> {
                    total += 11
                    aceCount++
                }
                "jack", "queen", "king" -> total += 10
                else -> total += card.value.toInt()
            }
        }
        while (total > 21 && aceCount > 0) {
            total -= 10
            aceCount--
        }

        return total
    }

    private suspend fun evaluateGameOutcome()
    {
        val balance = _appState.value.money
        val betAmount = _appState.value.chosenBlackjackBet.toFloat()

        val playerCards = _appState.value.playerCards
        val dealerCards = _appState.value.dealerCards

        val playerTotal = calculateHandTotal(playerCards)
        val dealerTotal = calculateHandTotal(dealerCards)

        when {
            dealerTotal > 21 || playerTotal > dealerTotal -> {
                _appState.update { currentState ->
                    currentState.copy(money = balance + betAmount)
                }
            }
            playerTotal == dealerTotal -> {} //Draw, no change in balance
            else -> {
                _appState.update { currentState ->
                    currentState.copy(money = balance - betAmount)
                }
            }
        }
        saveBalance()

        val lastFiveResults: MutableList<Float> = _appState.value.lastBlackjackResults.toMutableList()
        lastFiveResults.add(0, betAmount*2)
        if (lastFiveResults.size > 5) lastFiveResults.removeAt(lastFiveResults.size - 1)
        _appState.update { currentState ->
            currentState.copy(lastBlackjackResults = lastFiveResults)
        }
    }

    //SETTINGS
    fun onMusicVolumeChange(newVolume: Float)
    {
        _appState.update { currentState ->
            currentState.copy(musicVolume = newVolume)
        }
    }
    fun onSoundVolumeChange(newVolume: Float)
    {
        _appState.update { currentState ->
            currentState.copy(soundVolume = newVolume)
        }
    }
    fun onNotificationsClick()
    {
        val prevNotif = _appState.value.areNotificationsOn

        _appState.update { currentState ->
            currentState.copy(areNotificationsOn = !prevNotif)
        }

        //to do
    }
    fun onThemesClick()
    {
        val prevTheme = _appState.value.altThemeOn

        _appState.update { currentState ->
            currentState.copy(altThemeOn = !prevTheme)
        }

        //to do
    }
    fun onHelpClick()
    {
        //to do
    }
    fun onSignOutClick()
    {
        //save user data
    }
}