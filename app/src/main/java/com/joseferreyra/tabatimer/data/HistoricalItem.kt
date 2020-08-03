package com.joseferreyra.tabatimer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoricalItem (val active: Int, val rest: Int, val laps: Int) : Parcelable