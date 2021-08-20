package com.interswitchng.smartpos.services

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.interswitchng.smartpos.BuildConfig
import com.interswitchng.smartpos.old_shared.Constants
import com.interswitchng.smartpos.old_shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.old_shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.old_shared.services.IsoServiceImpl
import com.interswitchng.smartpos.old_shared.services.tcp.IsoSocketImpl
import com.interswitchng.smartpos.old_shared.services.utils.NibssIsoMessage
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.solab.parse.ConfigParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IsoServiceImplTest {

    private fun getBinAndCode(code: String): String {

        val bin = "506179"
        var binAndCode = "$bin$code"
        val remainder = 12 - code.length
        // pad if less than 12
        if (remainder > 0)
            binAndCode = "$binAndCode${"0".repeat(remainder)}"

        return binAndCode
    }

    private fun getPan(code: String): String {

        var nSum = 0
        var alternate = true
        for (i in code.length - 1 downTo 0) {

            var d = code[i] - '0'

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

        return "$code$checkDigit"
    }

    @Test
    fun should_return_correct_card_check_digits() {
        val code = getBinAndCode("131607374")
        val expectedPan = "5061791316073740003"

        val actualPan = getPan(code)
        assertEquals(expectedPan, actualPan)

        val code2 = "422584110620516"
        val expectedPan2 = "${code2}2"
        val actualPan2 = getPan(code2)


        assertTrue(check(expectedPan2))

        assertEquals(expectedPan2, actualPan2)

    }

    fun check(ccNumber: String): Boolean {
        var sum = 0
        var alternate = false
        for (i in ccNumber.length - 1 downTo 0) {
            var n = Integer.parseInt(ccNumber.substring(i, i + 1))
            if (alternate) {
                n *= 2
                if (n > 9) {
                    n = n % 10 + 1
                }
            }
            sum += n
            alternate = !alternate
        }
        return sum % 10 == 0
    }


    @Test
    fun reverseTxn() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val messageFactory by lazy {
            val data = FileUtils.getFromAssets(context)
            val string = String(data!!)
            val stringReader = StringReader(string)
            val messageFactory = ConfigParser.createFromReader(stringReader)
            messageFactory.isUseBinaryBitmap = false //NIBSS usebinarybitmap = false
            messageFactory.characterEncoding = "UTF-8"

            return@lazy messageFactory
        }


        val message = NibssIsoMessage(messageFactory.newMessage(0x200))
            .setValue(2, "5399832646020358")
            .setValue(3, "001000")
            .setValue(4, "000000000001")
            .setValue(7, "0908120611")
            .setValue(11, "000087")
            .setValue(12, "120611")
            .setValue(13, "0908")
            .setValue(14, "2203")
            .setValue(18, "9399")
            .setValue(22, "051")
            .setValue(23, "003")
            .setValue(25, "00")
            .setValue(26, "06")
            .setValue(28, "C00000000")
            .setValue(35, "5399832646020358D22032210030999960")
            .setValue(37, "000000000087")
            .setValue(40, "221")
            .setValue(41, "2076AK21")
            .setValue(42, "07616100000AK20")
            .setValue(43, "LASG APAPA GENERAL H   LA           LANG")
            .setValue(49, "566")
            .setValue(55, "9F26087877F614CC893F3A9F2701809F10120110A50003020000000000000000000000FF9F37040ECE078B9F36020143950504800008009A031909089C01009F02060000000000015F2A020566820239009F1A0205669F34034103029F3303E0F8C89F3501229F1E0831333530303030358407A00000000410109F090200205F340103")
            .setValue(123, "511101511344101")
            .setValue(128, "E84524AD7507B1887EDF84E771890DAF37DE496A38301974FC2A99B7626F6DB4")


        message.message.removeFields(32, 52, 59)

        val posDevice = mock<POSDevice>()
        val socket = IsoSocketImpl("196.6.103.73", BuildConfig.ISW_TERMINAL_PORT, 60 * 120)
        val store = mock<KeyValueStore> {
            whenever(mock.getString(Constants.KEY_SESSION_KEY, "")) doReturn "76FEF145AE2F495434C146577C439D51"
            whenever(mock.getNumber("stan", 0)) doReturn 88
        }

        // setup koin module
        val module = module {
            single { store }
        }

        StandAloneContext.loadKoinModules(module)

        val service = IsoServiceImpl(context, store, socket)
        val result = service.reversePurchase(message)

        print(result)
    }
}