package com.interswitchng.smartpos.modules.transactions.purchase.modules.cash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.gojuno.koptional.None
import com.gojuno.koptional.Some
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.transactions.purchase.shared.fragments.IswBasePaymentFragment
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import org.koin.android.viewmodel.ext.android.viewModel

internal class IswCashFragment : IswBasePaymentFragment() {

    private val cashViewModel: CashViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return layoutInflater.inflate(R.layout.isw_fragment_cash, container, false)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        processOnline()
    }

    private fun processOnline() {
        // show transaction progress alert
        showLoader("Processing Transaction")

        cashViewModel.transactionResult.observe(viewLifecycleOwner, Observer {
            it?.let { optional ->
                when(optional){
                    is None ->  showError("Unable to process Transaction", this::processOnline)

                    is Some -> {
                        // close loader
                        closeLoader()

                        // show transaction result
                        showTransactionResult(optional.value)
                    }
                }

            }
        })

        // initiate cash purchase
        cashViewModel.processOnline(paymentInfo, terminalInfo)
    }


    companion object {
        fun newInstance(paymentInfo: IswPaymentInfo): IswCashFragment {

            // create fragment and arguments
            val fragment = IswCashFragment()
            val bundle = Bundle().apply {
                putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)
            }

            // set fragment arguments
            fragment.arguments = bundle
            return fragment
        }
    }
}