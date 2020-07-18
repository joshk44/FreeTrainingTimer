package com.joseferreyra.freetrainingtimer

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.TextViewCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(excerciseTime, 10, 400, 1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(restTime, 10, 400, 1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(laps, 10, 400, 1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(goButton, 10, 400, 1, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)

        goButton.setOnClickListener {
            val intent = Intent(this@MainActivity, TimerRunningActivity::class.java)
            startActivity(intent)
        }
    }
}
