package com.joseferreyra.tabatimer.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.joseferreyra.tabatimer.ui.OnSwipeTouchListener

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun RecyclerView.ViewHolder.toast(message: String) {
    this.itemView.context.toast(message)
}

fun ViewGroup.inflate(@LayoutRes id: Int) = LayoutInflater.from(context).inflate(id, this, false)

@SuppressLint("ClickableViewAccessibility")
fun TextView.onSwipeListener(increment: Int, placeholder: String) {

    this.setOnTouchListener(object : OnSwipeTouchListener() {
        override fun onSwipeRight() {
            super.onSwipeRight()
            this@onSwipeListener.text = (this@onSwipeListener.text?.toString()?.let {
                when (it.isNullOrBlank() || it == placeholder) {
                    true -> increment.toString()
                    else -> "${it.toInt() + increment}"
                }
            })
        }

        override fun onSwipeLeft() {
            super.onSwipeLeft()
            this@onSwipeListener.setText(this@onSwipeListener.text?.toString()?.let {
                when (it.isNullOrBlank() || it == placeholder) {
                    true -> "0"
                    else -> "${if (it.toInt() > 0) it.toInt() - increment else 0}"
                }
            })
        }
    })
}
