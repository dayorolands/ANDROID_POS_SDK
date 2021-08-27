package com.interswitchng.smartpos.modules.menu.settings

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.interswitchng.smartpos.modules.menu.settings.terminal.TerminalInformation
import com.interswitchng.smartpos.shared.interfaces.library.IsoService
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.services.kimono.KimonoIsoServiceImpl
import com.interswitchng.smartpos.shared.services.kimono.models.AllTerminalInfo
import com.interswitchng.smartpos.shared.utilities.FileUtils
import com.interswitchng.smartpos.shared.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import org.koin.standalone.inject
import java.io.InputStream

internal class SettingsViewModel : BaseViewModel(), KoinComponent {

    private val store: KeyValueStore by inject()

    private val _keysDownloadSuccess = MutableLiveData<Boolean>()
    val keysDownloadSuccess: LiveData<Boolean> = _keysDownloadSuccess

    private val _configDownloadSuccess = MutableLiveData<Boolean>()
    val configDownloadSuccess: LiveData<Boolean> = _configDownloadSuccess

    private val _merchantDetailsDownloadSuccess = MutableLiveData<AllTerminalInfo>()
    val merchantDetailsDownloadSuccess: LiveData<AllTerminalInfo> = _merchantDetailsDownloadSuccess


    fun downloadKeys(
        terminalId: String,
        ip: String,
        port: Int,
        isKimono: Boolean
    ) {
        val isoService: IsoService = get { parametersOf(isKimono) }
        uiScope.launch {
            val isSuccessful = withContext(ioScope) {
                isoService.downloadKey(terminalId, ip, port)
            }
            _keysDownloadSuccess.value = isSuccessful
        }
    }

    fun downloadTerminalConfig(terminalId: String, ip: String, port: Int, isKimono: Boolean) {
        val isoService: IsoService = get { parametersOf(isKimono) }
        uiScope.launch {
            val isSuccessful =
                withContext(ioScope) { isoService.downloadTerminalParameters(terminalId, ip, port) }
            _configDownloadSuccess.value = isSuccessful
            //Logger.with("Settings ViewModel").logErr(isoService.downloadTerminalParameters(terminalId,ip,port).toString())
            println("Settings ViewModel : $isSuccessful")
        }
    }

    fun getTerminalInformation(xmlFile: InputStream): TerminalInformation =
        FileUtils.readXml(TerminalInformation::class.java, xmlFile)

    fun downloadMerchantDetails(){
        val kimonoService: KimonoIsoServiceImpl = get()

        val terminalSerialNo = Build.SERIAL
        uiScope.launch {
            val response =
                    withContext(ioScope) { kimonoService.downloadMerchantDetails(terminalSerialNo) }

            _merchantDetailsDownloadSuccess.value = response
        }
    }
}