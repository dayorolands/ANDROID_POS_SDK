package com.interswitchng.smartpos.di

import com.interswitchng.smartpos.BuildConfig
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.Constants
import com.interswitchng.smartpos.shared.interfaces.library.*
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.IswTransactionModule
import com.interswitchng.smartpos.shared.models.transaction.TransactionLogMigration
import com.interswitchng.smartpos.shared.services.EmailServiceImpl
import com.interswitchng.smartpos.shared.services.HttpServiceImpl
import com.interswitchng.smartpos.shared.services.UserStoreImpl
import com.interswitchng.smartpos.shared.services.kimono.KimonoIsoServiceImpl
import com.interswitchng.smartpos.shared.services.nibss.NibssIsoServiceImpl
import com.interswitchng.smartpos.shared.services.nibss.tcp.IsoSocketImpl
import com.interswitchng.smartpos.shared.services.storage.KeyValueStoreImpl
import com.interswitchng.smartpos.shared.services.storage.SharePreferenceManager
import com.interswitchng.smartpos.shared.services.storage.TransactionLogServiceImpl
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.zhuinden.monarchy.Monarchy
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

internal val serviceModule = module {
    single { IswPos.getInstance() }
    single { KimonoIsoServiceImpl(get(), get(), get()) }
    single<HttpService> { HttpServiceImpl(get()) }

    factory<IsoService> { (isKimono: Boolean) ->
        // return service based on kimono flag
        return@factory if (isKimono) KimonoIsoServiceImpl(get(), get(), get())
        else NibssIsoServiceImpl(androidContext(), get(), get(), get())
    }


    single<UserStore> { UserStoreImpl(get()) }
    single { SharePreferenceManager(androidContext()) }
    single<KeyValueStore> { KeyValueStoreImpl(get()) }
    single<EmailService> { EmailServiceImpl(get()) }





    single<TransactionLogService> {

        val filename = "com.interswitchng.smartpos"
        val realmKey = IsoUtils.hexToBytes(BuildConfig.REALM_KEY)
        // create realm configuration
        val realmConfig = RealmConfiguration.Builder()
            .name(filename)
            .encryptionKey(realmKey)
            .schemaVersion(3L)
            .migration(TransactionLogMigration())
            .modules(IswTransactionModule())
            .build()

        // build monarchy
        val monarchy = Monarchy.Builder()
            .setRealmConfiguration(realmConfig)
            .build()

        TransactionLogServiceImpl(monarchy)
    }
    factory<IsoSocket> {
        val resource = androidContext().resources

        // try getting terminal info
        val store: KeyValueStore = get()
        val terminalInfo = TerminalInfo.get(store)
        // getResult timeout based on terminal info
        val timeout = terminalInfo?.serverTimeoutInSec ?: resource.getInteger(R.integer.iswTimeout)

        val serverIp = terminalInfo?.serverIp ?: Constants.ISW_TERMINAL_IP
        val port = terminalInfo?.serverPort ?: BuildConfig.ISW_TERMINAL_PORT
        return@factory IsoSocketImpl(serverIp, port, timeout * 1000)
    }
}
