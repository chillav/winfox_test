package com.example.winfox_test.password

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winfox_test.R
import com.example.winfox_test.helpers.ToastHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PasswordViewModel(
    private val toastHelper: ToastHelper,
) : ViewModel() {

    private val _passwordMode = MutableStateFlow<PasswordMode?>(null)
    private val _event = MutableSharedFlow<Event>()

    val passwordState = MutableStateFlow("")
    val passwordMode = _passwordMode.asStateFlow()
    val event = _event.asSharedFlow()

    init {
        _passwordMode.value = if (UserPassword.password == UserPassword.EMPTY_PASSWORD) {
            PasswordMode.FirstSet
        } else {
            PasswordMode.RequirePass
        }
    }

    fun onSubmitButtonClicked() {
        when(validatePassword()) {
            is ValidationResult.Failure -> onFailurePassword()
            is ValidationResult.Success -> onSuccessPassword()
        }
    }

    private fun onSuccessPassword() {
        if (_passwordMode.value == PasswordMode.FirstSet) {
            UserPassword.password = passwordState.value
        }
        UserPassword.incorrectInputsAmount = 0

        viewModelScope.launch {
            _event.emit(Event.SuccessAuth)
        }
    }

    private fun onFailurePassword() {
        when {
            _passwordMode.value == PasswordMode.FirstSet -> {
                toastHelper.showToast(textRes = R.string.incorrect_password)
            }
            UserPassword.incorrectInputsAmount < MAX_INCORRECT_INPUTS -> {
                UserPassword.incorrectInputsAmount = UserPassword.incorrectInputsAmount.inc()
                toastHelper.showToast(textRes = R.string.incorrect_password)
            }
            else -> {
                UserPassword.lastBlockTime = System.currentTimeMillis()
                toastHelper.showToast(textRes = R.string.incorrect_password_block)
            }
        }
    }

    private fun validatePassword(): ValidationResult {
        val hasBlock = UserPassword.lastBlockTime != UserPassword.EMPTY_BLOCK_TIME
        val needTimeout = System.currentTimeMillis() - UserPassword.lastBlockTime < PASSWORD_TIMEOUT_IN_MILLIS
        if (hasBlock) {
            if (needTimeout) {
                return ValidationResult.Failure(reason = ValidationResult.FailureReason.Block)
            } else {
                UserPassword.lastBlockTime = UserPassword.EMPTY_BLOCK_TIME
                UserPassword.incorrectInputsAmount = 0
            }
        }

        val pass = passwordState.value
        val isCorrectLength = pass.length == PASSWORD_LENGTH
        val isCorrectPassword = if (_passwordMode.value == PasswordMode.RequirePass) {
            pass == UserPassword.password
        } else true

        return if (isCorrectLength && isCorrectPassword) {
            ValidationResult.Success
        } else {
            ValidationResult.Failure(reason = ValidationResult.FailureReason.Wrong)
        }
    }

    enum class PasswordMode(@StringRes val titleRes: Int) {
        FirstSet(titleRes = R.string.create_your_password_title),
        RequirePass(titleRes = R.string.submit_your_password_title),
    }

    sealed class ValidationResult {
        object Success : ValidationResult()
        data class Failure(val reason: FailureReason) : ValidationResult()

        enum class FailureReason {
            Wrong, Block
        }
    }

    sealed class Event {
        object SuccessAuth : Event()
    }

    companion object {
        private const val PASSWORD_LENGTH = 4
        private const val MAX_INCORRECT_INPUTS = 3
        private const val PASSWORD_TIMEOUT_IN_MILLIS = 30_000L // 30 sec
    }
}
