package com.joseferreyra.tabatimer.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.DisplayMetrics
import com.joseferreyra.tabatimer.R
import kotlinx.android.synthetic.main.activity_timer_running.*

const val DATA = "data"


class TimerRunningActivity : AppCompatActivity(), ClockListener {

    var myService: TimerService? = null
    var isBound = false
    var data = intArrayOf(40,20,3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_running)
        //bindService(intent, myConnection, Context.BIND_AUTO_CREATE)

        data = intent.extras.getIntArray (DATA)
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
            myService?.startTimer(data[0], data[1], data[2])
            myService?.getStatus()?.let{updateCurrentStatus(it)}
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }

    private fun updateCurrentStatus (it: COUNTERSTATUS) {
        when (it) {
            COUNTERSTATUS.ENDED -> {
                textView?.text = "DONE"
                textView.setTextColor(ContextCompat.getColor(this, R.color.neontwo))}
        }
    }


    override fun onPause() {
        super.onPause()
        if (isBound) myService?.removeListener()
    }

    override fun onTick(seconds: Long, type: COUNTERTYPE) {

        formatSeconds(seconds, type).let {
            if (textView?.text != it)
                textView?.text = it
        }

        when (type) {
            COUNTERTYPE.EXCERSICE -> ContextCompat.getColor(this, R.color.neonone)
            COUNTERTYPE.REST -> ContextCompat.getColor(this, R.color.neonthree)
        }.let {
            if (textView.textColors.defaultColor != it)
                textView.setTextColor(it)
        }
    }

    override fun onFinish() {
        updateCurrentStatus(COUNTERSTATUS.ENDED)
    }

    private fun formatSeconds(seconds: Long, type: COUNTERTYPE): String {

        if (seconds == 0L) {
            return when (type) {
                COUNTERTYPE.EXCERSICE -> "REST"
                COUNTERTYPE.REST -> "GO"
            }
        }

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
    fun onTick (milisec: Long, type: COUNTERTYPE)
    fun onFinish ()
}
