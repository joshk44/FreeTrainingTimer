package com.joseferreyra.tabatimer.data

import android.content.SharedPreferences

class HistoricalDataSource (private val sharedPreferences: SharedPreferences) {

    fun store(historicalItem: HistoricalItem) {
        val stored = sharedPreferences.getStringSet("HISTORIC", emptySet())


        val newStored = stored?.toMutableSet()?.run {
            if (this.contains(historicalItem.toStoredValue())) this.remove(historicalItem.toStoredValue())
            this.add(historicalItem.toStoredValue())
            if (this.size > 10)
                this.remove(this.first())
            this
        }

        sharedPreferences.edit().putStringSet("HISTORIC", newStored).apply()
    }

    fun getList() =
            with(sharedPreferences.getStringSet("HISTORIC", emptySet())) {
                this?.map { it.toHistoricalItem() }
            } ?: emptyList()
}