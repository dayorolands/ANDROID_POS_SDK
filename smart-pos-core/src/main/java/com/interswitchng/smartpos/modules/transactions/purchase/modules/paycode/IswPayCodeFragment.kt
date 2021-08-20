package com.interswitchng.smartpos.modules.transactions.purchase.modules.paycode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.gojuno.koptional.None
import com.gojuno.koptional.Some
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.modules.transactions.purchase.shared.fragments.IswBasePaymentFragment
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.utilities.DisplayUtils
import com.interswitchng.smartpos.shared.utils.DisplayUtils.runWithInternet
import com.interswitchng.smartpos.shared.utils.DisplayUtils.toast
import kotlinx.android.synthetic.main.isw_fragment_paycode.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class IswPayCodeFragment : IswBasePaymentFragment(), ScanBottomSheet.ScanResultCallback {

    private val payCodeViewModel: PayCodeViewModel by viewModel()
    private lateinit var transactionResult: TransactionResultData


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.isw_fragment_paycode, container, false)
    }


    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        setupUI()
    }

    private fun setupUI() {

        btnContinue.setOnClickListener {

            // ensure that a paycode has been typed in
            if (payCode.text.isNullOrEmpty() || payCode.text.isNullOrBlank()) {
                toast("A Paycode is required")
                return@setOnClickListener
            }

            // ensure that device is connected to internet
            runWithInternet {
                // disable buttons
                btnContinue.isEnabled = false
                btnContinue.isClickable = false

                // disable scan button
                btnScanCode.isClickable = false
                btnScanCode.isEnabled = false

                // hide keyboard
                DisplayUtils.hideKeyboard(requireActivity())

                // start paycode process
                processOnline()
            }
        }

        btnScanCode.setOnClickListener {
            ScanBottomSheet
                    .newInstance()
                    .show(childFragmentManager, ScanBottomSheet.toString())
        }

    }

    private fun processOnline() {
        // show transaction progress alert
        showLoader("Processing Transaction")

        // observe result
        payCodeViewModel.transactionResult.observe(viewLifecycleOwner, Observer {
            it?.let { option ->
                when (option) {
                    is None -> showError("Unable to process Transaction", this::processOnline)
                    is Some -> {
                        closeLoader()
                        // set result
                        transactionResult = option.value
                        // show transaction result screen
                        showTransactionResult(transactionResult)
                    }
                }
            }
        })

        // get the paycode
        val code = payCode.text.toString()
        // initiate paycode purchase
        payCodeViewModel.processOnline(terminalInfo, code, paymentInfo)
    }

    override fun onScanComplete(result: String) {
        payCode.setText(result)
        btnContinue.performClick()
    }

    override fun onScanError(code: Int) {
        // TODO handle code response
    }


    companion object {
        fun newInstance(paymentInfo: IswPaymentInfo) : IswPayCodeFragment {

            // create fragment and arguments
            val fragment = IswPayCodeFragment()
            val bundle = Bundle().apply {
                putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)
            }

            // set fragment arguments
            fragment.arguments = bundle
            return fragment
        }
    }


}
