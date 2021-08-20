package com.interswitchng.smartpos.shared.models.transaction.ussdqr.response

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.interswitchng.smartpos.shared.utilities.DisplayUtils


/**
 * This class captures information representing server response
 * for a USSD or QR code request
 */
internal data class CodeResponse(
        val responseCode: String,
        val responseDescription: String?,
        val date: String?,
        val reference: String?,
        val bankShortCode: String?,
        val defaultShortCode: String?,
        val terminalId: String?,
        val transactionReference: String?,
        val qrCodeData: String?
) {


    var qrCodeImage: Bitmap? = null

    val isSuccessful: Boolean get() = responseCode == OK

    @Throws(WriterException::class)
    internal fun setBitmap(context: Context, lengthInDp: Int = 240) {
        qrCodeData?.let { qrCodeImage = setBitmap(context, it, lengthInDp) }
    }


    companion object {
        internal const val OK = "00"
        internal const val PENDING = "Z0"
        internal const val BAD_REQUEST = "10400"
        internal const val SERVER_ERROR = "10500"


        /**
         * This method extracts the bitmap of a given qr code string
         *
         * @param context  the given view context in which to render the generated image
         * @param qrCodeData  the given qr string to be transformed to qr image
         * @param lengthInDp  the square length in pixel density representing dimensions for the generated bitmap
         * @return   the generated bitmap image
         */
        internal fun setBitmap(context: Context, qrCodeData: String, lengthInDp: Int = 240): Bitmap? {

            try {
                // colors
                val white = ContextCompat.getColor(context, android.R.color.white)
                val black = ContextCompat.getColor(context, android.R.color.black)

                // length in pixels
                val length = DisplayUtils.convertDpToPixel(lengthInDp.toFloat(), context).toInt()

                // create bitmap matrix
                val bitMatrix: BitMatrix = MultiFormatWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, length, length, null)

                // getResult matrix dimensions
                val bitMatrixWidth = bitMatrix.width
                val bitMatrixHeight = bitMatrix.height

                // create array with area of matrix
                val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

                // generate the coloring of bitmap with area
                for (y in 0 until bitMatrixHeight) {
                    val offset = y * bitMatrixWidth

                    for (x in 0 until bitMatrixWidth) {
                        val color =
                                if (bitMatrix.get(x, y)) black
                                else white

                        // set the color of specific pixel
                        pixels[offset + x] = color
                    }
                }

                // create and return bitmap
                val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)
                bitmap.setPixels(pixels, 0, bitMatrixWidth, 0, 0, bitMatrixWidth, bitMatrixHeight)
                return bitmap

            } catch (e: IllegalArgumentException) {
                return null
            } catch (ex: Exception) {
                ex.printStackTrace()
                return null
            }
        }
    }
}
