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
import com.example.gamblingapp.data.LocalDataStoreManager
import com.example.gamblingapp.data.LoginState
import com.example.gamblingapp.data.RegisterState
import com.example.gamblingapp.data.User
import com.example.gamblingapp.data.UsersRepository
import kotlinx.coroutines.delay
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

    fun setEmail(email: String)
    {
        _appState.update { currentState ->
            currentState.copy(email = email)
        }
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
            currentState.copy(showComingSoonDialog = !comingSoonCopy)
        }
    }

    fun changeIncorrectInputState()
    {
        val incorrectInputCopy = _appState.value.showIncorrectInputDialog

        _appState.update { currentState ->
            currentState.copy(showIncorrectInputDialog = !incorrectInputCopy)
        }
    }

    fun changeIncorrectBetState()
    {
        val incorrectBetCopy = _appState.value.showIncorrectBetDialog

        _appState.update { currentState ->
            currentState.copy(showIncorrectBetDialog = !incorrectBetCopy)
        }
    }

    fun changeUserNotExistState()
    {
        val userNotExistCopy = _appState.value.showUserNotExistDialog

        _appState.update { currentState ->
            currentState.copy(showUserNotExistDialog = !userNotExistCopy)
        }
    }

    fun changeUserExistState()
    {
        val userExistCopy = _appState.value.showUserExistDialog

        _appState.update { currentState ->
            currentState.copy(showUserExistDialog = !userExistCopy)
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
            inputType.Date -> """^(0?[1-9]|[12][0-9]|3[01])[\/](0?[1-9]|1[012])[\/]\d{4}$""".toRegex()
            inputType.FullName -> """(^[A-Za-z]{3,16})( ?)([A-Za-z]{3,16})?( ?)?([A-Za-z]{3,16})?( ?)?([A-Za-z]{3,16})""".toRegex()
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

        if(checkIfInputIncorrect(email, inputType.Email) || checkIfInputIncorrect(password, inputType.Password))
        {
            changeIncorrectInputState()
            return false
        }

        if(!usersRepository.doesUserExist(email,password))
        {
            changeUserNotExistState()
            return false
        }

        val userFlow = usersRepository.getUserStream(email,password)

        val user = userFlow.first()

        _appState.update { currentState ->
            currentState.copy(username = user.fullName, email = user.email, money = user.balance, user = user)
        }

        return true
    }

    suspend fun setUserData(): Boolean
    {
        val email = _registerState.value.email
        val password = _registerState.value.password
        val birthDate = _registerState.value.birthDate
        val pesel = _registerState.value.pesel
        val fullName = _registerState.value.fullName

        if(checkIfInputIncorrect(email, inputType.Email) || checkIfInputIncorrect(password, inputType.Password) || checkIfInputIncorrect(birthDate, inputType.Date) || checkIfInputIncorrect(pesel, inputType.Pesel) || checkIfInputIncorrect(fullName, inputType.FullName))
        {
            changeIncorrectInputState()
            return false
        }

        val emailExists = usersRepository.doesUserEmailExist(email)
        val peselExists = usersRepository.doesUserPeselExist(pesel)

        if(emailExists || peselExists)
        {
            changeUserExistState()
            return false
        }

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

        usersRepository.insertUser(currentUser)
        _appState.update { currentState ->
            currentState.copy(user = currentUser)
        }

        return true
    }

    private suspend fun saveBalance()
    {
        val currentUser = _appState.value.user

        usersRepository.updateUser(currentUser.copy(balance = _appState.value.money))
    }

    suspend fun savePassword()
    {
        val currentUser = _appState.value.user

        usersRepository.updateUser(currentUser.copy(password = _appState.value.password))
    }

    fun resetLogin()
    {
        _loginState.value = LoginState()
    }

    fun getUserFromLocal()
    {
        viewModelScope.launch {
            val user = usersRepository.getUserEmailStream(_appState.value.email).first()

            _appState.update { currentState ->
                currentState.copy(username = user.fullName, email = user.email, money = user.balance, user = user)
            }
        }
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
        else
        {
            changeIncorrectBetState()
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

    fun onUpdateSlot(slot: Int)
    {
        val id = when(Random.nextInt(6))
        {
            0 -> R.drawable.slot1cherry
            1 -> R.drawable.slot2orange
            2 -> R.drawable.slot3purple
            3 -> R.drawable.slot4bell
            4 -> R.drawable.slot5bar
            5 -> R.drawable.slot6seven
            else -> R.drawable.slot1cherry
        }

        when(slot){
            1 -> {
                _appState.update { currentState ->
                    currentState.copy(slot1Id = _appState.value.nextSlot1Id)
                }
                _appState.update { currentState ->
                    currentState.copy(nextSlot1Id = id)
                }
            }
            2 -> {
                _appState.update { currentState ->
                    currentState.copy(slot2Id = _appState.value.nextSlot2Id)
                }
                _appState.update { currentState ->
                    currentState.copy(nextSlot2Id = id)
                }
            }
            3 -> {
                _appState.update { currentState ->
                    currentState.copy(slot3Id = _appState.value.nextSlot3Id)
                }
                _appState.update { currentState ->
                    currentState.copy(nextSlot3Id = id)
                }
            }
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
        else
        {
            changeIncorrectBetState()
        }
    }

    suspend fun updateSlotsState()
    {
        val betAmount = _appState.value.chosenSlotsBet.toFloat()
        val currentBalance = _appState.value.money

        val result1 = _appState.value.slot1Id
        val result2 = _appState.value.slot2Id
        val result3 = _appState.value.slot3Id

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
            _appState.update { currentState ->
                currentState.copy(diceResultMessage = "...")
            }
        }
        else
        {
            changeIncorrectBetState()
        }
    }

    suspend fun updateDiceState()
    {
        val betAmount = _appState.value.chosenDiceBet.toFloat()
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

    fun updateAIDice(newDice: Int)
    {
        val id = when(newDice)
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
            currentState.copy(newAIDice = id)
        }
    }

    fun updatePlayerDice(newDice: Int)
    {
        val id = when(newDice)
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
            currentState.copy(newUserDice = id)
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

            if (playerHandTotal > 21)
            {
                _appState.update { currentState ->
                    currentState.copy(isGameOver = true)
                }
                _appState.update { currentState ->
                    currentState.copy(isBusted = true)
                }

                viewModelScope.launch {
                    _appState.update { currentState ->
                        currentState.copy(money = _appState.value.money - _appState.value.chosenBlackjackBet.toFloat())
                    }
                    saveBalance()
                }

                val lastFiveResults: MutableList<Float> = _appState.value.lastBlackjackResults.toMutableList()
                lastFiveResults.add(0, 0f)
                if (lastFiveResults.size > 5) lastFiveResults.removeAt(lastFiveResults.size - 1)
                _appState.update { currentState ->
                    currentState.copy(lastBlackjackResults = lastFiveResults)
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
                delay(500)
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
        else
        {
            changeIncorrectBetState()
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

        var winnings = betAmount
        when {
            dealerTotal > 21 || playerTotal > dealerTotal -> {
                winnings *= 2
            }
            playerTotal == dealerTotal -> {} //Draw, no change in balance
            else -> {
                winnings *= 0
            }
        }


        _appState.update { currentState ->
            currentState.copy(money = balance + winnings - betAmount)
        }
        saveBalance()

        val lastFiveResults: MutableList<Float> = _appState.value.lastBlackjackResults.toMutableList()
        lastFiveResults.add(0, winnings)
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
    fun onSoundVolumeChange(newVolume: Float): Float
    {
        _appState.update { currentState ->
            currentState.copy(soundVolume = newVolume)
        }

        return newVolume
    }
    fun onNotificationsClick(): Boolean
    {
        val prevNotif = !_appState.value.areNotificationsOn

        _appState.update { currentState ->
            currentState.copy(areNotificationsOn = prevNotif)
        }

        return prevNotif
    }
    fun onThemesClick(): Boolean
    {
        val prevTheme = !_appState.value.altThemeOn

        _appState.update { currentState ->
            currentState.copy(altThemeOn = prevTheme)
        }

        return prevTheme
    }
    fun onHelpClick()
    {
        //to do
    }
    fun onSignOutClick()
    {
        //to do
    }

    //MONEY STORE
    fun get1000Coins()
    {
        viewModelScope.launch {
            val prevBalance = _appState.value.money

            _appState.update { currentState ->
                currentState.copy(money = prevBalance + 1000f)
            }
            saveBalance()
        }
    }
    fun get100Coins()
    {
        viewModelScope.launch {
            val prevBalance = _appState.value.money

            _appState.update { currentState ->
                currentState.copy(money = prevBalance + 100f)
            }
            saveBalance()
        }
    }
    fun getFreeCoins()
    {
        viewModelScope.launch {
            val prevBalance = _appState.value.money

            _appState.update { currentState ->
                currentState.copy(money = prevBalance + 50f)
            }
            saveBalance()
        }
    }
}