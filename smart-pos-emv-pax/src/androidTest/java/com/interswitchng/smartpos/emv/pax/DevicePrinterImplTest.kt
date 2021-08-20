package com.interswitchng.smartpos.emv.pax

import android.graphics.BitmapFactory
import android.os.Looper
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.interswitchng.smartpos.emv.pax.old.models.*
import com.interswitchng.smartpos.emv.pax.old.services.POSDeviceImpl
import com.interswitchng.smartpos.old_shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.old_shared.models.core.UserType
import com.interswitchng.smartpos.old_shared.models.posconfig.PrintObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class DevicePrinterImplTest {


    private lateinit var pos: POSDevice
    private val context = InstrumentationRegistry.getContext().applicationContext

    @Before
    fun setup() {
        Looper.prepare()
        pos = POSDeviceImpl.create(context)

    }


    private fun getCardSlip(): List<PrintObject> {

        val expiry = "2702"
        val pan = "5060990580000367864"
        val track2Data = "5060990580000367864D2702601018444995"
        val amount = "2100"

        val alias = "000007"
        val terminalId = "2069018M"
        val merchantId = "IBP000000001384"
        val merchantLocation = "AIRTEL NETWORKS LIMITED PH MALL"
        val currencyCode = "566"
        val posGeoCode = "0023400000000056"
        val merchantName = "Interswitch Groups"
        val terminalType = "PAX"
        val uniqueId = "280-820-589"
        val merchantCode = "MX5882"

        val now = Date()
        val info = TransactionInfo(
                stan = "000120",
                dateTime = now.toString(),
                amount = amount,
                cardPan = pan,
                cardExpiry = expiry,
                type = TransactionType.Purchase,
                authorizationCode = "00",
                pinStatus = "PIN Verified",
                cardType = "VISA CARD"
        )

        val config = POSConfiguration(alias, terminalId, merchantId, terminalType, uniqueId, merchantLocation, merchantCode, merchantName)

        val status = TransactionStatus(responseMessage = "Transaction Approved", responseCode = "00", AID = "A0000000031010", telephone = "08031234273")

        val cardSlip = CardSlip(config, status, info)

        return cardSlip.getTransactionStatus()
    }


    @Test
    fun printCardSlip() {
        val slip = getCardSlip()
//        pos.printer.printSlip(slip, UserType.Customer)
        val bm = BitmapFactory.decodeResource(context.resources, R.drawable.isw_logo_gtb)
        (pos as POSDeviceImpl).setCompanyLogo(bm)
        pos.printer.printSlip(listOf(), UserType.Customer)

        Thread.sleep(2000)
    }
}