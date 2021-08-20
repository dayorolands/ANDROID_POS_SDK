package com.interswitchng.smartpos.usb

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import com.google.gson.Gson
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult

import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.utils.IswCompositeDisposable
import com.interswitchng.smartpos.shared.utilities.Logger

import com.interswitchng.smartpos.usb.interfaces.UsbConnector
import com.interswitchng.smartpos.usb.models.Request
import com.interswitchng.smartpos.usb.utils.Constants.COMMAND_PROCESS_RESULT
import com.interswitchng.smartpos.usb.utils.Constants.COMMAND_RESTART_COMMUNICATION
import com.interswitchng.smartpos.usb.utils.Constants.COMMAND_START_SERVICE
import com.interswitchng.smartpos.usb.utils.Constants.KEY_MESSAGE_RESULT
import com.interswitchng.smartpos.usb.utils.Constants.KEY_SERVICE_COMMAND
import com.interswitchng.smartpos.usb.utils.NotificationUtil
import org.koin.android.ext.android.inject
import java.util.concurrent.Executors

class UsbService : Service() {
    // flag to check if service has been started
    private var mServiceIsStarted = false

    private val mainHandler = Handler(Looper.getMainLooper())
    private val gson by lazy { Gson() }

    private val logger = Logger.with("UsbService")
    private val usbConnector: UsbConnector by inject()
    private val executor by lazy { Executors.newSingleThreadExecutor() }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        logger.log("OnDestroy Synchronization service")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        // evaluate start command
        return intent.extras!!.let {
            val command = it.get(KEY_SERVICE_COMMAND) as Int
//            val result: IswTransactionResult? = intent.getParcelableExtra(KEY_MESSAGE_RESULT)

            logger.log("Received command $command")
            // respond to service command
            return@let when (command) {
                COMMAND_START_SERVICE -> startSynchronization()
//                COMMAND_PROCESS_RESULT -> processPurchaseResult(result!!)
                COMMAND_RESTART_COMMUNICATION -> restartCommunication()
                else -> stopSynchronization()
            }
        }
    }

    private fun startSynchronization(): Int {
        // ensure service has not been started yet
        if (!mServiceIsStarted) {
            val msg = "Starting Synchronization service"
            logger.log(msg)

            // check build version to determine what type of notification to build
            val isPreAndroidO = Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1

            // lunch notification depending on device version
            if (isPreAndroidO) NotificationUtil.PreO.createNotification(this)
            else NotificationUtil.O.createNotification(this)

            // start receiving commands
            startReceivingCommands()

            // set flag for started service
            mServiceIsStarted = true
        }

        return START_REDELIVER_INTENT
    }

    private fun stopSynchronization(): Int {
        if (mServiceIsStarted) {
            logger.log("Stopping Synchronization service")
            mServiceIsStarted = false

            if (usbConnector.isOpen()) usbConnector.close()

            stopForeground(true)
            stopSelf()
        }

        return START_NOT_STICKY
    }

    private fun restartCommunication(): Int {
        if (usbConnector.isOpen())
            usbConnector.close()

        startReceivingCommands()
        return START_STICKY
    }


    private fun startReceivingCommands() {


        executor.execute {
            makeToast("Establishing communication with PC")

            val isOpen = usbConnector.open()
            val toastMessage =
                    if (isOpen) "A connection has been made!!!"
                    else "Unable to establish connection to PC"

            makeToast(toastMessage)

            // return early if no connection was made
            if (!isOpen) return@execute


            while (mServiceIsStarted && usbConnector.isOpen() && !executor.isShutdown) {
                // receive message
                val message = usbConnector.receive()
                makeToast(message)
                val request = gson.fromJson(message, Request::class.java)
                // notify listener of message
                if (request != null) {
                    UsbConfig.messageListener?.onMessageReceived(PaymentType.Card, request.amount)
                }
            }
        }

    }

    private fun processPurchaseResult(result: IswTransactionResult): Int {
        if (usbConnector.isOpen()) {
            val message = gson.toJson(result)
            usbConnector.sendAsync(message)
            logger.log("purchase result: $message")
        }
        return START_STICKY
    }

    private fun makeToast(msg: String) {
        mainHandler.post { Toast.makeText(this, msg, Toast.LENGTH_LONG).show() }
    }
}
