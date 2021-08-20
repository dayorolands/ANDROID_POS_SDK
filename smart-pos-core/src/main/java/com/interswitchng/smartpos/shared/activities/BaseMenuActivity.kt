package com.interswitchng.smartpos.shared.activities

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

abstract class BaseMenuActivity: AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // set back click to go back
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}