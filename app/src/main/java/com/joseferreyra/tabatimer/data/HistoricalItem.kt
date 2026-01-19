package com.joseferreyra.tabatimer.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoricalItem (val active: Int, val rest: Int, val laps: Int) : Parcelable

fun HistoricalItem.toStoredValue () = "$active,$rest,$laps"

fun String.toHistoricalItem () =
    this.split(",")?.map { it.toInt() }.let {
        HistoricalItem(it?.get(0) ?: 0 , it?.get(1) ?: 0,it?.get(2) ?: 0)
    }