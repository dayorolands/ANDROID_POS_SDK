package com.interswitch.smartpos.emv.telpo.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import com.interswitch.smartpos.emv.telpo.R
import com.interswitchng.smartpos.shared.interfaces.device.DevicePrinter
import com.interswitchng.smartpos.shared.interfaces.device.EmvCardReader
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.telpo.emv.EmvService
import com.telpo.pinpad.PinpadService
import com.telpo.tps550.api.util.StringUtil

class TelpoPOSDeviceImpl constructor(
        override val printer: DevicePrinter,
        private val factory: () -> EmvCardReader,
        private val context: Context
): POSDevice {

    override fun getEmvCardReader(): EmvCardReader = factory()

    override val name: String
        get() = DEVICE_NAME

    override val hasFingerPrintReader: Boolean get() = true

    override fun loadInitialKey(initialKey: String, ksn: String) {
        // get bytes of initial key and serial number
        val keyValue = StringUtil.toBytes(initialKey)
        val ksnValue = StringUtil.toBytes(ksn)

        // open emv service and device
        EmvService.Open(context)
        EmvService.Device_Open(context)

        // open pinpad service
        PinpadService.Open(context)
        // write initial key to pin pad service
        PinpadService.TP_PinpadWriteDukptIPEK(keyValue, ksnValue, 0, 0, 0)

        // close device
        PinpadService.Close()
        EmvService.Device_Close()
    }

    override fun loadMasterKey(masterKey: String) {
        EmvService.Open(context)
        EmvService.deviceOpen()
        PinpadService.Open(context)

        val key = StringUtil.toBytes(masterKey)
        PinpadService.TP_WriteMasterKey(INDEX_TMK, key, PinpadService.KEY_WRITE_DIRECT)
    }

    override fun loadPinKey(pinKey: String) {

        val key = StringUtil.toBytes(pinKey)
        PinpadService.TP_WritePinKey(INDEX_TPK, key, PinpadService.KEY_WRITE_DIRECT, INDEX_TMK)

        PinpadService.Close()
        EmvService.Device_Close()

    }

    fun setCompanyLogo(bitmap: Bitmap) {
        companyLogo = bitmap
    }

    companion object {

        internal const val INDEX_TIK = 0x01
        internal const val INDEX_TMK = 0x01
        internal const val INDEX_TPK = 0x03
        internal const val DEVICE_NAME: String = "TELPO"

        internal lateinit var companyLogo: Bitmap private set

        @JvmStatic
        internal fun create(context: Context, printer: DevicePrinter, factory: () -> EmvCardReader): TelpoPOSDeviceImpl {
            // setupDevice pos device
            setupDevice()

            // setup logo
            val drawable = ContextCompat.getDrawable(context,
                R.drawable.isw_telpo_app_logo
            )!!
            companyLogo = run {
                return@run when(drawable) {
                    is BitmapDrawable -> drawable.bitmap
                    else -> Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888).also { bitmap ->
                        val canvas = Canvas(bitmap)
                        drawable.setBounds(0, 0, canvas.width, canvas.height)
                        drawable.draw(canvas)
                    }
                }
            }

            return TelpoPOSDeviceImpl(
                printer,
                factory,
                context
            )
        }


        @JvmStatic
        fun create(context: Context): TelpoPOSDeviceImpl {
            val printer: DevicePrinter =
                TelpoDevicePrinterImpl(
                    context
                )
            val factory = {
                TelpoEmvCardReaderImpl(
                    context
                )
            }
            return create(
                context,
                printer,
                factory
            )
        }

        private var isSetup = false

        private fun setupDevice() {
            isSetup = true
        }
    }
}