package com.sheikh.whacamole.view_model

import android.app.Application
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var timer: CountDownTimer

    val time = MutableLiveData<Int>()
    private val highScore = PreferenceManager.getDefaultSharedPreferences(application)

    fun getRecord(): Int {
        return highScore.getInt("record", 0)
    }

    fun setRecord(record: Int) {
        highScore.edit().putInt("record", record).apply()
    }

    fun startTime(durationInSecs: Int, whenTimeFinishes: () -> Unit) {
        val durationInMilliSecs = (durationInSecs * 1000).toLong()
        timer = object : CountDownTimer(durationInMilliSecs, 1000) {
            override fun onTick(p0: Long) {
                val timeLeft = (p0 / 1000).toInt()
                time.postValue(timeLeft)
            }

            override fun onFinish() {
                whenTimeFinishes()
            }
        }

        timer.start()

    }

    fun stopTimer() {
        timer.cancel()
    }

    override fun onCleared() {
        stopTimer()
        super.onCleared()
    }
}