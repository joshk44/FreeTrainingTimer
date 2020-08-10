package com.joseferreyra.tabatimer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoricalItem (val active: Int, val rest: Int, val laps: Int) : Parcelable

fun HistoricalItem.toStoredValue () = "$active,$rest,$laps"

fun String.toHistoricalItem () =
    this.split(",")?.map { it.toInt() }.let {
        HistoricalItem(it[0], it[1], it[2])
    }