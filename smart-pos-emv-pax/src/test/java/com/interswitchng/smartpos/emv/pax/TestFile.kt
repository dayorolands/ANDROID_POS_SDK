package com.interswitchng.smartpos.emv.pax

import com.interswitchng.smartpos.emv.pax.old.utilities.EmvUtils
import com.interswitchng.smartpos.emv.pax.old.utilities.StringUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class TestFile {

    @Test
    fun showTest() {

        val m = "A0000000041010"
        val sub = m.substring(0 until m.length-1)
        assertEquals(sub, "A000000004101")
    }


    @Test
    fun hexToByte() {

        val string = "Some partition string"
        val bytes = string.toByteArray()
        println("byte length - ${bytes.size}")

        val hex = EmvUtils.bcd2Str(bytes)
        print("$hex ")
        println("hex length - ${hex.length}")
    }


    @Test
    fun format() {
        assertEquals(StringUtils.center(null, 0), null)
        assertEquals(StringUtils.center("foo", 3), ("foo"))
        assertEquals(StringUtils.center("foo", -1), ("foo"))
        assertEquals(StringUtils.center("moon", 10), ("   moon   "))
    }
}