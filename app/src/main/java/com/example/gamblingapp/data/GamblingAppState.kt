package com.example.gamblingapp.data

data class GamblingAppState(
    //current user's username
    val username: String = "",

    //current user's password
    val password: String = "",

    //current user's money
    val money: Float = 1000.0f,

    //bool for displaying the top bar with money and back button
    val hideTopBar: Boolean = true
)