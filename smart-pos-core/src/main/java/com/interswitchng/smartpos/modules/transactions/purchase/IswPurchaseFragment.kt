package com.interswitchng.smartpos.modules.transactions.purchase

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.interswitchng.smartpos.IswPos.Companion.getNextStan
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.transactions.purchase.modules.card.IswCardFragment
import com.interswitchng.smartpos.modules.transactions.purchase.modules.cash.IswCashFragment
import com.interswitchng.smartpos.modules.transactions.purchase.modules.options.IswPurchaseOptionsFragment
import com.interswitchng.smartpos.modules.transactions.purchase.modules.paycode.IswPayCodeFragment
import com.interswitchng.smartpos.modules.transactions.purchase.modules.result.IswPurchaseResultFragment
import com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.fragments.IswQrFragment
import com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.fragments.IswUssdFragment
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.shared.fragments.IswBottomSheetFragment
import com.interswitchng.smartpos.shared.models.core.Transaction
import com.interswitchng.smartpos.shared.models.results.IswPurchaseResult
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import org.koin.android.viewmodel.ext.android.sharedViewModel

internal class IswPurchaseFragment : IswBottomSheetFragment() {

    enum class PurchasePage { Options, Card, PayCode, USSD, QR, Cash, Result }

    private lateinit var currentPage: PurchasePage
    private val purchaseViewModel: IswPurchaseViewModel by sharedViewModel()
    private lateinit var homePage: IswPurchaseOptionsFragment
    private lateinit var result: IswPurchaseResult
    private lateinit var transactionResult: TransactionResultData


    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        // get the selected payment type
        val purchaseType = arguments?.getString(KEY_PURCHASE_TYPE)

        // get the current page based on chosen payment type
        val selectedPage = when (purchaseType?.let(PaymentType::valueOf)) {
            PaymentType.Card -> PurchasePage.Card
            PaymentType.PayCode -> PurchasePage.PayCode
            PaymentType.USSD -> PurchasePage.USSD
            PaymentType.QR -> PurchasePage.QR
            PaymentType.Cash -> PurchasePage.Cash
            else -> PurchasePage.Options
        }

        // show the selected page
        show(selectedPage)
    }

    override fun onViewCreated(view: View, savedInstance: Bundle?) {
        super.onViewCreated(view, savedInstance)

        purchaseViewModel.currentPage.observe(viewLifecycleOwner, Observer {
            // return if null
            val currentPage = it ?: return@Observer

            // select the current page
            show(currentPage)
        })

        // show the current page
        show(currentPage)
    }


    private fun show(page: PurchasePage) {
        if (::currentPage.isInitialized && page == currentPage) return

        // update transaction stan for card and paycode
        iswPaymentInfo = iswPaymentInfo.copy(currentStan = getNextStan())

        // update selected page
        currentPage = page


        val fragment = when (page) {
            PurchasePage.Card -> IswCardFragment.newInstance(iswPaymentInfo)
            PurchasePage.PayCode -> IswPayCodeFragment.newInstance(iswPaymentInfo)
            PurchasePage.QR -> IswQrFragment.newInstance(iswPaymentInfo)
            PurchasePage.USSD -> IswUssdFragment.newInstance(iswPaymentInfo)
            PurchasePage.Cash -> IswCashFragment.newInstance(iswPaymentInfo)
            PurchasePage.Result -> IswPurchaseResultFragment.newInstance(
                iswPaymentInfo,
                transactionResult
            )
            PurchasePage.Options -> {
                // if navigating back to options
                if (::homePage.isInitialized) {
                    // navigate back to home page (options)
                    childFragmentManager.popBackStack(homePage.idTag, 0)

                    // toggle bottom sheet lock and return
                    return setSheetDraggable(currentPage == PurchasePage.Options)
                }

                homePage = IswPurchaseOptionsFragment.newInstance(iswPaymentInfo)
                homePage
            }
        }

        // load card fragment
        childFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.isw_fade_in,
                R.anim.isw_fade_out,
                R.anim.isw_fade_in,
                R.anim.isw_fade_out
            )
            .replace(R.id.currentPage, fragment, fragment.idTag)
            // add home page to back navigation
            .addToBackStack(fragment.idTag)
            .commit()

        childFragmentManager.executePendingTransactions()

        // toggle bottom sheet lock
        setSheetDraggable(currentPage == PurchasePage.Options)
    }


    // navigate back to home page (options page)
    internal fun goHome() = purchaseViewModel.setCurrentPage(PurchasePage.Options)

    override fun hasPaymentResult(): Boolean = ::result.isInitialized

    override fun cancelPayment() =  dismiss()

    override fun showResult(result: TransactionResultData) {
        // set the result
        this.result = IswPurchaseResult(result)
        transactionResult = result
        // show result
        purchaseViewModel.setCurrentPage(PurchasePage.Result)
    }

    override fun getResult(): IswPurchaseResult = result

    companion object {
        const val KEY_PURCHASE_TYPE = "key_purchase_type"

        fun newInstance(
            transaction: Transaction.Purchase,
            paymentInfo: IswPaymentInfo
        ): IswPurchaseFragment {
            val fragment =
                IswPurchaseFragment()
            // create arguments for fragment
            val bundle = Bundle()
            bundle.putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)
            bundle.putString(KEY_PURCHASE_TYPE, transaction.type?.name)
            // set arguments for fragment
            fragment.arguments = bundle
            return fragment
        }
    }
}