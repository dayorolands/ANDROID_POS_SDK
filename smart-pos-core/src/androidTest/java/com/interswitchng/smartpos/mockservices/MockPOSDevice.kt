package com.interswitchng.smartpos.mockservices

import com.interswitchng.smartpos.old_shared.interfaces.device.EmvCardReader
import com.interswitchng.smartpos.old_shared.interfaces.device.DevicePrinter
import com.interswitchng.smartpos.old_shared.interfaces.device.POSDevice

internal class MockPOSDevice(override val printer: DevicePrinter) : POSDevice {

    override fun getEmvCardReader(): EmvCardReader {
        return MockEmvCardReaderImpl()
    }


}