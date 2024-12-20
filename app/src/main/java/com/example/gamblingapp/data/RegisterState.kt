package com.example.gamblingapp.data

data class RegisterState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val birthDate: String = "",
    val pesel: String = ""
)