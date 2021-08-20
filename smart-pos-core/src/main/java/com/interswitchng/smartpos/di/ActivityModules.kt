package com.interswitchng.smartpos.di


import com.interswitchng.smartpos.modules.menu.history.HistoryViewModel
import com.interswitchng.smartpos.modules.menu.report.ReportViewModel
import com.interswitchng.smartpos.modules.menu.settings.SettingsViewModel
import com.interswitchng.smartpos.modules.menu.settings.supervisor.enrollcard.EnrolledCardViewModel
import com.interswitchng.smartpos.modules.transactions.completion.viewmodels.IswCompletionPreAuthSelectionViewModel
import com.interswitchng.smartpos.modules.transactions.purchase.IswPurchaseViewModel
import com.interswitchng.smartpos.modules.transactions.purchase.modules.card.CardViewModel
import com.interswitchng.smartpos.modules.transactions.purchase.modules.cash.CashViewModel
import com.interswitchng.smartpos.modules.transactions.purchase.modules.paycode.PayCodeViewModel
import com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.viewModels.QrViewModel
import com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.viewModels.UssdViewModel
import com.interswitchng.smartpos.shared.interfaces.library.IsoService
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.services.kimono.KimonoIsoServiceImpl
import com.interswitchng.smartpos.shared.viewmodel.*
import com.interswitchng.smartpos.shared.viewmodel.CardFlowViewModel
import com.interswitchng.smartpos.shared.viewmodel.KimonoTransactionCardFlowViewModel
import com.interswitchng.smartpos.shared.viewmodel.TransactionCardFlowViewModel
import com.interswitchng.smartpos.shared.viewmodel.TransactionDetailViewModel
import com.interswitchng.smartpos.shared.viewmodel.TransactionResultViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module


internal val viewModels = module {

//     viewModel { AuthenticationViewModel() }
//    viewModel { FingerprintViewModel(get(), get()) }
//    viewModel { SetupFragmentViewModel(get(), get()) }

    viewModel { UssdViewModel(get()) }

    viewModel { QrViewModel(get()) }

    viewModel {
        val store: KeyValueStore = get()
        val terminalInfo = TerminalInfo.get(store)
        val isKimono = terminalInfo?.isKimono ?: false
        val isoService: IsoService = get { parametersOf(isKimono) }
        PayCodeViewModel(isoService, get())
    }


    viewModel {
        val store: KeyValueStore = get()
        val terminalInfo = TerminalInfo.get(store)
        val isKimono = terminalInfo?.isKimono ?: false

        val isoService: IsoService = get { parametersOf(isKimono) }
        CardViewModel(isoService)
    }

    viewModel { CashViewModel(get()) }

    viewModel { CardFlowViewModel(get()) }

    viewModel {
        val store: KeyValueStore = get()
        val terminalInfo = TerminalInfo.get(store)
        val isKimono = terminalInfo?.isKimono ?: false

        val isoService: IsoService = get { parametersOf(isKimono) }
        TransactionResultViewModel(
            get(),
            get(),
            get(),
            isoService
        )
    }

    viewModel { HistoryViewModel(get()) }

    viewModel { ReportViewModel(get(), get(), get()) }

    viewModel { SettingsViewModel() }

    viewModel { IswPurchaseViewModel() }

    viewModel {

        val store: KeyValueStore = get()
        val terminalInfo = TerminalInfo.get(store)
        val isKimono = terminalInfo?.isKimono ?: false
        val isoService: IsoService = get { parametersOf(isKimono) }

        TransactionCardFlowViewModel(isoService)
    }

    viewModel {
        val store: KeyValueStore = get()
        val terminalInfo = TerminalInfo.get(store)
        val isKimono = terminalInfo?.isKimono ?: false

        val isoService: IsoService = get { parametersOf(isKimono) }
        KimonoTransactionCardFlowViewModel(get(), isoService, get())
    }

    viewModel { IswCompletionPreAuthSelectionViewModel(get()) }

    viewModel { EnrolledCardViewModel(get()) }

    viewModel { TransactionDetailViewModel(get()) }

    viewModel { PrintViewModel(get()) }
}