package com.interswitchng.smartpos.activities

import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.old_modules.menu.settings.SettingsActivity
import com.interswitchng.smartpos.old_shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.old_shared.interfaces.library.IsoService
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.mockito.ArgumentMatchers.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController


@RunWith(RobolectricTestRunner::class)
class SettingsActivityTests {

    private lateinit var activityController: ActivityController<SettingsActivity>
    private lateinit var activity: SettingsActivity
    private val map = mutableMapOf<String, Any>()


    private val service: IsoService = mock {
        on(mock.downloadTerminalParameters(anyString())).thenAnswer {
            Thread.sleep(2000)
            true
        }
        on(mock.downloadKey(anyString())).thenAnswer {
            Thread.sleep(2000)
            true
        }
    }

    private val store: KeyValueStore = mock {
        on(mock.getNumber(anyString(), anyLong())) doAnswer {
            val key = it.arguments[0] as String
            val default = it.arguments[1] as Long
            map[key] as Long? ?: default
        }

        on(mock.saveNumber(anyString(), anyLong())) doAnswer {
            val key = it.arguments[0] as String
            val value = it.arguments[1] as Long
            map.set(key, value)
        }
    }

    @Before
    fun setup() {

        val module = module {
            single { store }
            single { service }
        }

        loadKoinModules(module)

        val intent = Intent()
        activityController = Robolectric.buildActivity(SettingsActivity::class.java, intent).create()
        activity = activityController.get()
    }


    @Test
    fun `should show error message and icon when 'keys download' is not successful`() {

        val terminalIdText = activity.findViewById<EditText>(R.id.etTerminalId)
        val keyButton = activity.findViewById<ImageView>(R.id.btnDownloadKeys)
        val keyText = activity.findViewById<TextView>(R.id.tvKeys)
        val keyDateText = activity.findViewById<TextView>(R.id.tvKeyDate)

        // return false to simulate error
        val service: IsoService = mock {
            on(mock.downloadKey(anyString())).thenAnswer {
                Thread.sleep(2000)
                false
            }
        }

        // load error service
        loadKoinModules(module(override = true) { single { service } })

        // start activity
        activityController.start()

        terminalIdText.setText("20390007")

        keyButton.performClick()
        Thread.sleep(3000)

        // run ui thread tasks
        Robolectric.flushForegroundThreadScheduler()

        // confirm that correct icon is being displayed
        assertEquals(activity.getDrawable(R.drawable.isw_ic_error)?.alpha, keyButton.drawable.alpha)
        // confirm correct error messages are being displayed
        assertEquals(activity.getString(R.string.isw_title_error_downloading_keys), keyText.text)
        assertEquals(activity.getString(R.string.isw_title_date, "No keys downloaded"), keyDateText.text)
    }

    @Test
    fun `should show correct error message and icon when 'terminal config download' is not successful`() {

        val terminalIdText = activity.findViewById<EditText>(R.id.etTerminalId)
        val terminalButton = activity.findViewById<ImageView>(R.id.btnDownloadTerminalConfig)
        val terminalText = activity.findViewById<TextView>(R.id.tvTerminalInfo)
        val terminalDateText = activity.findViewById<TextView>(R.id.tvTerminalInfoDate)


        // return false to simulate error
        val service: IsoService = mock {
            on(mock.downloadTerminalParameters(anyString())).thenAnswer {
                Thread.sleep(2000)
                false
            }
        }

        // load error service
        loadKoinModules(module(override = true) { single { service } })

        // start activity
        activityController.start()

        terminalIdText.setText("20390007")

        terminalButton.performClick()
        Thread.sleep(3000)

        // run ui thread tasks
        Robolectric.flushForegroundThreadScheduler()


        // confirm that correct icon is being displayed
        assertEquals(activity.getDrawable(R.drawable.isw_ic_error)?.alpha, terminalButton.drawable.alpha)
        // confirm error messages
        assertEquals(activity.getString(R.string.isw_title_error_downloading_terminal_config), terminalText.text)
        assertEquals(activity.getString(R.string.isw_title_date, "No terminal configuration"), terminalDateText.text)
    }


    @Test
    fun `should have correct workflow when 'key-download' button is clicked`() {

        val terminalIdText = activity.findViewById<EditText>(R.id.etTerminalId)
        val keyProgress = activity.findViewById<ProgressBar>(R.id.progressKeyDownload)
        val keyButton = activity.findViewById<ImageView>(R.id.btnDownloadKeys)
        val keyText = activity.findViewById<TextView>(R.id.tvKeys)
        val keyDateText = activity.findViewById<TextView>(R.id.tvKeyDate)

        // start activity
        activityController.start()

        terminalIdText.setText("12345678")

        // confirm that download key button is visible
        assertEquals(keyButton.visibility, View.VISIBLE)
        // confirm that progress is not visible
        assertEquals(keyProgress.visibility, View.GONE)
        // confirm the text visible
        assertEquals(keyText.text, activity.getString(R.string.isw_title_download_keys))
        // confirm that last download is visible
        assertEquals(keyDateText.visibility, View.VISIBLE)


        keyButton.performClick()
        Thread.sleep(1000)

        // confirm button is disabled
        assertEquals(false, keyButton.isEnabled)
        // confirm that download key button is not visible
        assertEquals(keyButton.visibility, View.GONE)
        // confirm that progress now visible
        assertEquals(keyProgress.visibility, View.VISIBLE)
        // confirm the text being shown
        assertEquals(keyText.text, activity.getString(R.string.isw_title_downloading_keys))
        // confirm that last download is not visible
        assertEquals(keyDateText.visibility, View.GONE)
        Thread.sleep(5000)

        // run ui thread tasks
        Robolectric.flushForegroundThreadScheduler()

        // confirm that correct icon is being displayed
        assertEquals(keyButton.drawable.alpha, activity.getDrawable(R.drawable.isw_ic_check)?.alpha)
        // confirm that changes are reverted
        assertEquals(keyButton.visibility, View.VISIBLE)
        assertEquals(keyProgress.visibility, View.GONE)
        assertEquals(keyText.text, activity.getString(R.string.isw_title_keys_downloaded))
        assertEquals(keyDateText.visibility, View.VISIBLE)
    }


    @Test
    fun `should have correct workflow when download_terminal config is clicked`() {

        val terminalIdText = activity.findViewById<EditText>(R.id.etTerminalId)
        val terminalProgress = activity.findViewById<ProgressBar>(R.id.progressTerminalDownload)
        val terminalButton = activity.findViewById<ImageView>(R.id.btnDownloadTerminalConfig)
        val terminalText = activity.findViewById<TextView>(R.id.tvTerminalInfo)
        val terminalDateText = activity.findViewById<TextView>(R.id.tvTerminalInfoDate)

        // start activity
        activityController.start()

        terminalIdText.setText("12345678")

        // confirm that download terminal button is visible
        assertEquals(terminalButton.visibility, View.VISIBLE)
        // confirm that progress is not visible
        assertEquals(terminalProgress.visibility, View.GONE)
        // confirm the text visible
        assertEquals(terminalText.text, activity.getString(R.string.isw_title_download_terminal_configuration))
        // confirm that last download is visible
        assertEquals(terminalDateText.visibility, View.VISIBLE)


        terminalButton.performClick()
        Thread.sleep(1000)

        // confirm button is disabled
        assertEquals(false, terminalButton.isEnabled)
        // confirm that download terminal button is not visible
        assertEquals(terminalButton.visibility, View.GONE)
        // confirm that progress now visible
        assertEquals(terminalProgress.visibility, View.VISIBLE)
        // confirm the text being shown
        assertEquals(terminalText.text, activity.getString(R.string.isw_title_downloading_terminal_config))
        // confirm that last download is not visible
        assertEquals(terminalDateText.visibility, View.GONE)
        Thread.sleep(5000)

        // run ui thread tasks
        Robolectric.flushForegroundThreadScheduler()


        // confirm that correct icon is being displayed
        assertEquals(terminalButton.drawable.alpha, activity.getDrawable(R.drawable.isw_ic_check)?.alpha)
        // confirm that changes are reverted
        assertEquals(terminalButton.visibility, View.VISIBLE)
        assertEquals(terminalProgress.visibility, View.GONE)
        assertEquals(terminalText.text, activity.getString(R.string.isw_title_terminal_config_downloaded))
        assertEquals(terminalDateText.visibility, View.VISIBLE)
    }
}