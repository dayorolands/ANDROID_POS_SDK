package com.interswitchng.smartpos

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import androidx.test.runner.AndroidJUnit4
import com.interswitchng.smartpos.old_shared.Constants
import com.interswitchng.smartpos.old_shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.old_shared.services.IsoServiceImpl
import com.interswitchng.smartpos.old_shared.services.tcp.IsoSocketImpl
import com.interswitchng.smartpos.old_shared.services.utils.IsoUtils
import com.interswitchng.smartpos.old_shared.services.utils.NibssIsoMessage
import com.interswitchng.smartpos.old_shared.services.storage.KeyValueStoreImpl
import com.interswitchng.smartpos.old_shared.services.storage.SharePreferenceManager
import com.solab.parse.ConfigParser
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

@RunWith(AndroidJUnit4::class)
class IsoServiceTest {

    val context = InstrumentationRegistry.getContext()

    @Test
    fun sendTerminalRequest() {
        val prefMnager = SharePreferenceManager(context)
        val store = KeyValueStoreImpl(prefMnager)

        val modules = listOf(module(override = true) {
            single { prefMnager }
            single<KeyValueStore> { store }
        })

        loadKoinModules(modules)

        val ip = context.resources.getString(R.string.isw_nibss_ip)
        val port = context.resources.getInteger(R.integer.iswNibssPort)
        val socket = IsoSocketImpl(ip, port, 6000)
        val isoService = IsoServiceImpl(context, store, socket)

        print(ip + " : ")
        println(port)

        val terminalId = "2076AK21" //"20390007"
        val result = isoService.downloadKey(terminalId)

        val paramResult = isoService.downloadTerminalParameters(terminalId, ip, port)

        assertTrue(result)
        assertTrue(paramResult)

        // make paycode purchase
        val messageFactory by lazy {
            try {

                val data = FileUtils.getFromAssets(context)
                val string = String(data!!)
                val stringReader = StringReader(string)
                val messageFactory = ConfigParser.createFromReader(stringReader)
                messageFactory.isUseBinaryBitmap = false //NIBSS usebinarybitmap = false
                messageFactory.characterEncoding = "UTF-8"

                return@lazy messageFactory

            } catch (e: Exception) {
                println(e.localizedMessage)
                e.printStackTrace()
                throw e
            }
        }



        val message = NibssIsoMessage(messageFactory.newMessage(0x200))
        message
                .setValue(2, "5061791316073740003")
                .setValue(3, "001000")
                .setValue(4, "000000000100")
                .setValue(7, "0301191339")
                .setValue(11, "001009")
                .setValue(12, "191339")
                .setValue(13, "0301")
                .setValue(14, "2003")
                .setValue(18, "9399")
                .setValue(22, "051")
                .setValue(23, "000")
                .setValue(25, "00")
                .setValue(26, "06")
                .setValue(28, "C00000000")
                .setValue(35, "5061791316073740003D2003")
                .setValue(37, "000000001009")
                .setValue(40, "501")
                .setValue(41, "2076AK21")
                .setValue(42, "07616100000AK20")
                .setValue(49, "566")
                .setValue(55, "9F260831BDCBC7CFF6253B9F2701809F10120110A50003020000000000000000000000FF9F3704F435D8A29F36020527950508800000009A031903019C01009F02060000000001005F2A020566820238009F1A0205669F34034103029F3303E0F8C89F3501229F0306000000000000")
                .setValue(59, "00") //""90")
                .setValue(123, "510101561344101")

        message.message.removeFields( 32, 43,  52)



        // set message hash
        val bytes = message.message.writeData()
        val length = bytes.size
        val temp = ByteArray(length - 64)
        if (length >= 64) {
            System.arraycopy(bytes, 0, temp, 0, length - 64)
        }

        val sessionKey = store.getString(Constants.KEY_SESSION_KEY, "")
        val hashValue = IsoUtils.getMac(sessionKey, temp) //SHA256
        message.setValue(128, hashValue)
        message.dump(System.out, "request -- ")

        // open connection
        val isConnected = socket.open()
        if (!isConnected) println("failed to connect")

        val request = message.message.writeData()
        val response = socket.sendReceive(request)
        // close connection
        socket.close()

        val responseMsg = NibssIsoMessage(messageFactory.parseMessage(response, 0))
        responseMsg.dump(System.out, "")


        // return response
        responseMsg.message.let {
            val responseCode = it.getObjectValue<String>(39)
            val message = IsoUtils.getIsoResult(responseCode)
            println("responseCode: $responseCode, message: $message")
        }
    }
}