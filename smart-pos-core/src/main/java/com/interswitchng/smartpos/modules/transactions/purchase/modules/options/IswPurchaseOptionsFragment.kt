package com.interswitchng.smartpos.modules.transactions.purchase.modules.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.transactions.purchase.IswPurchaseFragment.PurchasePage as PurchasePage
import com.interswitchng.smartpos.modules.transactions.purchase.IswPurchaseViewModel
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.modules.transactions.purchase.shared.fragments.IswBasePaymentFragment
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import kotlinx.android.synthetic.main.isw_fragment_options.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

internal class IswPurchaseOptionsFragment: IswBasePaymentFragment(), View.OnClickListener {

    private val purchaseViewModel: IswPurchaseViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.isw_fragment_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // add click listener to all buttons
        arrayOf(btnCard, btnWallet, btnQr, btnUssd, btnCash).forEach {
            it.setOnClickListener(this)
        }

        // attach listener to back arrow
        optionsToolbar.setNavigationOnClickListener {
            parent.dismiss()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnCard -> purchaseViewModel.setCurrentPage(PurchasePage.Card)
            R.id.btnWallet -> purchaseViewModel.setCurrentPage(PurchasePage.PayCode)
            R.id.btnUssd -> purchaseViewModel.setCurrentPage(PurchasePage.USSD)
            R.id.btnQr -> purchaseViewModel.setCurrentPage(PurchasePage.QR)
            R.id.btnCash -> purchaseViewModel.setCurrentPage(PurchasePage.Cash)
        }
    }



    companion object {
        fun newInstance(paymentInfo: IswPaymentInfo) : IswPurchaseOptionsFragment {

            // create fragment and arguments
            val fragment = IswPurchaseOptionsFragment()
            val bundle = Bundle().apply {
                putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)
            }

            // set fragment arguments
            fragment.arguments = bundle
            return fragment
        }
    }

}