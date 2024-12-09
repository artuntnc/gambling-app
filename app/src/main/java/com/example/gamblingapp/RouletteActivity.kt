package com.example.gamblingapp

import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RouletteActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var balance: Int = 0
    //Its checking the last 5 result
    private val lastFiveResults = mutableListOf<String>()

    // Roulette wheel number order and corresponding colors
    private val numberOrder = listOf(
        Pair(0, "Green"), Pair(32, "Red"), Pair(15, "Black"), Pair(19, "Red"), Pair(4, "Black"),
        Pair(21, "Red"), Pair(2, "Black"), Pair(25, "Red"), Pair(17, "Black"), Pair(34, "Red"),
        Pair(6, "Black"), Pair(27, "Red"), Pair(13, "Black"), Pair(36, "Red"), Pair(11, "Black"),
        Pair(30, "Red"), Pair(8, "Black"), Pair(23, "Red"), Pair(10, "Black"), Pair(5, "Red"),
        Pair(24, "Black"), Pair(16, "Red"), Pair(33, "Black"), Pair(1, "Red"), Pair(20, "Black"),
        Pair(14, "Red"), Pair(31, "Black"), Pair(9, "Red"), Pair(22, "Black"), Pair(18, "Red"),
        Pair(29, "Black"), Pair(7, "Red"), Pair(28, "Black"), Pair(12, "Red"), Pair(35, "Black"),
        Pair(3, "Red"), Pair(26, "Black"),
    )

    // Degrees per number for each sector on the roulette wheel but idk why it doesnt show me the
    //wrong number
    private val degreesPerNumber = 360f / numberOrder.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roulette)

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        balance = sharedPreferences.getInt("userBalance", 0)

        // UI elements buttons etc...

        val spinButton: Button = findViewById(R.id.spinButton)
        val betAmountInput: EditText = findViewById(R.id.betAmount)
        val redButton: Button = findViewById(R.id.redButton)
        val blackButton: Button = findViewById(R.id.blackButton)
        val greenButton: Button = findViewById(R.id.greenButton)
        val resultText: TextView = findViewById(R.id.resultTv)
        val lastResultsText: TextView = findViewById(R.id.lastResults)
        val balanceText: TextView = findViewById(R.id.balanceText)

        var selectedColor: String? = null

        // Color selection buttons for the game
        redButton.setOnClickListener { selectedColor = "Red" }
        blackButton.setOnClickListener { selectedColor = "Black" }
        greenButton.setOnClickListener { selectedColor = "Green" }

        // Spin button click handler
        spinButton.setOnClickListener {
            // Validate bet amount it cant be 0 and less than the total balance
            val betAmount = betAmountInput.text.toString().toIntOrNull()
            if (betAmount == null || betAmount <= 0 || betAmount > balance || selectedColor == null) {
                resultText.text = "Invalid input!"
                return@setOnClickListener
            }

            startRouletteSpinAnimation {
                // Random select a number
                val randomNumber = (0..36).random()
                val color = getColorForNumber(randomNumber)

                // Calculate winnings
                val winnings = when {
                    color == selectedColor && color == "Green" -> betAmount * 14
                    color == selectedColor -> betAmount * 2
                    else -> 0
                }

                // Updating the  balance
                balance += winnings - betAmount
                sharedPreferences.edit().putInt("userBalance", balance).apply()

                // Updating results
                resultText.text = "Number: $randomNumber, Color: $color"
                lastFiveResults.add(0, "Number: $randomNumber, Color: $color")
                if (lastFiveResults.size > 5) lastFiveResults.removeAt(lastFiveResults.size - 1)

                // Showing last 5 results
                lastResultsText.text = lastFiveResults.joinToString("\n")

                // Updating balance display
                balanceText.text = "Balance: $$balance"
            }
        }
    }

    // Function to start the roulette spin animation
    private fun startRouletteSpinAnimation(onAnimationEnd: () -> Unit) {
        val wheel: ImageView = findViewById(R.id.wheel)

        // Randomly select a number
        val randomNumber = (0..36).random()

        // Find the index of the selected number in the wheel
        val numberIndex = numberOrder.indexOfFirst { it.first == randomNumber }
        val targetDegree = numberIndex * degreesPerNumber //calculating the target degree

        // Get the current rotation of the wheel
        val currentRotation = wheel.rotation % 360f

        val correctedTargetDegree = (360f * 5) + (360f - currentRotation + targetDegree) % 360f

       //Objectanimator for rotating wheel
        val rotateAnim = ObjectAnimator.ofFloat(wheel, "rotation", currentRotation, correctedTargetDegree)
        rotateAnim.duration = 3000 // 3 seconds duration
        rotateAnim.interpolator = AccelerateDecelerateInterpolator() // Smooth acceleration and deceleration

        rotateAnim.addListener(object : android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(animation: android.animation.Animator) {}

            override fun onAnimationEnd(animation: android.animation.Animator) {
                onAnimationEnd()
            }

            override fun onAnimationCancel(animation: android.animation.Animator) {}

            override fun onAnimationRepeat(animation: android.animation.Animator) {}
        })

        rotateAnim.start()
    }

    // Function to return the color for a given number
    private fun getColorForNumber(number: Int): String {
        return numberOrder.firstOrNull { it.first == number }?.second ?: "Black"
    }
}
