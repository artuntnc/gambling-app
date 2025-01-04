package com.example.gamblingapp.data

import androidx.compose.ui.graphics.Color

data class GamblingAppState(
    //current user's username
    val username: String = "",

    //current user's password
    val password: String = "",

    //current user's money
    val money: Float = 1000.0f,

    //bool for displaying the top bar with money and user
    val hideTopBar: Boolean = true,

    //last results in roulette game
    val lastResult: List<Float> = listOf(),

    //chosen color for roulette game
    val chosenRouletteColor: Color = Color.Red,

    //bet set by the user
    val chosenRouletteBet: Float = 0.0f,

    //current tilt of the roulette
    val rouletteDegree: Float = 0f,

    //target tilt of the roulette
    val targetRouletteDegree: Float = 0f,

    //roulette error message
    val rouletteError: String = "",

    //check for whether the roulette has begun spinning
    val rouletteSpun: Boolean = false,

    //roulette current winning pair
    val winningSector: Pair<Int, Color> = Pair(-1, Color.Yellow)
)