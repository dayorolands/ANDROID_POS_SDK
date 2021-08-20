package com.interswitchng.smartpos.config

import android.app.Application
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.old_shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.old_shared.models.core.POSConfig
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class IswPosTest {

    @Before
    fun setup() {
        val app: Application = mock()
        val device: POSDevice = mock()
        val config: POSConfig = mock()
        IswPos.setupTerminal(app, device, config, true)
    }

    @Test
    fun `should return iswPos instance when retrieved`() {

        // test that the value is present on a different thread
        Thread().run {
            val value = IswPos.getInstance()

            assertNotNull("IswPos is null", value)

            // test that they are the same values on different threads
            Thread().run {
                val secondValue = IswPos.getInstance()
                assertSame("multiple instances were created", value, secondValue)
            }
        }

    }
}