package com.interswitchng.smartpos.config

import android.app.Application
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.old_shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.old_shared.models.core.POSConfig
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Test
import org.koin.standalone.get
import org.koin.standalone.getKoin
import org.koin.test.KoinTest


class DependencyGraphTest: KoinTest {
/*
    @Test
    fun checkDependencyGraph() {
        // add app context
        val appContext = module(override = true) {
            single { mock<Context>() }
        }

        // getResult all old_modules
        val moduleList = appModules + activityModules + appContext

        checkModules(moduleList)
    }*/

    @Test
    fun `check that dependency was setup after configuring IswPos`() {
        val app: Application = mock()
        val device: POSDevice = mock()
        val config: POSConfig = mock()


        IswPos.setupTerminal(app, device, config, true)
        val isw: IswPos = get()

        assertNotNull(isw)
        assertSame(isw, IswPos.getInstance())
    }


    @After
    fun tearDown() {
        getKoin().close()
    }
}