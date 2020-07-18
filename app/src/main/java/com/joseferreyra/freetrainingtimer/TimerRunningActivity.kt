package com.joseferreyra.freetrainingtimer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.support.v4.widget.TextViewCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_timer_running.*


class TimerRunningActivity : AppCompatActivity(), ClockListener {


    var myService: TimerService? = null
    var isBound = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_running)
        //bindService(intent, myConnection, Context.BIND_AUTO_CREATE)
        val intent = Intent(this, TimerService::class.java)
        startService(intent)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = resources.displayMetrics.density
        val dpWidth = outMetrics.widthPixels / density * 1.8
        val lenght = textView.text.length
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(textView, 10, dpWidth.toInt() / lenght, 1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        if (myService != null) myService!!.setListener(this@TimerRunningActivity)

    }

    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName,
                                        service: IBinder) {
            val binder = service as TimerService.MyLocalBinder
            myService = binder.getService()
            myService!!.setListener(this@TimerRunningActivity)
            isBound = true
            myService?.startTimer(10, 2, 2)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }


    override fun onPause() {
        super.onPause()
        if (isBound) myService?.removeListener()
    }

    override fun onTick(seconds: Long, type: COUNTERTYPE) {

        formatSeconds(seconds).let {
            if (textView?.text != it)
                textView?.text = formatSeconds(seconds)
        }

        when (type) {
            COUNTERTYPE.EXCERSICE -> ContextCompat.getColor(this, R.color.neonone)
            COUNTERTYPE.REST -> ContextCompat.getColor(this, R.color.neonthree)
        }.let {
            if (textView.textColors.defaultColor != it)
                textView.setTextColor(it)
        }
    }

    fun formatSeconds(seconds: Long): String {
        val minutes = "${seconds / 60}"
        var seconds = "${seconds % 60}".let {
            when (it.length) {
                1 -> "0$it"
                else -> it
            }
        }
        return "$minutes:$seconds"
    }
}

interface ClockListener {
    fun onTick(milisec: Long, type: COUNTERTYPE)
}
