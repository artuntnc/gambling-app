package com.example.gamblingapp.managers

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

object BackgroundMusicManager {
    private var mediaPlayer: MediaPlayer? = null
    private var isPaused: Boolean = false

    fun initialize(context: Context, resourceId: Int) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resourceId).apply {
                isLooping = true
            }
            Log.d("BackgroundMusicManager", "MediaPlayer initialized")
        }
    }

    fun playMusic() {
        if (isPaused) {
            mediaPlayer?.start()
            isPaused = false
            Log.d("BackgroundMusicManager", "Music resumed")
        } else {
            mediaPlayer?.start()
            Log.d("BackgroundMusicManager", "Music started")
        }
    }

    fun pauseMusic() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            isPaused = true
            Log.d("BackgroundMusicManager", "Music paused")
        }
    }

    fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        Log.d("BackgroundMusicManager", "Music stopped and released")
    }
}