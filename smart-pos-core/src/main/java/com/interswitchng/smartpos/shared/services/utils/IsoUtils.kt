package com.interswitchng.smartpos.shared.services.utils

import java.security.MessageDigest

object IsoUtils {

    @JvmStatic
    fun hexToBytes(hex: String): ByteArray {
        if (hex.length and 0x01 == 0x01)
            throw IllegalArgumentException()

        val bytes = ByteArray(hex.length / 2)
        for (idx in bytes.indices) {
            val hi = Character.digit(hex[idx * 2].toInt(), 16)
            val lo = Character.digit(hex[idx * 2 + 1].toInt(), 16)
            if (hi < 0 || lo < 0)
                throw IllegalArgumentException()
            bytes[idx] = (hi shl 4 or lo).toByte()
        }

        return bytes
    }

    @JvmStatic
    fun bytesToHex(bytes: ByteArray): String {

        val hex = CharArray(bytes.size * 2)
        for (idx in bytes.indices) {
            val hi = (bytes[idx].toInt() and 0xF0).ushr(4)
            val lo = bytes[idx].toInt() and 0x0F
            hex[idx * 2] = (if (hi < 10) '0'.toInt() + hi else 'A'.toInt() - 10 + hi).toChar()
            hex[idx * 2 + 1] = (if (lo < 10) '0'.toInt() + lo else 'A'.toInt() - 10 + lo).toChar()
        }
        return String(hex)
    }

    @Throws(Exception::class)
    @JvmStatic
    internal fun getMac(seed: String, macDataBytes: ByteArray): String {

        val keyBytes = hexToBytes(seed)
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(keyBytes, 0, keyBytes.size)
        digest.update(macDataBytes, 0, macDataBytes.size)
        val hashedBytes = digest.digest()
        var hashText = bytesToHex(hashedBytes)
        hashText = hashText.replace(" ", "")

        if (hashText.length < 64) {
            val numberOfZeroes = 64 - hashText.length
            var zeroes = ""
            var temp = hashText
            for (i in 0 until numberOfZeroes)
                zeroes += "0"
            temp = zeroes + temp
            return temp
        }

        return hashText
    }

    internal fun getIsoResult(code: String): Pair<String, String>? {
        return resultMap[code]?.let { Pair(code, it) }
    }

    fun getIsoResultMsg(code: String): String? {
        return getIsoResult(code)?.second
    }

    internal fun generatePan(code: String): String {
        val bin = "506179"
        var binAndCode = "$bin$code"
        val remainder = 12 - code.length
        // pad if less than 12
        if (remainder > 0)
            binAndCode = "$binAndCode${"0".repeat(remainder)}"

        var nSum = 0
        var alternate = true
        for (i in binAndCode.length - 1 downTo 0) {

            var d = binAndCode[i] - '0'

            if (alternate)
                d *= 2

            // We add two digits to handle
            // cases that make two digits after
            // doubling
            nSum += d / 10
            nSum += d % 10

            alternate = !alternate
        }

        val unitDigit = nSum % 10
        val checkDigit = 10 - unitDigit

        return "$binAndCode$checkDigit"
    }

    internal const val OK: String = "00"

    internal const val TIMEOUT_CODE = "0x0x0"

    private val resultMap = mapOf(
            "00" to "Transaction Approved",
            "01" to "Refer to card issuer",
            "02" to "Refer to card issuer, special condition",
            "03" to "Invalid merchant",
            "04" to "Pick-up card",
            "05" to "Do not honor",
            "06" to "Error",
            "07" to "Pick-up card, special condition",
            "08" to "Honor with identification",
            "09" to "Request in progress",
            "10" to "Approved, partial",
            "11" to "Approved, VIP",
            "12" to "Invalid transaction",
            "13" to "Invalid amount",
            "14" to "Invalid card number",
            "15" to "No such issuer",
            "16" to "Approved, update track 3",
            "17" to "Customer cancellation",
            "18" to "Customer dispute",
            "19" to "Re-enter transaction",
            "20" to "Invalid response",
            "21" to "No action taken",
            "22" to "Suspected malfunction",
            "23" to "Unacceptable transaction fee",
            "24" to "File update not supported",
            "25" to "Unable to locate record",
            "26" to "Duplicate record",
            "27" to "File update field edit error",
            "28" to "File update file locked",
            "29" to "File update failed",
            "30" to "Format error",
            "31" to "Bank not supported",
            "32" to "Completed partially",
            "33" to "Expired card, pick-up",
            "34" to "Suspected fraud, pick-up",
            "35" to "Contact acquirer, pick-up",
            "36" to "Restricted card, pick-up",
            "37" to "Call acquirer security, pick-up",
            "38" to "PIN tries exceeded, pick-up",
            "39" to "No credit account",
            "40" to "Function not supported",
            "41" to "Lost card, pick-up",
            "42" to "No universal account",
            "43" to "Stolen card, pick-up",
            "44" to "No investment account",
            "45" to "Account closed",
            "46" to "Identification required",
            "47" to "Identification cross-check required",
            "51" to "Not sufficient funds",
            "52" to "No check account",
            "53" to "No savings account",
            "54" to "Expired card",
            "55" to "Incorrect PIN",
            "56" to "No card record",
            "57" to "Transaction not permitted to cardholder",
            "58" to "Transaction not permitted on terminal",
            "59" to "Suspected fraud",
            "60" to "Contact acquirer",
            "61" to "Exceeds withdrawal limit",
            "62" to "Restricted card",
            "63" to "Security violation",
            "64" to "Original amount incorrect",
            "65" to "Exceeds withdrawal frequency",
            "66" to "Call acquirer security",
            "67" to "Hard capture",
            "68" to "Response received too late",
            "69" to "Advice received too late",
            "90" to "Cut-off in progress",
            "91" to "Issuer or switch inoperative",
            "92" to "Routing error",
            "93" to "Violation of law",
            "94" to "Duplicate transaction",
            "95" to "Reconcile error",
            "96" to "System malfunction",
            "98" to "Exceeds cash limit",
            "A1" to "ATC not incremented",
            "A2" to "ATC limit exceeded",
            "A3" to "ATC configuration error",
            "A4" to "CVR check failure",
            "A5" to "CVR configuration error",
            "A6" to "TVR check failure",
            "A7" to "TVR configuration error",
            "C0" to "Unacceptable PIN",
            "C1" to "PIN Change failed",
            "C2" to "PIN Unblock failed",
            "D1" to "MAC Error",
            "E1" to "Prepay error",
            TIMEOUT_CODE to "Read Timeout Error"
    )
}