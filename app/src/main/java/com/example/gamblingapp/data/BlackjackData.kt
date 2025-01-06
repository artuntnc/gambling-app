package com.example.gamblingapp.data

import com.example.gamblingapp.R

data class Card(val suit: String, val value: String)
{
    fun getImageResource(): Int
    {
        return when ("${value}_of_${suit}")
        {
            "ace_of_clubs" -> R.drawable.ace_of_clubs
            "2_of_clubs" -> R.drawable.two_of_clubs
            "3_of_clubs" -> R.drawable.three_of_clubs
            "4_of_clubs" -> R.drawable.four_of_clubs
            "5_of_clubs" -> R.drawable.five_of_clubs
            "6_of_clubs" -> R.drawable.six_of_clubs
            "7_of_clubs" -> R.drawable.seven_of_clubs
            "8_of_clubs" -> R.drawable.eight_of_clubs
            "9_of_clubs" -> R.drawable.nine_of_clubs
            "10_of_clubs" -> R.drawable.ten_of_clubs
            "jack_of_clubs" -> R.drawable.jack_of_clubs2
            "king_of_clubs" -> R.drawable.king_of_clubs2
            "queen_of_clubs" -> R.drawable.queen_of_clubs2

            "ace_of_diamonds" -> R.drawable.ace_of_diamonds
            "2_of_diamonds" -> R.drawable.two_of_diamonds
            "3_of_diamonds" -> R.drawable.three_of_diamonds
            "4_of_diamonds" -> R.drawable.four_of_diamonds
            "5_of_diamonds" -> R.drawable.five_of_diamonds
            "6_of_diamonds" -> R.drawable.six_of_diamonds
            "7_of_diamond" -> R.drawable.seven_of_diamonds
            "8_of_diamonds"-> R.drawable.eight_of_diamonds
            "9_of_diamonds" -> R.drawable.nine_of_diamonds
            "10_of_diamonds" -> R.drawable.ten_of_diamonds
            "jack_of_diamonds" -> R.drawable.jack_of_diamonds2
            "king_of_diamonds" -> R.drawable.king_of_diamonds2
            "queen_of_diamonds" -> R.drawable.queen_of_diamonds2

            "ace_of_hearts" -> R.drawable.ace_of_hearts
            "2_of_hearts" -> R.drawable.two_of_hearts
            "3_of_hearts" -> R.drawable.three_of_hearts
            "4_of_hearts" -> R.drawable.four_of_hearts
            "5_of_hearts" -> R.drawable.five_of_hearts
            "6_of_hearts" -> R.drawable.six_of_hearts
            "7_of_hearts" -> R.drawable.seven_of_hearts
            "8_of_hearts" -> R.drawable.eight_of_hearts
            "9_of_hearts" -> R.drawable.nine_of_hearts
            "10_of_hearts" -> R.drawable.ten_of_hearts
            "jack_of_hearts" -> R.drawable.jack_of_hearts2
            "queen_of_hearts" -> R.drawable.queen_of_hearts2
            "king_of_hearts" -> R.drawable.king_of_hearts2

            "ace_of_spades" -> R.drawable.ace_of_spades2
            "2_of_spades" -> R.drawable.two_of_spades
            "3_of_spades" -> R.drawable.three_of_spades
            "4_of_spades" -> R.drawable.four_of_spades
            "5_of_spades" -> R.drawable.five_of_spades
            "6_of_spades" -> R.drawable.six_of_spades
            "7_of_spades" -> R.drawable.seven_of_spades
            "8_of_spades" -> R.drawable.eight_of_spades
            "9_of_spades" -> R.drawable.nine_of_spades
            "10_of_spades" -> R.drawable.ten_of_spades
            "jack_of_spades" -> R.drawable.jack_of_spades2
            "queen_of_spades" -> R.drawable.queen_of_spades2
            "king_of_spades" -> R.drawable.king_of_spades2
            else -> R.drawable.cardbacksite
        }
    }
}