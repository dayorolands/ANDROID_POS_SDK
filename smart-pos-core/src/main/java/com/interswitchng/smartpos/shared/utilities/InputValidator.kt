package com.interswitchng.smartpos.shared.utilities


class InputValidator(private val value: String) {

    var hasError: Boolean = false
    var message: String = ""


    fun isNotEmpty(): InputValidator {
        if (hasError) return this

        if (value.isEmpty()) {
            hasError = true
            message = EMPTY_MESSAGE
        }

        return this
    }

    fun hasMinLength(min: Int): InputValidator {
        if (hasError) return this

        if (value.length < min) {
            hasError = true
            message = "$MIN_LENGTH_MESSAGE $min"
        }

        return this
    }

    fun hasMaxLength(max: Int): InputValidator {
        if (hasError) return this

        if (value.length > max) {
            hasError = true
            message = "$MAX_LENGTH_MESSAGE $max"
        }

        return this
    }

    fun isAlphaNumeric(): InputValidator {
        if (hasError) return this

        if (!value.matches(alphaNumeric)) {
            hasError = true
            message = ALPHA_NUMERIC_MESSAGE
        }

        return this
    }

    fun isNumber(): InputValidator {
        if (hasError) return this

        if (value.toLongOrNull() == null) {
            hasError = true
            message = NUMBER_MESSAGE
        }

        return this
    }

    fun isNumberBetween(min: Long, max: Long): InputValidator {
        if (hasError) return this

        val valueAsNumber = value.toLongOrNull()
        val isBetweenRange = valueAsNumber != null && valueAsNumber in min..max

        if (!isBetweenRange) {
            hasError = true
            message = "$RANGE_MESSAGE $min and $max"
        }

        return this
    }

    fun isExactLength(length: Int): InputValidator {
        if (hasError) return this

        if (value.length != length) {
            hasError = true
            message = "$EXACT_LENGTH_MESSAGE $length characters"
        }

        return this
    }


    fun isValidIp(): InputValidator {
        if (hasError) return this

        if (!value.matches(validIp)) {
            hasError = true
            message = IP_MESSAGE
        }

        return this
    }

    companion object {
        private val alphaNumeric = Regex("[A-Za-z0-9]*")
        private val validIp = Regex("""\A(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\z""")
        private const val EMPTY_MESSAGE = "cannot be empty"
        private const val MAX_LENGTH_MESSAGE = "cannot be more than"
        private const val MIN_LENGTH_MESSAGE = "cannot be less than"
        private const val EXACT_LENGTH_MESSAGE = "must be exactly"
        private const val RANGE_MESSAGE = "must be between"
        private const val ALPHA_NUMERIC_MESSAGE = "can only contain alpha numeric values"
        private const val NUMBER_MESSAGE = "can only contain numeric values"
        private const val IP_MESSAGE = "invalid ip address"
    }
}