package com.interswitchng.smartpos.emv.pax.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import com.interswitchng.smartpos.emv.pax.R
import com.interswitchng.smartpos.shared.interfaces.device.EmvCardReader
import com.interswitchng.smartpos.shared.interfaces.device.DevicePrinter
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.emv.pax.emv.DeviceImplNeptune
import com.interswitchng.smartpos.emv.pax.utilities.EmvUtils
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.pax.dal.IDAL
import com.pax.dal.entity.ECheckMode
import com.pax.dal.entity.EPedKeyType
import com.pax.dal.entity.EPedType
import com.pax.jemv.device.DeviceManager
import com.pax.neptunelite.api.NeptuneLiteUser


/**
 * This class provides Implementation for the [POSDevice]
 */
class POSDeviceImpl private constructor(override val printer: DevicePrinter, private val factory: () -> EmvCardReader) : POSDevice {


    init {
        System.loadLibrary("F_DEVICE_LIB_PayDroid")
        System.loadLibrary("F_EMV_LIB_PayDroid")
        System.loadLibrary("F_ENTRY_LIB_PayDroid")
        System.loadLibrary("JNI_EMV_v100")
        System.loadLibrary("JNI_ENTRY_v100")
        System.loadLibrary("F_PUBLIC_LIB_PayDroid")
    }

    private val ped by lazy { dal.getPed(EPedType.INTERNAL) }

    override val name get() = DEVICE_NAME

    override val hasFingerPrintReader: Boolean = false

    override fun getEmvCardReader(): EmvCardReader = factory()

    override fun loadInitialKey(initialKey: String, ksn: String) {
        val keyValue = EmvUtils.str2Bcd(initialKey)
        val ksnValue = EmvUtils.str2Bcd(ksn)
        ped.writeTIK(INDEX_TIK, 0.toByte(), keyValue, ksnValue, ECheckMode.KCV_NONE, null)
    }

    override fun loadMasterKey(masterKey: String) {
        val masterKeyValue = EmvUtils.str2Bcd(masterKey)
        ped.writeKey(EPedKeyType.TLK, 0.toByte(), EPedKeyType.TMK, INDEX_TMK, masterKeyValue, ECheckMode.KCV_NONE, null)
    }

    override fun loadPinKey(pinKey: String) {
        val checkMode = ECheckMode.KCV_NONE
        val key = EmvUtils.str2Bcd(pinKey)
        ped.writeKey(EPedKeyType.TMK, 0.toByte(), EPedKeyType.TPK, INDEX_TPK, key, checkMode, null)
    }


    fun setCompanyLogo(bitmap: Bitmap) {
        companyLogo = bitmap
    }


    companion object {

        internal const val INDEX_TIK: Byte = 0x01
        internal const val INDEX_TMK: Byte = 0x01
        internal const val INDEX_TPK: Byte = 0x03
        internal const val DEVICE_NAME: String = "PAX"

        private var isSetup = false

        internal lateinit var companyLogo: Bitmap private set

        @JvmStatic
        lateinit var dal: IDAL private set

        @JvmStatic
        internal fun create(context: Context, printer: DevicePrinter = DevicePrinterImpl, factory: () -> EmvCardReader, companyLogo: Int = R.drawable.isw_pax_app_logo): POSDeviceImpl {
            // setupDevice pos device
            setupDevice(context)

            // setup logo
            val drawable = ContextCompat.getDrawable(context, companyLogo)!!
            this.companyLogo = run {
                return@run when (drawable) {
                    is BitmapDrawable -> drawable.bitmap
                    else -> Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888).also { bitmap ->
                        val canvas = Canvas(bitmap)
                        drawable.setBounds(0, 0, canvas.width, canvas.height)
                        drawable.draw(canvas)
                    }
                }
            }

            return POSDeviceImpl(printer, factory)
        }


        @JvmStatic
        fun create(context: Context): POSDeviceImpl {
            val printer: DevicePrinter = DevicePrinterImpl
            val factory = { EmvCardReaderImpl(context) }
            return create(context, printer, factory)
        }

        private fun setupDevice(context: Context) {
            if (!isSetup) {
                val neptunes = NeptuneLiteUser.getInstance()
                dal = neptunes.getDal(context)
                // set IDevice implementation
                DeviceManager.getInstance().setIDevice(DeviceImplNeptune.getInstance())
                isSetup = true
            }
        }
    }
}