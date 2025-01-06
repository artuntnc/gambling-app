package com.example.gamblingapp.data

import android.text.BoringLayout
import androidx.compose.ui.graphics.Color
import com.example.gamblingapp.R

data class GamblingAppState(
    //current user's username
    val username: String = "",
    //current user's password
    val password: String = "",
    //current user's money
    val money: Float = 1000.0f,

    //sound value
    val soundVolume: Float = 0.5f,
    //music value
    val musicVolume: Float = 0.5f,
    //alt theme check
    val altThemeOn: Boolean = false,
    //notifications check
    val areNotificationsOn: Boolean = false,

    //bool for displaying the top bar with money and user
    val hideTopBar: Boolean = true,

    //last results in roulette game
    val lastRouletteResults: List<Float> = listOf(),
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
    val winningSector: Pair<Int, Color> = Pair(-1, Color.Yellow),

    //last results in slots game
    val lastSlotsResults: List<Float> = listOf(),
    //slots error message
    val slotsError: String = "",
    //check for whether the slots have begun spinning
    val slotsSpun: Boolean = false,
    //bet set by the user
    val chosenSlotsBet: Float = 0.0f,
    //current slot 1
    val slot1Id: Int = R.drawable.slot1cherry,
    //current slot 2
    val slot2Id: Int = R.drawable.slot1cherry,
    //current slot 3
    val slot3Id: Int = R.drawable.slot1cherry,
    //next slot 1
    val nextSlot1Id: Int = R.drawable.slot1cherry,
    //next slot 2
    val nextSlot2Id: Int = R.drawable.slot1cherry,
    //next slot 3
    val nextSlot3Id: Int = R.drawable.slot1cherry,

    //last results in dice game
    val lastDiceResults: List<Float> = listOf(),
    //dice error message
    val diceError: String = "",
    //check for whether the dice have been cast
    val diceCast: Boolean = false,
    //bet set by the user
    val chosenDiceBet: Float = 0.0f,
    //new dice upon a roll
    val newUserDice: Int = R.drawable.dice_1,
    //new dice upon a roll
    val newAIDice: Int = R.drawable.dice_1,
    //Result message displayed after playing
    val diceResultMessage: String = "Roll the dice to play!",

    //last results in dice game
    val lastBlackjackResults: List<Float> = listOf(),
    //blackjack error message
    val blackjackError: String = "",
    //check for whether the user chose to do something
    val blackjackPlayed: Boolean = false,
    //bet set by the user
    val chosenBlackjackBet: Float = 0.0f,
    //cards in player's hands
    val playerCards: List<Card> = listOf(),
    //cards in player's hands
    val dealerCards: List<Card> = listOf(),
    //check for player turn
    val isPlayerTurn: Boolean = false,
    //check for if the game is over
    val isGameOver: Boolean = true,
    //check for if the player is busted
    val isBusted: Boolean = false,
    //player hands value
    val playerTotal: Int = 0,
    //player hands value
    val dealerTotal: Int = 0,
)