package com.joseferreyra.tabatimer.ui

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joseferreyra.tabatimer.R
import com.joseferreyra.tabatimer.data.HistoricalItem
import com.joseferreyra.tabatimer.databinding.HistoricalItemBinding
import com.joseferreyra.tabatimer.extensions.inflate

class HistoricalAdapter(private val items: List<HistoricalItem>, private val listener: (item: HistoricalItem) -> Unit) : RecyclerView.Adapter<HistoricalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.historical_item), listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View, private val listener: (item: HistoricalItem) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val binding = HistoricalItemBinding.bind(itemView)

        fun bind(historicalItem: HistoricalItem) {
            binding.active?.text = historicalItem.active.toString()
            binding.rest?.text = historicalItem.rest.toString()
            binding.laps?.text = historicalItem.laps.toString()
            binding.root.setOnClickListener {
                listener(historicalItem)
            }
        }
    }
}