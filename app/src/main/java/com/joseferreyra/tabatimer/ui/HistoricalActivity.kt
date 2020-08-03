package com.joseferreyra.tabatimer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.joseferreyra.tabatimer.R
import com.joseferreyra.tabatimer.data.HistoricalDataSource
import kotlinx.android.synthetic.main.activity_historical.*

class HistoricalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historical)
        rvHistorical.adapter = HistoricalAdapter(HistoricalDataSource().historicalItems)
        rvHistorical.layoutManager = LinearLayoutManager(this)
    }
}