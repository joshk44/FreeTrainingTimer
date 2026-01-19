package com.joseferreyra.tabatimer.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.joseferreyra.tabatimer.R
import com.joseferreyra.tabatimer.databinding.ActivityTimerRunningBinding

const val DATA = "data"

class TimerRunningActivity : AppCompatActivity(), ClockListener {

    var myService: TimerService? = null
    var isBound = false
    var data = intArrayOf(40, 20, 3)

    private lateinit var binding: ActivityTimerRunningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerRunningBinding.inflate(layoutInflater)
        val view = binding.root
        intent.extras?.getIntArray(DATA)?.let { data = it }
        val intent = Intent(this, TimerService::class.java)
        startService(intent)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)
        setContentView(view)

    }

    override fun onResume() {
        super.onResume()
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = resources.displayMetrics.density
        val dpWidth = outMetrics.widthPixels / density * 1.8
        val lenght = binding.textView.text.length
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(binding.textView, 10, dpWidth.toInt() / lenght, 1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        if (myService != null) myService!!.setListener(this@TimerRunningActivity)

    }

    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TimerService.MyLocalBinder
            myService = binder.getService()
            myService!!.setListener(this@TimerRunningActivity)
            isBound = true

            if (myService?.getStatus() != CounterStatus.RUNNING) {
                myService?.startTimer(data[0], data[1], data[2])
            }
            myService?.getStatus()?.let { updateCurrentStatus(it) }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }

    private fun updateCurrentStatus(it: CounterStatus) {
        when (it) {
            CounterStatus.ENDED -> {
                binding.textView?.text = "DONE"
                binding.textView.setTextColor(ContextCompat.getColor(this, R.color.neontwo))
            }
            CounterStatus.RUNNING -> Log.d("Running", "Running")
        }
    }


    override fun onPause() {
        super.onPause()
        if (isBound) myService?.removeListener()
    }

    override fun onTick(seconds: Long, type: CounterType) {

        formatSeconds(seconds, type).let {
            if (binding.textView?.text != it)
                binding.textView?.text = it
        }

        when (type) {
            CounterType.EXERCISE -> ContextCompat.getColor(this, R.color.neonone)
            CounterType.REST -> ContextCompat.getColor(this, R.color.neonthree)
        }.let {
            if (binding.textView.textColors.defaultColor != it)
                binding.textView.setTextColor(it)
        }
    }

    override fun onFinish() {
        updateCurrentStatus(CounterStatus.ENDED)
    }

    private fun formatSeconds(seconds: Long, type: CounterType): String {

        if (seconds == 0L) {
            return when (type) {
                CounterType.EXERCISE -> "REST"
                CounterType.REST -> "GO"
            }
        }

        val minutes = "${seconds / 60}"
        var secondsStr = "${seconds % 60}".let {
            when (it.length) {
                1 -> "0$it"
                else -> it
            }
        }
        return "$minutes:$secondsStr"
    }
}

interface ClockListener {
    fun onTick(milisec: Long, type: CounterType)
    fun onFinish()
}
