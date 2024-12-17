package com.example.gamblingapp.ui

import androidx.lifecycle.ViewModel
import com.example.gamblingapp.data.GamblingAppState
import com.example.gamblingapp.data.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GamblingAppViewModel : ViewModel()
{
    private val _appState = MutableStateFlow(GamblingAppState())
    val appState: StateFlow<GamblingAppState> = _appState.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    enum class inputType
    {
        Email,
        Password,
        Date,
        FullName,
        Pesel
    }


    fun checkIfInputCorrect(text: String, input: inputType): Boolean
    {
        when(input) {
            inputType.Email -> TODO()
            inputType.Password -> TODO()
            inputType.Date -> TODO()
            inputType.FullName -> TODO()
            inputType.Pesel -> TODO()
        }

        return true
    }
}