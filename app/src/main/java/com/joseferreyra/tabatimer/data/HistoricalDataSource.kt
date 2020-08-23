package com.joseferreyra.tabatimer.data

import android.content.SharedPreferences
import androidx.core.content.edit

class HistoricalDataSource (private val sharedPreferences: SharedPreferences) {

    fun store(historicalItem: HistoricalItem) {
        val stored = sharedPreferences.getStringSet("HISTORIC", emptySet())

        val newStored = stored?.toMutableSet()?.run {
            if (this.contains(historicalItem.toStoredValue())) this.remove(historicalItem.toStoredValue())
            if (this.size > 10)
                this.remove(this.first())
            this.add(historicalItem.toStoredValue())
            this
        }
        sharedPreferences.edit {
            putStringSet("HISTORIC", newStored)
        }
    }

    fun getList() =
            with(sharedPreferences.getStringSet("HISTORIC", emptySet())) {
                this?.map { it.toHistoricalItem() }
            } ?: emptyList()
}