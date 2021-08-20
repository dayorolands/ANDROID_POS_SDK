package com.interswitch.smartpos.emv.telpo.utils


import android.util.Log
import com.interswitch.smartpos.emv.telpo.emv.ICCData
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*



internal object EmvUtils {
    private val HEX_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

//    fun createAppList(terminalConfig: TerminalConfig, aiDs: EmvAIDs): List<EMV_APPLIST> {
//        val creator = { card: EmvCard -> card.toAPPListItem(terminalConfig) }
//        return aiDs.cards.map(creator).toList()
//    }


    /**
     * ASCII码字符串转数字字符串
     *
     * @param content
     * ASCII字符串
     * @return 字符串
     */
    @JvmStatic
    fun asciiStringToString(content: String): String {
        val result = StringBuffer()
        val length = content.length / 2
        for (i in 0 until length) {
            val c = content.substring(i * 2, i * 2 + 2)
            val a = hexStringToAlgorism(c)
            val b = a.toChar()
            val d = b.toString()
            result.append(d)
        }
        return result.toString()
    }


    /**
     * 十六进制字符串装十进制
     *
     * @param hex
     * 十六进制字符串
     * @return 十进制数值
     */
    @JvmStatic
    fun hexStringToAlgorism(hex: String): Long {
        val hexUpper = hex.toUpperCase()
        val max = hexUpper.length
        var result: Long = 0
        for (i in max downTo 1) {
            val c = hexUpper[i - 1]
            val algorism = if (c in '0'..'9') {
                c - '0'
            } else {
                c.toInt() - 55
            }
            val k = max - i
            result += (Math.pow(16.0, k.toDouble()) * java.lang.Double.valueOf(algorism.toDouble())).toLong()
        }
        return result
    }

    @JvmStatic
    fun bcd2Str(bytes: ByteArray): String {
        val temp = StringBuilder(bytes.size * 2)
        for (i in bytes.indices) {
            var left = (bytes[i].toInt() and (0xf0)).ushr(4).toByte()
            var right = (bytes[i].toInt() and 0x0f).toByte()
            if (left in 0x0a..0x0f) {
                left = (left - 0x0a).toByte()
                left = (left + 'A'.toByte()).toByte()
            } else {
                left = (left + '0'.toByte()).toByte()
            }

            if (right >= 0x0a && right <= 0x0f) {
                right = (right - 0x0a).toByte()
                right = (right + 'A'.toByte()).toByte()
            } else {
                right = (right + '0'.toByte()).toByte()
            }

            temp.append(String.format("%c", left))
            temp.append(String.format("%c", right))
        }
        return temp.toString()
    }

    @JvmStatic
    fun bcd2Str(b: ByteArray?, len: Int): String? {
        if (b == null) {
            return null
        }
        val sb = StringBuilder(len * 2)
        val minLen = Math.min(b.size, len)
        for (i in 0 until minLen) {
            sb.append(HEX_DIGITS[(b[i].toInt() and 0xF0).ushr(4)])
            sb.append(HEX_DIGITS[b[i].toInt() and 0xF])
        }

        return sb.toString()
    }

    @JvmStatic
    fun str2Bcd(asc: String): ByteArray {
        var ascCopy = asc
        var len = ascCopy.length
        val mod = len % 2
        if (mod != 0) {
            ascCopy = "0$ascCopy"
            len = ascCopy.length
        }
        if (len >= 2) {
            len /= 2
        }
        val bbt = ByteArray(len)
        val abt = ascCopy.toByteArray()

        for (p in 0 until ascCopy.length / 2) {
            val j: Int = if (abt[2 * p] in 97..122) {
                abt[2 * p] - 97 + 10
            } else {
                if (abt[2 * p] in 65..90)
                    abt[2 * p] - 65 + 10
                else
                    abt[2 * p] - 48
            }

            val k: Int = if (abt[2 * p + 1] in 97..122) {
                abt[2 * p + 1] - 97 + 10
            } else {
                if (abt[2 * p + 1] in 65..90)
                    abt[2 * p + 1] - 65 + 10
                else {
                    abt[2 * p + 1] - 48
                }
            }
            val a = (j shl 4) + k
            val b = a.toByte()
            bbt[p] = b
        }
        return bbt
    }

    @JvmStatic
    fun bytes2String(source: ByteArray): String {
        var result = ""
        try {
            if (source.isNotEmpty())
                result = String(source, Charset.defaultCharset())
        } catch (e: UnsupportedEncodingException) {
            Log.e("byte2Str", e.message)
            //e.printStackTrace();
        }

        return result
    }

    @JvmStatic
    fun int2ByteArray(i: Int): ByteArray {
        val to = ByteArray(4)
        val offset = 0
        to[offset] = (i.ushr(24) and 0xFF).toByte()
        to[offset + 1] = (i.ushr(16) and 0xFF).toByte()
        to[offset + 2] = (i.ushr(8) and 0xFF).toByte()
        to[offset + 3] = (i and 0xFF).toByte()
        for (j in to.indices) {
            if (to[j].toInt() != 0) {
                return Arrays.copyOfRange(to, j, to.size)
            }
        }
        return byteArrayOf(0x00)
    }

    @JvmStatic
    fun bytes2Long(byteNum: ByteArray): Long {
        var num: Long = 0
        for (ix in byteNum.indices) {
            num = num shl 8
            num = num or (byteNum[ix].toInt() and 0xff).toLong()
        }
        return num
    }

    @JvmStatic
    fun LongToBytes(values: Long): ByteArray {
        val buffer = ByteArray(8)
        for (i in 0..7) {
            val offset = 64 - (i + 1) * 8
            buffer[i] = (values shr offset and 0xff).toByte()
        }
        return buffer
    }

    /**
     * bytes字符串转换为Byte值
     * @param String src Byte字符串，每个Byte之间没有分隔符
     * @return byte[]
     */
    @JvmStatic
    fun hexStr2Bytes(src: String): ByteArray {
        var m: Int
        var n: Int
        val l = src.length / 2

        val ret = ByteArray(l)
        for (i in 0 until l) {
            m = i * 2 + 1
            n = m + 1
            ret[i] = java.lang.Byte.decode("0x" + src.substring(i * 2, m) + src.substring(m, n))
        }
        return ret
    }


    @JvmStatic
    fun buildIccString(tagValues: List<Pair<ICCData, ByteArray?>>): String {
        var hex = ""

        for (tagValue in tagValues) {
            tagValue.second?.apply {
                val tag = Integer.toHexString(tagValue.first.tag).toUpperCase()
                var value = bcd2Str(this)
                var length = Integer.toHexString(size)

                // truncate tag value if it exceeds max length
                if (size > tagValue.first.max) {
                    val expectedLength = 2 * tagValue.first.max // hex is double the length
                    value = value.substring(0 until expectedLength)
                    length = Integer.toHexString(tagValue.first.max)
                }

                // prepend 0 based on value length
                val lengthStr = (if (length.length > 1) length else "0$length").toUpperCase()
                hex = "$hex$tag$lengthStr$value"
            }
        }

        return hex
    }
}