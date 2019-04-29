package com.joseferreyra.freetrainingtimer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule


class TimerService: Service(){

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Timer().schedule(1000, 1000) {
            Toast.makeText(this@TimerService, "Timer!!!", Toast.LENGTH_SHORT).show()

        }
    }

//    fun Timer.schedule(
//            delay: Long,
//            period: Long,
//            crossinline action: TimerTask.() -> Unit
//    ): TimerTask

}