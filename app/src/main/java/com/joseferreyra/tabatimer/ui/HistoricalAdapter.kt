package com.joseferreyra.tabatimer.ui

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joseferreyra.tabatimer.R
import com.joseferreyra.tabatimer.data.HistoricalItem
import com.joseferreyra.tabatimer.extensions.inflate

class HistoricalAdapter(private val items: List<HistoricalItem>) : RecyclerView.Adapter<HistoricalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.historical_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val active = itemView.findViewById<TextView>(R.id.active)
        val rest = itemView.findViewById<TextView>(R.id.rest)
        val laps = itemView.findViewById<TextView>(R.id.laps)

        fun bind(historicalItem: HistoricalItem) {
            active?.text = historicalItem.active.toString()
            rest?.text = historicalItem.rest.toString()
            laps?.text = historicalItem.laps.toString()
        }
    }
}