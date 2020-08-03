package com.joseferreyra.tabatimer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joseferreyra.tabatimer.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.onboarding.*

const val SHAREPREF = "TABATIMER"
const val ONBOARDINGSHOWN = "onboarding"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showOnBoarding()

        goButton.setOnClickListener {
            if (!excerciseTime?.text.isNullOrEmpty() && excerciseTime?.text != getString(R.string.active) &&
                    !restTime?.text.isNullOrEmpty() && restTime?.text != getString(R.string.rest) &&
                    !laps?.text.isNullOrEmpty() && laps?.text != getString(R.string.laps) ) {
                val intent = Intent(this@MainActivity, TimerRunningActivity::class.java)
                intent.putExtra(DATA, intArrayOf(
                        excerciseTime?.text.toString().toInt(),
                        restTime?.text.toString().toInt(),
                        laps?.text.toString().toInt()))
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.info), Toast.LENGTH_LONG).show()
            }
        }

        excerciseTime?.setOnTouchListener(object : OnSwipeTouchListener() {
            override fun onSwipeRight() {
                super.onSwipeRight()
                excerciseTime.setText(excerciseTime?.text?.toString()?.let {
                    when (it.isNullOrBlank() || it == getString(R.string.active)) {
                        true -> "5"
                        else -> "${it.toInt() + 5}"
                    }
                })
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                excerciseTime.setText(excerciseTime?.text?.toString()?.let {
                    when (it.isNullOrBlank() || it == getString(R.string.active)) {
                        true -> "0"
                        else -> "${if (it.toInt() > 4) it.toInt() - 5 else 0}"
                    }
                })
            }

        })

        restTime?.setOnTouchListener(object : OnSwipeTouchListener() {
            override fun onSwipeRight() {
                super.onSwipeRight()
                restTime.setText(restTime?.text?.toString()?.let {
                    when (it.isNullOrBlank() || it == getString(R.string.rest)) {
                        true -> "5"
                        else -> "${it.toInt() + 5}"
                    }
                })
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                restTime.setText(restTime?.text?.toString()?.let {
                    when (it.isNullOrBlank() || it == getString(R.string.rest)) {
                        true -> "5"
                        else -> "${if (it.toInt() > 4) it.toInt() - 5 else 0}"
                    }
                })
            }

        })

        laps?.setOnTouchListener(object : OnSwipeTouchListener() {
            override fun onSwipeRight() {
                super.onSwipeRight()
                laps.setText(laps?.text?.toString()?.let {
                    when (it.isNullOrBlank() || it == getString(R.string.laps)) {
                        true -> "1"
                        else -> "${it.toInt() + 1}"
                    }
                })
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                laps.setText(laps?.text?.toString()?.let {
                    when (it.isNullOrBlank() || it == getString(R.string.laps)) {
                        true -> "0"
                        else -> "${if (it.toInt() > 0) it.toInt() - 1 else 0}"
                    }
                })
            }

        })

        historicalFloating.setOnClickListener {
            startActivity(Intent (this, HistoricalActivity::class.java))
        }
    }

    private fun showOnBoarding() {
        val sharedPreferences = getSharedPreferences(SHAREPREF, Context.MODE_PRIVATE)

        if (!sharedPreferences.getBoolean(ONBOARDINGSHOWN, false)) {
            onboardinng.visibility = View.VISIBLE
            gotit.setOnClickListener {
                onboardinng.visibility = View.GONE
                sharedPreferences.edit().putBoolean(ONBOARDINGSHOWN, true).apply()
            }
        }
    }
}
