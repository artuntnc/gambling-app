package com.example.gamblingapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class SlotActivity : AppCompatActivity() {
    private lateinit var slot1: ImageView
    private lateinit var slot2: ImageView
    private lateinit var slot3: ImageView
    private lateinit var spinButton: Button
    private lateinit var balanceText: TextView

    private var balance = 1000
    private var mediaPlayer: MediaPlayer? = null

    private val images = listOf(
        R.drawable.slot1cherry,  // Cherry
        R.drawable.slot2orange,  // Orange
        R.drawable.slot3purple,  // Purple
        R.drawable.slot4bell,    // Bell
        R.drawable.slot5bar,     // Bar
        R.drawable.slot6seven    // Seven
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slot_machine)


        slot1 = findViewById(R.id.slot1)
        slot2 = findViewById(R.id.slot2)
        slot3 = findViewById(R.id.slot3)
        spinButton = findViewById(R.id.spinButton)
        balanceText = findViewById(R.id.balanceText)

        updateBalanceText()

        spinButton.setOnClickListener {
            spinSlots()
        }
    }

    private fun spinSlots() {
        spinButton.isEnabled = false
        BackgroundMusicManager.pauseMusic()

        mediaPlayer = MediaPlayer.create(this, R.raw.slotmachinesound).apply {
            isLooping = true
            start()
        }

        val results = IntArray(3) { Random.nextInt(images.size) }

        animateSlot(slot1, results[0]) {
            animateSlot(slot2, results[1]) {
                animateSlot(slot3, results[2]) {
                    calculateResult(results[0], results[1], results[2])
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null
                    BackgroundMusicManager.playMusic()
                }
            }
        }
    }

    private fun animateSlot(slot: ImageView, finalIndex: Int, onEnd: () -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        var currentIndex = 0
        val totalDuration = 2000L
        val interval = 100L

        val startTime = System.currentTimeMillis()

        val runnable = object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                if (elapsedTime < totalDuration) {

                    slot.setImageResource(images[currentIndex])
                    currentIndex = (currentIndex + 1) % images.size
                    handler.postDelayed(this, interval)
                } else {

                    slot.setImageResource(images[finalIndex])
                    onEnd()
                }
            }
        }

        handler.post(runnable)
    }

    private fun calculateResult(result1: Int, result2: Int, result3: Int) {
        when {
            result1 == result2 && result2 == result3 -> {

                val reward = 300
                balance += reward
                balanceText.text = "Jackpot! You won $$reward!"
            }
            result1 == result2 || result2 == result3 || result1 == result3 -> {

                val reward = 100
                balance += reward
                balanceText.text = "You won $$reward!"
            }
            else -> {

                balance -= 50
                balanceText.text = "No match. Try again!"
            }
        }

        updateBalanceText()

        if (balance <= 0) {
            spinButton.isEnabled = false
            balanceText.text = "Game Over! Balance: $0"
        } else {
            spinButton.isEnabled = true
        }
    }

    private fun updateBalanceText() {
        balanceText.text = "Balance: $$balance"
    }
    override fun onDestroy() {

        mediaPlayer?.release()
        super.onDestroy()
    }
}
