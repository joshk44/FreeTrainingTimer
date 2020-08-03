package com.joseferreyra.tabatimer.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

fun Context.toast (message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun RecyclerView.ViewHolder.toast (message: String) {
    this.itemView.context.toast(message)
}

fun ViewGroup.inflate (@LayoutRes id: Int) = LayoutInflater.from(context).inflate(id, this, false)

