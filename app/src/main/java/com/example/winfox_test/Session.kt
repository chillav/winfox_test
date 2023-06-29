package com.example.winfox_test

import com.chibatching.kotpref.KotprefModel

object Session : KotprefModel() {
    const val EMPTY_SESSION_TIME = 0L
    var lastSessionTime by longPref(default = EMPTY_SESSION_TIME)
}
