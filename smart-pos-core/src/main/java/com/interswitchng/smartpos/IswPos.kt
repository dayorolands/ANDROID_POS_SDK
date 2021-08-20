package com.interswitchng.smartpos

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.interswitchng.smartpos.di.networkModule
import com.interswitchng.smartpos.di.serviceModule
import com.interswitchng.smartpos.di.viewModels
import com.interswitchng.smartpos.modules.menu.history.HistoryActivity
import com.interswitchng.smartpos.modules.menu.report.ReportActivity
import com.interswitchng.smartpos.modules.menu.settings.SettingsOptionsActivity
import com.interswitchng.smartpos.modules.print.PrinterModule
import com.interswitchng.smartpos.modules.transactions.IswPaymentActivity
import com.interswitchng.smartpos.shared.IswConstants.KEY_BILL_INFO
import com.interswitchng.smartpos.shared.IswConstants.KEY_PAYMENT_INFO
import com.interswitchng.smartpos.shared.errors.NotConfiguredException
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.shared.interfaces.device.POSFingerprint
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.POSConfig
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.core.Transaction
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.results.IswPrintResult
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.payments.billpayment.IswBillPaymentInfo
import com.zhuinden.monarchy.Monarchy
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.inject
import java.util.*


/**
 * This class is the primary bridge used to interact with the payment SDK.
 * It is responsible for triggering the purchase request and presenting the
 * final result of the triggered transaction
 *
 */
class IswPos private constructor(private val app: Application, internal val device: POSDevice, internal val config: POSConfig) {

    /**
     * Callback interface for payment
     */
    interface IswPaymentCallback {
        /**
         * Called when user cancels the current payment transaction
         */
        fun onUserCancel()

        /**
         * Called when the current payment transaction has completed
         */
        fun onPaymentCompleted(result: IswTransactionResult)
    }

    /**
     * Callback interface for printer
     */
    interface IswPrinterCallback {
        /**
         * Called when an error occurs in printing
         */
        fun onError(result: IswPrintResult)

        /**
         * Called when the current printing is completed
         */
        fun onPrintCompleted(result: IswPrintResult)
    }

    // callback instance
    private var callback: IswPaymentCallback? = null
    private var pinterCallback: IswPrinterCallback? = null
    private val printerModule = PrinterModule()


    @Throws(NotConfiguredException::class)
    fun pay(amount: Int, activityCallback: IswPaymentCallback, transaction: Transaction, surcharge: Int = 0, additionalAmounts: Int = 0) {
        // ensure the terminal has been configured
        if (!isConfigured()) throw NotConfiguredException()

        // ensure callback doesn't exist
        // before triggering another transaction
        if (callback == null) {
            // set callback
            this.callback = activityCallback


            // get transaction stan
            val stan = getNextStan()
            // create payment info for purchase request
            val paymentInfo = IswPaymentInfo(amount, stan, surcharge, additionalAmounts)

            // create intent start activity with new task
            val intent = Intent(app, IswPaymentActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(KEY_PAYMENT_INFO, paymentInfo)
                .let { transaction.bundleUp(it) }

            // start activity
            app.startActivity(intent)
        }
    }

    @Throws(NotConfiguredException::class)
    fun initiatePayment(activity: FragmentActivity, amount: Int, paymentType: PaymentType?) {
    }

    @Throws(NotConfiguredException::class)
    fun payBill(amount: Int, activityCallback: IswPaymentCallback,
                paymentCode: String, customerId: String,
                customerPhone: String = "", customerEmail: String =""){

        // ensure the terminal has been configured
        if (!isConfigured()) throw NotConfiguredException()

        // ensure callback doesn't exist
        // before triggering another transaction
        if (callback == null) {
            // set callback
            this.callback = activityCallback


            // get transaction stan
            val stan = getNextStan()
            // create payment info for request
            val paymentInfo = IswPaymentInfo(amount, stan)

            //create bills info
            val billInfo = IswBillPaymentInfo(paymentCode, customerId, customerPhone, customerEmail)

            // create intent start activity with new task
            val intent = Intent(app, IswPaymentActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra(KEY_PAYMENT_INFO, paymentInfo)
                    .putExtra(KEY_BILL_INFO, billInfo)
                    .let { Transaction.Payments.bundleUp(it) }

            // start activity
            app.startActivity(intent)
        }

    }

    fun canPrint(printerCallback: IswPrinterCallback) {
        if(this.pinterCallback == null){
            this.pinterCallback = printerCallback
            printerModule.canPrint()
        }
    }

    fun print(printObject: List<PrintObject>, printerCallback: IswPrinterCallback){
        if(this.pinterCallback == null){
            this.pinterCallback = printerCallback
            printerModule.print(printObject)
        }
    }

    internal fun cancelPayment() {
        resetState(true, null)
    }

    internal fun setResult(result: IswTransactionResult) {
        resetState(false, result)
    }

     private fun resetState(isCancel: Boolean, result: IswTransactionResult?) {
        // ensure callback exists
        callback?.apply {
            // trigger callback
            if (isCancel) onUserCancel()
            else onPaymentCompleted(result!!)
        }

        // reset callback
        callback = null
    }

    internal fun setPrintResult(result: IswPrintResult){
        pinterCallback?.apply {
            if(result.status == "00") this.onPrintCompleted(result)
            else this.onError(result)
        }

        //reset callback
        pinterCallback = null
    }

    fun gotoSettings() = showSettingsScreen()

    fun goToSettingsUpdatePage() = showSettingsUpdateScreen()

    fun gotoReports() = showScreen(ReportActivity::class.java)

    fun gotoHistory() = showScreen(HistoryActivity::class.java)

    companion object {
        // code used to start purchase request
        const val CODE_PURCHASE: Int = 23849
        // purchase result information
        private const val KEY_PURCHASE_RESULT = "purchase_result_key"

        private object Container: KoinComponent
        private const val KEY_STAN = "stan"
        private const val HAS_FINGERPRINT = "fingerprint_support"
        private lateinit var INSTANCE: IswPos
        private var isSetup = false
        private val store: KeyValueStore by Container.inject()

        private const val IMAGE_NAME = "bitmap_img"


        /**
         * This function determines if the sdk has been configured
         */
        internal fun isConfigured () = TerminalInfo.get(store) != null


        /**
         * This method is responsible for setting up the terminal for the current application
         */
        @JvmStatic
        fun setupTerminal(
            app: Application,
            device: POSDevice,
            fingerPrint: POSFingerprint?,
            config: POSConfig,
            withRealm: Boolean
        ) {
            if (!isSetup) {

                // prevent multiple threads from creating iswPos
                synchronized(this) {
                    INSTANCE = IswPos(app, device, config)
                }

                // add app context
                val appContext = module(override = true) {
                    single { app.applicationContext }
                    single { device }
                    if (fingerPrint != null) {
                        single<POSFingerprint> { fingerPrint }
                    }
                }

                // set up koin
                val modules = listOf(appContext, serviceModule, networkModule, viewModels)
                loadKoinModules(modules)

                // setup usb connector if exists
                config.usbConnector?.configure(app)

                // setup monarchy and realmdb
                if (withRealm) Monarchy.init(app)

                // set setup flag
                isSetup = true
            }
        }


        /**
         * This method returns the next STAN (System Trace Audit Number)
         */
        internal fun getNextStan(): String {
            var stan = store.getNumber(KEY_STAN, 0)

            // compute and save new stan
            val newStan = if (stan > 999999) 0 else ++stan
            store.saveNumber(KEY_STAN, newStan)

            return String.format(Locale.getDefault(), "%06d", newStan)
        }


        /**
         * This method loads the settings screen
         */
        @JvmStatic
        fun showSettingsScreen() = showScreen(SettingsOptionsActivity::class.java)


        /**
         * This method loads the settings screen
         */
        @JvmStatic
        fun showSettingsUpdateScreen() = println("showScreen(TerminalSettingsActivity::class.java)")

        @JvmStatic
        fun showMainActivity() = println("showScreen(MainActivity::class.java)")

        private fun showScreen(clazz: Class<*>) {
            val app = INSTANCE.app
            val intent = Intent(app, clazz).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            app.startActivity(intent)
        }

        /**
         * This method returns the single instance of IswPos for the current app
         */
        @JvmStatic
        fun getInstance(): IswPos = INSTANCE
    }
}