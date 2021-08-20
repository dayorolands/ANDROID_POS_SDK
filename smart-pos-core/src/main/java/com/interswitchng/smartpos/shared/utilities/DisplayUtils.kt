package com.interswitchng.smartpos.shared.utilities

import android.app.Activity
import android.content.Context
import android.provider.Settings.Secure.getString
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.CURRENCYTYPE
import com.interswitchng.smartpos.shared.models.core.IswLocal
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


internal object DisplayUtils: KoinComponent {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into dp
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param sp A value in (scalable pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent pixel equivalent to sp value
     */
    fun convertSpToPixels(sp: Float, context: Context): Float {
        val scale = context.resources.displayMetrics.scaledDensity
        return sp * scale
    }



    fun getIsoString(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        dateFormat.timeZone = TimeZone.getTimeZone("GMT+1")
        return dateFormat.format(date)
    }


    /**
     * This method converts interger amount notation to string (e.g. 1000 -> 1,000.00)
     *
     * @param amount A value in integer representing the transaction amount
     * @return A string representation of the decimal notation for the amount
     */
    fun getAmountString(amount: Double): String {

        val numberFormat = NumberFormat.getInstance()
        numberFormat.minimumFractionDigits = 2
        numberFormat.maximumFractionDigits = 2

        return numberFormat.format(amount)
    }

    fun getAmountString(amount: String): Double {
        val cleanString = amount.replace("[$,.]".toRegex(), "")
        val parsed = java.lang.Double.parseDouble(cleanString)
        return parsed/100
    }

    fun getAmountString(amount: Int): String {
        val amountAsDouble: Double = amount / 100.0
        return getAmountString(amountAsDouble)
    }

    fun getAmountString(iswPaymentInfo: IswPaymentInfo): String {
        return getAmountString(iswPaymentInfo.amount)
    }


    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        try {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
        }
    }

    fun getAmountWithCurrency(amount: String): String {
        val store: KeyValueStore  = get()

        // get the currency based on the terminal's configured currency code
        var currency = when (val config = TerminalInfo.get(store)) {
            null -> ""
            else -> when(config.currencyCode) {
                IswLocal.NIGERIA.code -> IswLocal.NIGERIA.currency
                else -> ""
            }
        }

        if (CURRENCYTYPE == IswLocal.USA.currency){
            currency = when(val config = TerminalInfo.get(store)){
                null -> ""
                else -> when(config.currencyCode2){
                    IswLocal.USA.code -> IswLocal.USA.currency
                    else -> ""
                }
            }
        }

        Logger.with("Display Utils").logErr( amount)
        var formattedAmount = getAmountString(amount.toInt())
        return "$currency $formattedAmount"
    }
}