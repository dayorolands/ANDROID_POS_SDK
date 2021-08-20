package com.interswitchng.smartpos.base

import com.interswitchng.smartpos.mockservices.MockKeyValueStore
import com.interswitchng.smartpos.mockservices.MockHttpService
import com.interswitchng.smartpos.mockservices.MockUserStore
import com.interswitchng.smartpos.old_shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.old_shared.interfaces.library.UserStore
import com.interswitchng.smartpos.old_shared.interfaces.library.HttpService
import org.koin.dsl.module.module


private val mockServiceModule = module(override = true) {
    single<KeyValueStore> { MockKeyValueStore() }
    single<HttpService> { MockHttpService.Builder().build() }
    single<UserStore> { MockUserStore(get()) }
}


val mockAppModules = listOf(mockServiceModule)