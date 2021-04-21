package ru.carwash.utils

import android.widget.Spinner

fun Spinner.setSpinnerText(text: String) {
    for (i in 0 until this.adapter.count) {
        if (this.adapter.getItem(i).toString().contains(text)) {
            this.setSelection(i)
        }
    }
}