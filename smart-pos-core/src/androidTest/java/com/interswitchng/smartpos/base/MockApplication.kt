package com.interswitchng.smartpos.base

import android.app.Application
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.mockservices.MockPOSDevice
import com.interswitchng.smartpos.mockservices.Printer
import com.interswitchng.smartpos.old_shared.models.core.POSConfig
import org.koin.standalone.StandAloneContext.loadKoinModules

class MockApplication: Application() {

    override fun onCreate() {
        super.onCreate()


        val alias = "000007"
        val terminalId = "2069018M"
        val clientId = "IBP000000001384"
        val clientSecret = "secret"
        val merchantLocation = "AIRTEL NETWORKS LIMITED PH MALL"
        // val currencyCode = "566"
        // val posGeoCode = "0023400000000056"
        val terminalType = "PAX"
        val uniqueId = "280-820-589"
        val merchantCode = "MX5882"


        // setup device for terminal
        val device = MockPOSDevice(Printer())
        val config = POSConfig(alias, clientId, clientSecret, merchantCode)
        IswPos.setupTerminal(this, device, config)

        // load mock old_modules based on runner arguments
        if (shouldMock) {
            // override existing old_modules
            loadKoinModules(mockAppModules)
        }
    }

    companion object {
        internal var shouldMock = true
    }

}