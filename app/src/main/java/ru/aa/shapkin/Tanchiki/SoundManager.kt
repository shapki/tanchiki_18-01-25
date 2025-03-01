package ru.aa.shapkin.Tanchiki

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer

@SuppressLint("StaticFieldLeak")
class SoundManager(context: Context) {
    private lateinit var bulletBurstPlayer: MediaPlayer
    private lateinit var bulletShotPlayer: MediaPlayer
    private lateinit var introMusicPlayer: MediaPlayer
    private lateinit var tankMovePlayerFirst: MediaPlayer
    private lateinit var tankMovePlayerSecond: MediaPlayer
    private var isIntroFinished = false

    init {
        prepareGapLessTankMoveSound()
    }

    private fun prepareGapLessTankMoveSound() {
        tankMovePlayerFirst.isLooping = true
        tankMovePlayerSecond.isLooping = true
        tankMovePlayerFirst.setNextMediaPlayer(tankMovePlayerSecond)
        tankMovePlayerSecond.setNextMediaPlayer(tankMovePlayerFirst)
    }

    fun playIntroMusic() {
        if (isIntroFinished) {
            return
        }
        introMusicPlayer.setOnCompletionListener {
            isIntroFinished = true
        }
        introMusicPlayer.start()
    }

    fun bulletShot() {
        bulletShotPlayer.start()
    }

    fun bulletBurst() {
        bulletBurstPlayer.start()
    }

    fun tankMove() {
        tankMovePlayerFirst.start()
    }

    fun tankStop() {
        if (tankMovePlayerFirst.isPlaying) {
            tankMovePlayerFirst.pause()
        }
        if (tankMovePlayerSecond.isPlaying) {
            tankMovePlayerSecond.pause()
        }
    }

    fun pauseSounds() {
        bulletBurstPlayer.pause()
        bulletShotPlayer.pause()
        introMusicPlayer.pause()
        tankMovePlayerFirst.pause()
        tankMovePlayerSecond.pause()
    }
}