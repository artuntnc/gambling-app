package com.example.gamblingapp.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.gamblingapp.R
import com.example.gamblingapp.data.GamblingAppState
import com.example.gamblingapp.data.LoginState
import com.example.gamblingapp.data.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GamblingAppViewModel : ViewModel()
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
        return when(input) {
            inputType.Email -> false
            inputType.Password -> false
            inputType.Date -> false
            inputType.FullName -> false
            inputType.Pesel -> false
            inputType.Money -> false
        }

        return true
    }

    fun getUserData(): Boolean
    {
        //should get data from the computer for now just set money
        _appState.update { currentState ->
            currentState.copy(money = 1000.0f)
        }
        return true
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
                currentState.copy(chosenRouletteBet = bet.toFloat())
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

    fun onRouletteSpinClick()
    {
        if (!_appState.value.rouletteSpun)
        {
            val betAmount = _appState.value.chosenRouletteBet
            val currentBalance = _appState.value.money

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
                currentState.copy(targetRouletteDegree = Random.nextFloat() * 360f + 720f)
            }
            _appState.update { currentState ->
                currentState.copy(winningSector = getSector(_appState.value.targetRouletteDegree))
            }
        }
    }

    fun updateRouletteState(newAngle: Float)
    {
        val betAmount = _appState.value.chosenRouletteBet
        val currentBalance = _appState.value.money

        val (number, color) = _appState.value.winningSector
        val winnings = when {
            _appState.value.chosenRouletteColor == color && (color == Color.Green || number == 0) -> betAmount * 14f
            _appState.value.chosenRouletteColor == color -> betAmount * 2f
            else -> 0f
        }

        _appState.update { currentState ->
            currentState.copy(money = currentBalance + winnings - betAmount)
        }

        _appState.update { currentState ->
            currentState.copy(rouletteDegree = newAngle%360)
        }

        _appState.update { currentState ->
            currentState.copy(rouletteSpun = false)
        }

        var lastFiveResults: MutableList<Float> = _appState.value.lastRouletteResults.toMutableList()
        lastFiveResults.add(0, winnings)
        if (lastFiveResults.size > 5) lastFiveResults.removeAt(lastFiveResults.size - 1)
        _appState.update { currentState ->
            currentState.copy(lastRouletteResults = lastFiveResults)
        }
    }

    private fun getSector(degrees: Float): Pair<Int,Color> {
        val sectors = arrayOf(
            Pair(32, Color.Red), Pair(15, Color.Black), Pair(19, Color.Red), Pair(4, Color.Black), Pair(21, Color.Red), Pair(2, Color.Black), Pair(25, Color.Red), Pair(17, Color.Black), Pair(34, Color.Red), Pair(6, Color.Black), Pair(27, Color.Red), Pair(13, Color.Black), Pair(36, Color.Red), Pair(11, Color.Black), Pair(30, Color.Red), Pair(8, Color.Black), Pair(23, Color.Red), Pair(10, Color.Black), Pair(5, Color.Red), Pair(24, Color.Black), Pair(16, Color.Red), Pair(33, Color.Black), Pair(1, Color.Red), Pair(20, Color.Black), Pair(14, Color.Red), Pair(31, Color.Black), Pair(9, Color.Red), Pair(22, Color.Black), Pair(18, Color.Red), Pair(29, Color.Black), Pair(17, Color.Red), Pair(28, Color.Black), Pair(12, Color.Red), Pair(35, Color.Black), Pair(3, Color.Red), Pair(26, Color.Black), Pair(0, Color.Green)
        )

        for (i in sectors.indices) {
            val start = i * (360f / sectors.size)
            val end = start + (360f / sectors.size)
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
                currentState.copy(chosenSlotsBet = bet.toFloat())
            }
        }
    }

    fun onSlotsSpinClick()
    {
        if (!_appState.value.slotsSpun)
        {
            val betAmount = _appState.value.chosenSlotsBet
            val currentBalance = _appState.value.money

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
                currentState.copy(nextSlot1Id = id3)
            }
        }
    }

    fun updateSlotsState()
    {
        val betAmount = _appState.value.chosenRouletteBet
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

        _appState.update { currentState ->
            currentState.copy(slotsSpun = false)
        }

        _appState.update { currentState ->
            currentState.copy(slot1Id = _appState.value.nextSlot1Id)
        }

        _appState.update { currentState ->
            currentState.copy(slot1Id = _appState.value.nextSlot1Id)
        }

        _appState.update { currentState ->
            currentState.copy(slot1Id = _appState.value.nextSlot1Id)
        }


        var lastFiveResults: MutableList<Float> = _appState.value.lastSlotsResults.toMutableList()
        lastFiveResults.add(0, reward)
        if (lastFiveResults.size > 5) lastFiveResults.removeAt(lastFiveResults.size - 1)
        _appState.update { currentState ->
            currentState.copy(lastRouletteResults = lastFiveResults)
        }
    }

    //DICE FUNCTIONS

    fun onDiceBetChange(bet: String)
    {
        if (!_appState.value.diceCast)
        {
            _appState.update { currentState ->
                currentState.copy(chosenDiceBet = bet.toFloat())
            }
        }
    }

    fun onDiceSpinClick()
    {
        if (!_appState.value.diceCast)
        {
            val betAmount = _appState.value.chosenDiceBet
            val currentBalance = _appState.value.money

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

    fun updateDiceState(newPlayerDice: Int, newComputerDice: Int)
    {
        val betAmount = _appState.value.chosenRouletteBet
        val currentBalance = _appState.value.money


        val (number, color) = _appState.value.winningSector
        var winnings = betAmount
        var resultMessage = ""
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

        _appState.update { currentState ->
            currentState.copy(rouletteSpun = false)
        }

        _appState.update { currentState ->
            currentState.copy(rouletteSpun = false)
        }

        var lastFiveResults: MutableList<Float> = _appState.value.lastDiceResults.toMutableList()
        lastFiveResults.add(0, winnings)
        if (lastFiveResults.size > 5) lastFiveResults.removeAt(lastFiveResults.size - 1)
        _appState.update { currentState ->
            currentState.copy(lastRouletteResults = lastFiveResults)
        }
    }
}