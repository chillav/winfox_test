package com.example.winfox_test.password

import com.chibatching.kotpref.KotprefModel

object UserPassword : KotprefModel() {
    const val EMPTY_PASSWORD = ""
    const val EMPTY_BLOCK_TIME = 0L

    var password by stringPref(default = EMPTY_PASSWORD)
    var incorrectInputsAmount by intPref(default = 0)
    var lastBlockTime by longPref(default = EMPTY_BLOCK_TIME)
}
