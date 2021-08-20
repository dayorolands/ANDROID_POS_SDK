package com.interswitchng.smartpos.modules.menu.settings.supervisor.enrollcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.menu.settings.PinDialogFragment
import com.interswitchng.smartpos.shared.fragments.IswBottomSheetFragment
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvMessage
import com.interswitchng.smartpos.shared.utilities.SecurityUtils
import com.interswitchng.smartpos.shared.utils.DisplayUtils.toast
import kotlinx.android.synthetic.main.isw_cancel_button.*
import kotlinx.android.synthetic.main.isw_fragment_enroll_card.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

internal class EnrolledCardFragment : Fragment() {

    sealed class EnrollAction {
        object EnrollCard : EnrollAction()
        data class ValidateEnrolledCard(val onSuccess: () -> Unit) : EnrollAction()
    }

    enum class CardEnrollState {
        ShowInsertCard,
        EnterPin,
        ContinueToRefund
    }


    val store: KeyValueStore by inject()

    // terminal info
    val terminalInfo by lazy { TerminalInfo.get(store)!! }
    private val cardViewModel: EnrolledCardViewModel by viewModel()

    private lateinit var pinDialog: PinDialogFragment
    private lateinit var action: EnrollAction

    // flag to confirm that supervisor has been validated
    private var supervisorValidated = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.isw_fragment_enroll_card, container, false)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        // observe view model
        observeViewModel()

        // attach cancel button click listener
        closeBtn.setOnClickListener {
            // close enrollment dialog
            closeDialog()
        }

        // setup a none transaction for the card reader
        cardViewModel.setupTransaction(0, terminalInfo)
    }

    private fun observeViewModel() {
        with(cardViewModel) {

            val owner = { viewLifecycleOwner.lifecycle }

            // observe emv messages
            emvMessage.observe(owner) {
                it?.let(::processMessage)
            }
        }
    }

    private fun processMessage(message: EmvMessage) {

        // assigns value to ensure the when expression is exhausted
        when (message) {
            // when card should be inserted
            is EmvMessage.InsertCard -> {
                showContainer(CardEnrollState.ShowInsertCard)
            }

            // when card has been read
            is EmvMessage.CardRead -> {

                // ensure the card is an exiting supervisor card else return early
                if (action is EnrollAction.ValidateEnrolledCard) {
                    val savedPan = store.getString(KEY_SUPERVISOR_PAN, "")
                    // if no saved pan, then supervisor
                    // has not been enrolled
                    if (savedPan.isBlank()) {
                        return toast("Supervisor card has not been enrolled")
                    } else {
                        val hashedPan = SecurityUtils.getSecurePassword(message.cardPan)
                        // else if both pans are not equal then a new card is being used
                        if (hashedPan != savedPan) return toast("Invalid supervisor card")
                    }
                }

                // show enter pin hint
                showContainer(CardEnrollState.EnterPin)

                // create a pin dialog
                pinDialog = PinDialogFragment()

                // set pin validator
                pinDialog.onValidate { pin ->
                    when (action) {
                        is EnrollAction.EnrollCard -> savePin(message.cardPan, pin)
                        is EnrollAction.ValidateEnrolledCard -> validatePin(pin)
                    }
                }

                // set successful pin input listener
                pinDialog.onSuccess {
                    // trigger success callback for supervisor pin validation
                    when (action) {
                        is EnrollAction.ValidateEnrolledCard -> {
                            // set flag
                            supervisorValidated = true
                            // show continue button
                            showContainer(CardEnrollState.ContinueToRefund)
                        }
                        is EnrollAction.EnrollCard -> {
                            toast("Enrollment Successful!!!")
                            // close enrollment dialog
                            closeDialog()
                        }
                    }
                }

                // show pin dialog
                pinDialog.show(childFragmentManager, pinDialog.tag)
            }

            // when card gets removed
            is EmvMessage.CardRemoved -> {
                // check if supervisor is validated
                val isValidated =
                    action is EnrollAction.ValidateEnrolledCard && supervisorValidated

                // ignore card removal if validated
                if (isValidated) return

                val msgTitle =
                    if (action is EnrollAction.EnrollCard) "Enrollment"
                    else "Transaction"

                // else close dialog and
                toast("$msgTitle Cancelled: Card was removed")
                // close enrollment dialog
                closeDialog()
            }
        }
    }

    private fun showContainer(state: CardEnrollState) {

        if (state == CardEnrollState.EnterPin) {
            // show pin
            arrayOf(insertCardImage, insertCardHint).forEach { it.visibility = View.GONE }
            insertCardHintTitle.text = "Input your PIN"

        } else if (state == CardEnrollState.ContinueToRefund) {
            // show button to continue to refund
            insertCardHintTitle.text = "Remove Supervisor Card"
            insertCardHint.text = "Remove the supervisor card and click the button below to continue"

            // set views as visible
            arrayOf(insertCardImage, insertCardHint, btnContinueRefund).forEach {
                it.visibility = View.VISIBLE
            }

            // attach click listener to refund button
            btnContinueRefund.setOnClickListener {
                // trigger success action to continue refund
                val successAction = (action as EnrollAction.ValidateEnrolledCard).onSuccess
                successAction.invoke()
            }
        }
    }

    private fun savePin(pan: String, pin: String): Boolean {
        // get encrypted values
        val encryptedPan = SecurityUtils.getSecurePassword(pan)
        val encryptedPin = SecurityUtils.getSecurePassword(pin)

        // save encrypted values
        store.saveString(KEY_SUPERVISOR_PAN, encryptedPan)
        store.saveString(KEY_SUPERVISOR_PIN, encryptedPin)
        return true
    }

    private fun validatePin(pin: String): Boolean {
        // get admin configured pin
        val existingPin = store.getString(KEY_SUPERVISOR_PIN, "")

        // hash provided pin
        val providedPin = SecurityUtils.getSecurePassword(pin)
        val isValid = providedPin == existingPin

        // show message
        if (isValid) toast("PIN OK")
        else toast("Invalid PIN")

        return isValid
    }


    fun setAction(action: EnrollAction) {
        this.action = action
    }

    private fun closeDialog() {
        if (::pinDialog.isInitialized) pinDialog.dismiss()

        val parent = parentFragment as IswBottomSheetFragment
        // dismiss parent bottom sheet
        parent.dismiss()
    }

    companion object {
        private const val KEY_SUPERVISOR_PAN = "supervisor_pan_key"
        private const val KEY_SUPERVISOR_PIN = "supervisor_pan_pin"

        // create fragment and argument
        fun newInstance() = EnrolledCardFragment()
    }
}