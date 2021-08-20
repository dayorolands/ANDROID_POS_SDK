package com.interswitchng.smartpos.emv.pax.utilities


internal object StringUtils {

    @JvmOverloads
    fun center(s: String?, size: Int, pad: Char = ' ', newLine: Boolean = false): String? {
        if (s == null || size <= s.length)
            return s

        val sb = StringBuilder(size)
        for (i in 0 until (size - s.length) / 2) {
            sb.append(pad)
        }
        sb.append(s)
        while (sb.length < size) {
            sb.append(pad)
        }

        if (newLine) sb.append("\n")
        return sb.toString()
    }
}