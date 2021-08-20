package com.interswitchng.smartpos.usb

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult

import com.interswitchng.smartpos.usb.interfaces.MessageListener
import com.interswitchng.smartpos.usb.interfaces.UsbConnector
import com.interswitchng.smartpos.usb.services.UsbConnectionManager
import com.interswitchng.smartpos.usb.utils.Constants
import com.interswitchng.smartpos.shared.interfaces.library.UsbConnector as UsbCoreConnector
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

class UsbConfig : UsbCoreConnector {

    internal lateinit var app: Application

    override fun configure(app: Application) {
        this.app = app

        val module = module {
            single<UsbConnector> { UsbConnectionManager() }
        }

        loadKoinModules(module)
        // start comms service
        startUsbService(app)
    }

    fun setMessageListener(listener: MessageListener) {
        messageListener = listener
    }

    fun removeMessageListener() {
        messageListener = null
    }

    fun startService(context: Context) = startUsbService(context)


//    fun handleResult(result: IswTransactionResult) {
//        val intent = Intent(app, UsbService::class.java).apply {
//            putExtra(Constants.KEY_MESSAGE_RESULT, result)
//            putExtra(Constants.KEY_SERVICE_COMMAND, Constants.COMMAND_PROCESS_RESULT)
//        }
//        app.startService(intent)
//    }

    companion object {

        internal var messageListener: MessageListener? = null


        @TargetApi(Build.VERSION_CODES.O)
        internal fun startUsbService(context: Context) {
            // create intent to start service
            val intent = Intent(context, UsbService::class.java).apply {
                putExtra(Constants.KEY_SERVICE_COMMAND, Constants.COMMAND_START_SERVICE)
            }

            // check build version to determine what type of notification to build
            val isPreAndroidO = Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1

            // start service
            if (isPreAndroidO) context.startService(intent)
            else ContextCompat.startForegroundService(context, intent)
        }

    }
}
