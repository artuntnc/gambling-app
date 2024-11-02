package com.example.gamblingapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.TextView


class SplashActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.progress_bar)
        progressText = findViewById(R.id.progress_text)

        // Update the loading bar in 5 seconds
        val handler = Handler(Looper.getMainLooper())
        val totalTime = 5000
        val step = 100 // every 100 ms step the loading bar 10br
        var progressStatus = 0

        Thread {
            while (progressStatus < 100) {
                progressStatus += 10 // Every cycle increase the loading bar %10
                handler.post {
                    progressBar.progress = progressStatus
                    progressText.text = "$progressStatus"
                }
                Thread.sleep(step.toLong())
            }

            // After 5 second go to the main activiy
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }.start()
    }
}