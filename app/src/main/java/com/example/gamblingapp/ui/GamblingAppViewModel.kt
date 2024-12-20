package com.example.gamblingapp.ui

import androidx.lifecycle.ViewModel
import com.example.gamblingapp.data.GamblingAppState
import com.example.gamblingapp.data.LoginState
import com.example.gamblingapp.data.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
        Pesel
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

    fun checkIfInputIncorrect(text: String, input: inputType): Boolean
    {
        return when(input) {
            inputType.Email -> false
            inputType.Password -> false
            inputType.Date -> false
            inputType.FullName -> false
            inputType.Pesel -> false
        }

        return true
    }

    fun resetLogin()
    {
        _loginState.value = LoginState()
    }
}