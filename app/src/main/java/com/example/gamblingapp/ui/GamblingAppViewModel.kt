package com.example.gamblingapp.ui

import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
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
        _appState.update { currentState ->
            currentState.copy(chosenRouletteBet = bet.toFloat())
        }
    }

    fun onRouletteButtonClick(color: Color)
    {
        _appState.update { currentState ->
            currentState.copy(chosenRouletteColor = color)
        }
    }

    fun onRouletteSpinClick()
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
            currentState.copy(targetRouletteDegree = Random.nextFloat()*360f+720f)
        }
        _appState.update { currentState ->
            currentState.copy(winningSector = getSector(_appState.value.targetRouletteDegree))
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
            currentState.copy(rouletteDegree = newAngle)
        }

        _appState.update { currentState ->
            currentState.copy(rouletteSpun = false)
        }

        var lastFiveResults: MutableList<Float> = _appState.value.lastResult.toMutableList()
        lastFiveResults.add(0, winnings)
        if (lastFiveResults.size > 5) lastFiveResults.removeAt(lastFiveResults.size - 1)
        _appState.update { currentState ->
            currentState.copy(lastResult = lastFiveResults)
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
}