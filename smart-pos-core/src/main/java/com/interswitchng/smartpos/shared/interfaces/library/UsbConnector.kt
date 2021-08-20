package com.interswitchng.smartpos.shared.interfaces.library

import android.app.Application

interface UsbConnector {

    fun configure(app: Application)
}