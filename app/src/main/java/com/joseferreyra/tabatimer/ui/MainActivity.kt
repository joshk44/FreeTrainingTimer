package com.joseferreyra.tabatimer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joseferreyra.tabatimer.R
import com.joseferreyra.tabatimer.databinding.ActivityMainBinding
import com.joseferreyra.tabatimer.extensions.onSwipeListener

const val SHAREPREF = "TABATIMER"
const val ONBOARDINGSHOWN = "onboarding"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showOnBoarding()

        binding.goButton.setOnClickListener {
            if (!binding.excerciseTime.text.isNullOrEmpty() && binding.excerciseTime.text != getString(R.string.active) && binding.excerciseTime?.text != "0" &&
                    !binding.restTime.text.isNullOrEmpty() && binding.restTime.text != getString(R.string.rest) && binding.restTime.text != "0" &&
                    !binding.laps.text.isNullOrEmpty() && binding.laps.text != getString(R.string.laps) && binding.laps.text != "0" ) {
                val intent = Intent(this@MainActivity, TimerRunningActivity::class.java)
                intent.putExtra(DATA, intArrayOf(
                        binding.excerciseTime.text.toString().toInt(),
                        binding.restTime.text.toString().toInt(),
                        binding.laps.text.toString().toInt()))
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.info), Toast.LENGTH_LONG).show()
            }
        }

        binding.excerciseTime.onSwipeListener(5, getString(R.string.active))
        binding.restTime.onSwipeListener(5, getString(R.string.rest))
        binding.laps.onSwipeListener(1, getString(R.string.laps))

        binding.historicalFloating.setOnClickListener {
            startActivity(Intent (this, HistoricalActivity::class.java))
        }
    }

    private fun showOnBoarding() {
        val sharedPreferences = getSharedPreferences(SHAREPREF, Context.MODE_PRIVATE)
        if (!sharedPreferences.getBoolean(ONBOARDINGSHOWN, false)) {
            binding.onboardinng.onboarding.visibility = View.VISIBLE
            binding.onboardinng.gotit.setOnClickListener {
                binding.onboardinng.onboarding.visibility = View.GONE
                sharedPreferences.edit().putBoolean(ONBOARDINGSHOWN, true).apply()
            }
        }
    }
}
