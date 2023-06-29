package com.example.winfox_test.helpers

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

class ToastHelper(private val context: Context) {

    fun showToast(@StringRes textRes: Int, duration: Duration = Duration.Short) {
        Toast.makeText(context, textRes, duration.value).show()
    }

    enum class Duration(val value: Int) {
        Short(value = Toast.LENGTH_SHORT),
        Long(value = Toast.LENGTH_LONG)
    }
}
