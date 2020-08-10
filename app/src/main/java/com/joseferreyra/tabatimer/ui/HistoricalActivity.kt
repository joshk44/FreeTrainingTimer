package com.joseferreyra.tabatimer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.joseferreyra.tabatimer.R
import com.joseferreyra.tabatimer.data.HistoricalDataSource
import com.joseferreyra.tabatimer.data.HistoricalItem
import kotlinx.android.synthetic.main.activity_historical.*

class HistoricalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historical)
        rvHistorical.adapter = HistoricalAdapter(HistoricalDataSource(sharedPreferences = getSharedPreferences("HISTORICAL", Context.MODE_PRIVATE)).getList()) {redirectToTimer(it) }
        rvHistorical.layoutManager = LinearLayoutManager(this)
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