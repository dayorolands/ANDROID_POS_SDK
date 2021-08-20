package com.interswitchng.smartpos.usb.utils

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import java.util.*
import androidx.core.app.NotificationCompat
import com.interswitchng.smartpos.usb.BuildConfig
import com.interswitchng.smartpos.usb.R
import com.interswitchng.smartpos.usb.UsbService
import com.interswitchng.smartpos.usb.utils.Constants.COMMAND_RESTART_COMMUNICATION
import com.interswitchng.smartpos.usb.utils.Constants.COMMAND_STOP_SERVICE
import com.interswitchng.smartpos.usb.utils.Constants.KEY_SERVICE_COMMAND


object NotificationUtil {

    const val ONGOING_NOTIFICATION_ID = 5012
    val SMALL_ICON = R.drawable.ic_smart_pos_notification
    const val CHANNEL_NAME = "SmartPOS notification Channel"



    /** PendingIntent to stop the service.  */
    private fun getStopServicePI(context: Service): PendingIntent {
        val iStopService = Intent(context, UsbService::class.java).apply { putExtra(KEY_SERVICE_COMMAND, COMMAND_STOP_SERVICE) }
        return PendingIntent.getService(context, getRandomNumber(), iStopService, 0)
    }

    /** PendingIntent to restart service usb communication.  */
    private fun getRestartCommunicationPI(context: Service): PendingIntent {
        val iRestartCommunication = Intent(context, UsbService::class.java).apply { putExtra(KEY_SERVICE_COMMAND, COMMAND_RESTART_COMMUNICATION) }
        return PendingIntent.getService(context, getRandomNumber(), iRestartCommunication, 0)
    }


    //
    // Pre O specific versions.
    //

    @TargetApi(25)
    object PreO {

        fun createNotification(context: Service) {

            // Create a notification.
            val builder = NotificationCompat.Builder(context, "PreO channel")
                    .setContentTitle(getNotificationTitle(context))
                    .setContentText(getNotificationContent(context))
                    .setSmallIcon(SMALL_ICON)
                    .setStyle(NotificationCompat.BigTextStyle())


            val restartAction = getRestartAction(context)
            builder.addAction(restartAction)

            if (BuildConfig.DEBUG) {
                // Action to stop the service.
                val stopAction = getStopAction(context)
                builder.addAction(stopAction)
            }


            context.startForeground(ONGOING_NOTIFICATION_ID, builder.build())
        }
    }

    private fun getNotificationContent(context: Service): String {
        return context.getString(R.string.notification_text_content)
    }

    private fun getNotificationTitle(context: Service): String {
        return context.getString(R.string.notification_text_title)
    }

    //
    // Oreo and Above Specific versions.
    //

    @TargetApi(26)
    object O {

        private val CHANNEL_ID = "${getRandomNumber()}"

        fun createNotification(context: Service) {
            val channelId = createChannel(context)
            val builder = buildNotification(context, channelId)

            val restartAction = getRestartAction(context)
            builder.addAction(restartAction)

            if (BuildConfig.DEBUG) {
                // Action to stop the service.
                val stopAction = getStopAction(context)
                builder.addAction(stopAction)
            }

            context.startForeground(ONGOING_NOTIFICATION_ID, builder.build())
        }

        private fun buildNotification(context: Service, channelId: String): NotificationCompat.Builder {

            // Create a notification.
            return NotificationCompat.Builder(context, channelId)
                    .setContentTitle(getNotificationTitle(context))
                    .setContentText(getNotificationContent(context))
                    .setSmallIcon(SMALL_ICON)
                    .setStyle(NotificationCompat.BigTextStyle())
        }

        private fun createChannel(context: Service): String {
            // Create a channel.
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationManager.createNotificationChannel(notificationChannel)
            return CHANNEL_ID
        }
    }


    private fun getStopAction(context: Service): NotificationCompat.Action {
        return NotificationCompat.Action.Builder(0, getNotificationStopActionText(context), getStopServicePI(context)).build()
    }

    private fun getRestartAction(context: Service): NotificationCompat.Action {
        val restartText = context.getString(R.string.notification_reconnect_text)
        return NotificationCompat.Action.Builder(0, restartText, getRestartCommunicationPI(context)).build()
    }

    private fun getNotificationStopActionText(context: Service): String {
        return context.getString(R.string.notification_stop_action_text)
    }

    private fun getRandomNumber(): Int {
        return Random().nextInt(100000)
    }
}