package se.kruskakli.nsomobile.core.presentation

import android.util.Patterns

// check if value is number
fun isNumber(value: String): Boolean {
    return value.isEmpty() || Regex("^\\d+\$").matches(value)
}

fun isIPv4Address(value: String): Boolean {
    return value.isNotEmpty() && Regex("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$").matches(value)
}

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean {
    return password.any { it.isDigit() } &&
            password.any { it.isLetter() }
}