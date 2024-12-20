package com.example.gamblingapp.data

import androidx.compose.ui.graphics.Color

data class GamblingAppState(
    //current user's username
    val username: String = "",

    //current user's password
    val password: String = "",

    //current user's money
    val money: Float = 1000.0f,

    //bool for displaying the top bar with money and back button
    val hideTopBar: Boolean = true,

    //last results in roulette game
    val lastResult: List<Float> = listOf(),

    //chosen color for roulette game
    val chosenColor: Color = Color.Red
)