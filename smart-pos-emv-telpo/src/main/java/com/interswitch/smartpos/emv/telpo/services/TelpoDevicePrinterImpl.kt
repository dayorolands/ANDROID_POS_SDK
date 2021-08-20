package com.interswitch.smartpos.emv.telpo.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.widget.Toast
import com.interswitchng.smartpos.shared.interfaces.device.DevicePrinter
import com.interswitchng.smartpos.shared.models.core.UserType
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.posconfig.PrintStringConfiguration
import com.interswitchng.smartpos.shared.models.printer.info.PrintStatus
import com.telpo.tps550.api.TelpoException
import com.telpo.tps550.api.printer.UsbThermalPrinter
import java.lang.Exception

class TelpoDevicePrinterImpl constructor(private val context: Context) : DevicePrinter {

    private val printer = UsbThermalPrinter(context)

    private val line: String = "-".repeat(SCREEN_NORMAL_LENGTH)

    override fun printSlip(slip: List<PrintObject>, isReceipt: Boolean): PrintStatus {
        printer.apply {
            reset()
            setAlgin(UsbThermalPrinter.ALGIN_LEFT)
        }

        printer.setLineSpace(1)
        printer.walkPaper(8)

        if(isReceipt){
            //Print the company's logo
            printCompanyLogo()
        }

        for (item in slip) printItem(item)

//        // print thank you message at end of slip
//        val thankYouMsg = PrintObject.Data("Powered By InterSwitch", PrintStringConfiguration(displayCenter = true))
//        printItem(thankYouMsg)
//
//        // print pos version at end of slip
//        val posVersion = PrintObject.Data("SmartPOS version 1.0.0", PrintStringConfiguration(displayCenter = true))
//        printItem(posVersion)

        // print phone number at end of slip
        val phoneNumber = PrintObject.Data("Tel: 07009065000", PrintStringConfiguration(displayCenter = true))
        printItem(phoneNumber)

        // print email at end of slip
        val email = PrintObject.Data("Email: ifiscustomercare@interswitchgroup.com", PrintStringConfiguration(displayCenter = true))
        printItem(email)

        return try {
            printer.printString()
            // set step distance
            printer.walkPaper(8)
            PrintStatus.Ok("Printed")
        } catch (exception: TelpoException) {
            PrintStatus.Ok("Failed to print: ${exception.localizedMessage}")
        }
    }

    override fun canPrint(): PrintStatus {
        return when (printer.checkStatus()) {
            UsbThermalPrinter.STATUS_NO_PAPER -> PrintStatus.Error("No paper in the tray")
            UsbThermalPrinter.STATUS_OVER_FLOW -> PrintStatus.Error("Overflow error")
            UsbThermalPrinter.STATUS_OVER_HEAT -> PrintStatus.Error("Device overheating")
            UsbThermalPrinter.STATUS_UNKNOWN -> PrintStatus.Error("Unknown error")
            else -> PrintStatus.Ok("Can print!")
        }
    }

    override fun printCompanyLogo() {
        printer.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE)
        TelpoPOSDeviceImpl.companyLogo.also { logo ->
            // copy out bitmap
            val it = logo.copy(logo.config, logo.isMutable)
            val smallScale =
                    if (it.width == it.height) getScaledDownBitmap(it)
                    else getScaledDownBitmap(it, threshold = 200)
            val paddingLeft = ((SCREEN_NORMAL_LENGTH * 12.5) - smallScale.width) / 2 // 1 dot in print is 12.5px

            // add padding to bitmap
            val outputBitmap = Bitmap.createBitmap(smallScale.width + paddingLeft.toInt(), smallScale.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(outputBitmap)
            // draw logo in output bitmap
            canvas.drawColor(Color.WHITE)
            canvas.drawBitmap(smallScale, paddingLeft.toFloat() - 50.toFloat(), 0f, null)

            // print bitmap
            printer.printLogo(outputBitmap, false)
        }
    }

    /**
     * @param bitmap the Bitmap to be scaled
     * @param threshold the maximum dimension (either width or height) of the scaled bitmap
     * @param isNecessaryToKeepOrig is it necessary to keep the original bitmap? If not recycle the original bitmap to prevent memory leak.
     */

    fun getScaledDownBitmap(bitmap: Bitmap, threshold: Int = 150, isNecessaryToKeepOrig: Boolean = false): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        var newWidth = width
        var newHeight = height

        if (width > height && width > threshold) {
            newWidth = threshold
            newHeight = (height * newWidth.toFloat() / width).toInt() + 50
        }

        if (width in (height + 1)..threshold) {
            //the bitmap is already smaller than our required dimension, no need to resize it
            return bitmap
        }

        if (width < height && height > threshold) {
            newHeight = threshold
            newWidth = (width * newHeight.toFloat() / height).toInt()
        }

        if (height in (width + 1)..threshold) {
            //the bitmap is already smaller than our required dimension, no need to resize it
            return bitmap
        }

        if (width == height && width > threshold) {
            newWidth = threshold
            newHeight = newWidth
        }

        return if (width == height && width <= threshold) {
            //the bitmap is already smaller than our required dimension, no need to resize it
            bitmap
        } else getResizedBitmap(bitmap, newWidth, newHeight, isNecessaryToKeepOrig)

    }

    private fun printItem(item: PrintObject) {
        when (item) {
            is PrintObject.Line -> printer.addString(line)
            is PrintObject.BitMap -> printer.printLogo(item.bitmap, true)
            is PrintObject.Data -> {
                // strip all new lines
                val dataValue = item.value.trim()
                // get string print config
                val printConfig = item.config

                // set font size
                when {
                    printConfig.isTitle -> {
                        printer.setGray(7)
                        printer.setTextSize(25)
                    }
                    else -> {
                        printer.setTextSize(19)
                    }
                }

                // print string
                if (printConfig.displayCenter) {
                    printer.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE)
                    printer.addString(dataValue)
                } else {
                    printer.setAlgin(UsbThermalPrinter.ALGIN_LEFT)
                    printer.addString(dataValue)
                }
            }

        }
    }

    private fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int, isNecessaryToKeepOrig: Boolean): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
        if (!isNecessaryToKeepOrig) {
            bm.recycle()
        }
        return resizedBitmap
    }

    companion object {
        private const val SCREEN_NORMAL_LENGTH = 34
    }
}