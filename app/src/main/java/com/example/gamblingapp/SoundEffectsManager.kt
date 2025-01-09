package com.example.gamblingapp

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

class SoundEffectsManager(private val context: Context) {
    private val soundPool: SoundPool
    private val soundMap = mutableMapOf<String, Int>()

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    fun loadSound(effectName: String, resourceId: Int) {
        val soundId = soundPool.load(context, resourceId, 1)
        soundMap[effectName] = soundId
    }

    fun playSound(effectName: String) {
        val soundId = soundMap[effectName]
        if (soundId != null) {

            BackgroundMusicManager.pauseMusic()

            soundPool.setOnLoadCompleteListener { _, _, _ ->
                soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            }

            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)


            soundPool.setOnLoadCompleteListener { _, _, _ ->
                BackgroundMusicManager.playMusic()
            }
        }
    }

    fun release() {
        soundPool.release()
    }
}
