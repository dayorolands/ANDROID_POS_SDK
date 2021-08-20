package com.interswitchng.interswitchpossdkdemo.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.interswitchng.interswitchpossdkdemo.R
import com.interswitchng.interswitchpossdkdemo.fragments.DefaultTransactionsFragment
import com.interswitchng.interswitchpossdkdemo.fragments.MoreTransactionsFragment
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.shared.utilities.toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home_drawer.*

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private val instance by lazy { IswPos.getInstance() }

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        setSupportActionBar(homeToolbar)

        // setup the drawer layout
        // Find our drawer view
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, homeToolbar,
            R.string.isw_drawer_open,
            R.string.isw_drawer_close
        );

        // Tie DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener(drawerToggle)

        // attach menu nav click listeners
        arrayOf(history, reports, settings, transactions, help).forEach {
            it.setOnClickListener(this)
        }

        homeToolbar.setNavigationOnClickListener {
            // show default on back arrow click
            if (!drawerToggle.isDrawerIndicatorEnabled) showDefault()
            // show side menu on menu-click
            else drawerLayout.openDrawer(Gravity.LEFT)
        }
    }

    override fun onStart() {
        super.onStart()

        // Sync the toggle state after has occurred.
        drawerToggle.syncState()

        // show default options
        showDefault()
    }

    fun showMore() {
        homeToolbar.title = "Payment Method"

        // show Fragment
        show(MoreTransactionsFragment())

        // show back arrow
        drawerToggle.isDrawerIndicatorEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showDefault() {
        homeToolbar.title = ""

        // show Fragment
        show(DefaultTransactionsFragment())


        // show hamburger menu
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()
    }

    private fun show(fragment: Fragment) {
        // load card fragment
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.isw_fade_in,
                R.anim.isw_fade_out,
                R.anim.isw_fade_in,
                R.anim.isw_fade_out
            )
            .replace(R.id.home_content, fragment, fragment.tag)
            .commit()

        supportFragmentManager.executePendingTransactions()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.settings -> instance.gotoSettings()
            R.id.history -> instance.gotoHistory()
            R.id.reports -> instance.gotoReports()
            R.id.transactions -> showHomeSettings()
            R.id.help -> toast("No view yet")
        }

        // hide the drawer
        drawerLayout.closeDrawer(GravityCompat.START)
    }


    private fun showHomeSettings() {
        val intent = Intent(this, HomeSettingActivity::class.java)
        startActivity(intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}

