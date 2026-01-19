package com.joseferreyra.tabatimer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.joseferreyra.tabatimer.data.HistoricalDataSource
import com.joseferreyra.tabatimer.data.HistoricalItem
import com.joseferreyra.tabatimer.databinding.ActivityHistoricalBinding

class HistoricalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoricalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoricalBinding.inflate(layoutInflater)
        val view = binding.root
        binding.rvHistorical.adapter = HistoricalAdapter(
            HistoricalDataSource(sharedPreferences = getSharedPreferences("HISTORICAL", Context.MODE_PRIVATE)).getList()) {redirectToTimer(it) }
        binding.rvHistorical.layoutManager = LinearLayoutManager(this)
        setContentView(view)
    }

    private fun redirectToTimer (historicalItem: HistoricalItem) {
        startActivity (Intent(this, TimerRunningActivity::class.java).apply {
            this.putExtra(DATA, intArrayOf(
                    historicalItem.active,
                    historicalItem.rest,
                    historicalItem.laps))
        } )
    }
}