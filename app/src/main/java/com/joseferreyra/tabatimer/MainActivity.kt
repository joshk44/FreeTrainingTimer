package com.joseferreyra.tabatimer

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.TextViewCompat
import androidx.appcompat.app.AppCompatActivity
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

        excerciseTime?.setOnTouchListener (object: OnSwipeTouchListener() {
            override fun onSwipeRight() {
                super.onSwipeRight()
                excerciseTime.setText (excerciseTime?.text?.toString()?.let{
                    when (it.isNullOrBlank()) {
                        true -> "5"
                        else -> "${it.toInt() + 5}"
                    }
                })
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                excerciseTime.setText (excerciseTime?.text?.toString()?.let{
                    when (it.isNullOrBlank()) {
                        true -> "0"
                        else -> "${if (it.toInt()>4)  it.toInt() - 5 else 0}"
                    }
                })
            }

        })

        restTime?.setOnTouchListener (object: OnSwipeTouchListener() {
            override fun onSwipeRight() {
                super.onSwipeRight()
                restTime.setText (restTime?.text?.toString()?.let{
                    when (it.isNullOrBlank()) {
                        true -> "5"
                        else -> "${it.toInt() + 5}"
                    }
                })
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                restTime.setText (restTime?.text?.toString()?.let{
                    when (it.isNullOrBlank()) {
                        true -> "0"
                        else -> "${if (it.toInt()>4)  it.toInt() - 5 else 0}"
                    }
                })
            }

        })

        laps?.setOnTouchListener (object: OnSwipeTouchListener() {
            override fun onSwipeRight() {
                super.onSwipeRight()
                laps.setText (laps?.text?.toString()?.let{
                    when (it.isNullOrBlank()) {
                        true -> "1"
                        else -> "${it.toInt() + 1}"
                    }
                })
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                laps.setText (laps?.text?.toString()?.let{
                    when (it.isNullOrBlank()) {
                        true -> "0"
                        else -> "${if (it.toInt()>0)  it.toInt() - 1 else 0}"
                    }
                })
            }

        })
    }
}
