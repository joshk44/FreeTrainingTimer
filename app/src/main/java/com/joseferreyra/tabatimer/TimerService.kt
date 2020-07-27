package com.joseferreyra.tabatimer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log


class TimerService : Service() {

    var currentStep = 0
    var laps = 0
    var exercise = 0
    var rest = 0
    var totalSteps = 0
    var cacheSeconds = 0L

    private val myBinder = MyLocalBinder()

    private var listener: ClockListener? = null

    private var timer: CountDownTimer? = null

    private var counterStatus: COUNTERSTATUS? = null

    private var mediaPlayerBump: MediaPlayer? = null
    private var mediaPlayerBim: MediaPlayer? = null
    private var mediaPlayerFinish: MediaPlayer? = null


    override fun onCreate() {
        super.onCreate()
        mediaPlayerBim = MediaPlayer.create(this, R.raw.bim)
        mediaPlayerBump = MediaPlayer.create(this, R.raw.bump)
        mediaPlayerFinish= MediaPlayer.create(this, R.raw.fin)
    }

    override fun onBind(intent: Intent): IBinder? {
        return myBinder
    }

    inner class MyLocalBinder : Binder() {
        fun getService(): TimerService {
            return this@TimerService
        }
    }

    fun startTimer(exercise: Int, rest: Int, laps: Int) {
        this.exercise = exercise
        this.rest = rest
        this.laps = laps
        this.totalSteps = if (rest > 0) 2 * laps - 1 else laps
        scheduleTimer()
    }

    private fun scheduleTimer() {
        setupTimer(exercise, COUNTERTYPE.EXCERSICE)
    }

    private fun setupTimer(seconds: Int, type: COUNTERTYPE) {
        if (timer == null)
            timer = object : CountDownTimer(seconds.times(1000).toLong(), 100) {

                override fun onFinish() {
                    onTimerFinish(type)
                }

                override fun onTick(millisUntilFinished: Long) {
                    millisUntilFinished.div(1000).let {
                        if (cacheSeconds != it) {
                            cacheSeconds = it
                            Log.d("Timer", "tick $it $type")
                            when (cacheSeconds) {
                                1L, 2L -> {
                                    mediaPlayerBump?.seekTo(0)
                                    mediaPlayerBump?.start()
                                }
                                0L -> {
                                    mediaPlayerBim?.seekTo(0)
                                    mediaPlayerBim?.start()
                                }
                            }
                            listener?.onTick(it, type)
                            Log.d("Timer", "tick $it $type")
                        }
                    }
                }
            }.start()
        counterStatus = COUNTERSTATUS.RUNNING
    }

    private fun onTimerFinish(type: COUNTERTYPE) {
        currentStep++
        timer = null
        if (currentStep < totalSteps) {
            Log.d("Timer", "finish lap $type")
            when (type) {
                COUNTERTYPE.EXCERSICE -> {
                    when (rest) {
                        0 -> setupTimer(exercise, COUNTERTYPE.EXCERSICE)
                        else -> setupTimer(rest, COUNTERTYPE.REST)
                    }
                }
                else -> {
                    setupTimer(exercise, COUNTERTYPE.EXCERSICE)
                }
            }
        } else {
            Log.d("Timer", "finish total")
            mediaPlayerFinish?.start()
            counterStatus = COUNTERSTATUS.ENDED
            listener?.onFinish()
            currentStep = 0
        }
    }

    fun setListener(listener: ClockListener) {
        this.listener = listener
    }

    fun removeListener() {
        this.listener = null
    }

    fun getStatus() = counterStatus
}

enum class COUNTERTYPE {
    EXCERSICE, REST
}

enum class COUNTERSTATUS {
    RUNNING, ENDED
}