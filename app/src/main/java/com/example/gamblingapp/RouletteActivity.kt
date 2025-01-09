package com.example.gamblingapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.*
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class RouletteActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var sharedPreferences: SharedPreferences
    private var balance: Int = 0
    private val lastFiveResults = mutableListOf<String>()

    // Sectors of the wheel
    private val sectors = arrayOf(
        "32 red", "15 black", "19 red", "4 black", "21 red", "2 black", "25 red", "17 black", "34 red",
        "6 black", "27 red", "13 black", "36 red", "11 black", "30 red", "8 black", "23 red", "10 black",
        "5 red", "24 black", "16 red", "33 black", "1 red", "20 black", "14 red", "31 black", "9 red",
        "22 black", "18 red", "29 black", "7 red", "28 black", "12 red", "35 black", "3 red", "26 black", "0 green "
    )

    private var degree = 0
    private var degreeOld = 0
    private val random = Random
    private val halfSector = 360f / 37f / 2f

    private var selectedColor: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        mediaPlayer = MediaPlayer.create(this, R.raw.roulettespin)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roulette)

        // SharedPreferences setup
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        balance = sharedPreferences.getInt("userBalance", 1000) // Default balance 1000

        // Bind UI components
        val wheel: ImageView = findViewById(R.id.wheel)
        val spinButton: Button = findViewById(R.id.spinButton)
        val betAmountInput: EditText = findViewById(R.id.betAmount)
        val redButton: Button = findViewById(R.id.redButton)
        val blackButton: Button = findViewById(R.id.blackButton)
        val greenButton: Button = findViewById(R.id.greenButton)
        val resultText: TextView = findViewById(R.id.resultTv)
        val lastResultsText: TextView = findViewById(R.id.lastResults)
        val balanceText: TextView = findViewById(R.id.balanceText)

        balanceText.text = "Balance: $$balance"

        // Color selection buttons
        redButton.setOnClickListener { selectedColor = "red" }
        blackButton.setOnClickListener { selectedColor = "black" }
        greenButton.setOnClickListener { selectedColor = "green" }

        // Spin button
        spinButton.setOnClickListener {
            val betAmountStr = betAmountInput.text.toString().trim()
            val betAmount = betAmountStr.toIntOrNull()

            // Validate bet amount
            if (betAmountStr.isEmpty() || betAmount == null || betAmount <= 0) {
                Toast.makeText(this, "Enter a valid numeric bet amount greater than 0.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (betAmount > balance) {
                Toast.makeText(this, "Bet amount exceeds your current balance.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (selectedColor == null) {
                Toast.makeText(this, "Please select a color to bet on.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mediaPlayer.start()
            // Start the spin
            spin(wheel) { winningSector ->
                val (number, color) = winningSector.split(" ")
                val winnings = when {
                    selectedColor == "green" && color == "green"-> betAmount * 14
                    selectedColor == color -> betAmount * 2
                    else -> 0
                }
                balance += winnings - betAmount
                sharedPreferences.edit().putInt("userBalance", balance).apply()

                // Update UI
                resultText.text = "Result: $winningSector"
                lastFiveResults.add(0, winningSector)
                if (lastFiveResults.size > 5) lastFiveResults.removeAt(lastFiveResults.size - 1)
                lastResultsText.text = lastFiveResults.joinToString("\n")
                balanceText.text = "Balance: $$balance"
                mediaPlayer.stop()
                mediaPlayer.prepare()
            }
        }
    }

    private fun spin(wheel: ImageView, onResult: (String) -> Unit) {
        degreeOld = degree % 360
        degree = random.nextInt(360) + 720

        val rotateAnim = RotateAnimation(
            degreeOld.toFloat(), degree.toFloat(),
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnim.duration = 3600
        rotateAnim.fillAfter = true
        rotateAnim.interpolator = DecelerateInterpolator()
        rotateAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Clear result
            }

            override fun onAnimationEnd(animation: Animation?) {
                val sector = getSector(360 - (degree % 360))
                onResult(sector)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        wheel.startAnimation(rotateAnim)
    }

    private fun getSector(degrees: Int): String {
        for (i in sectors.indices) {
            val start = i * (360f / sectors.size)
            val end = start + (360f / sectors.size)
            if (degrees in start.toInt() until end.toInt()) {
                return sectors[i]
            }
        }
        return "zero"
    }
}
