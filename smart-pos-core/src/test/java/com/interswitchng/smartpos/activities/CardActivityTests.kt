package com.interswitchng.smartpos.activities

import android.content.Intent
import com.interswitchng.smartpos.old_modules.card.CardActivity
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CardActivityTests {

    private lateinit var cardActivity: CardActivity

    @Before
    fun setup() {

        val cardIntent = Intent()
        cardActivity = Robolectric.buildActivity(CardActivity::class.java, cardIntent).create().get()
    }

    fun `card layout should be invisible when card is inserted`() {

    }
}