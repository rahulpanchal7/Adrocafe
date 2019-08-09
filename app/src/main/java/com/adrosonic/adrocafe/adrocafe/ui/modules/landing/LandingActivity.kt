package com.adrosonic.adrocafe.adrocafe.ui.modules.landing

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.databinding.ActivityLandingBinding
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.login.LoginActivity
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.FoodFragment
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.OrderFragment
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.app_bar_landing.*
import kotlinx.android.synthetic.main.nav_header_landing.view.*

class LandingActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var preferenceHelper: PreferenceHelper ?= null
    private var activityBinding: ActivityLandingBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_landing)
        setSupportActionBar(toolbar_landing)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container_landing, FoodFragment(), ConstantsDirectory.foodfragment)
            .commit()

        preferenceHelper = PreferenceHelper(application)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar_landing, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)

        setDrawerCustomProperties()

        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        setHeaderText()
    }

    private fun setDrawerCustomProperties(){
        drawer_layout.useCustomBehavior(Gravity.START)
        drawer_layout.setViewScale(Gravity.START, 0.9f)
        drawer_layout.setViewElevation(Gravity.START, 20f)
        drawer_layout.setViewScrimColor(Gravity.START, Color.TRANSPARENT)
        drawer_layout.setDrawerElevation(Gravity.START, 25f)
        drawer_layout.setRadius(Gravity.START, 25f)
    }

    private fun setHeaderText(){
        val username = preferenceHelper?.getValueString(ConstantsDirectory.PREFS_USERNAME)
        val useremail = preferenceHelper?.getValueString(ConstantsDirectory.PREFS_USEREMAIL)
        nav_view.getHeaderView(0).apply {
            textView_username.text = username
            textView_useremail.text = useremail
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_food -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_landing, FoodFragment(),"food")
                    .commit()
            }
            R.id.nav_oders -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_landing, OrderFragment(), "order")
                    .commit()
            }
            R.id.nav_credits -> {

            }
            R.id.nav_about -> {

            }
//            R.id.nav_history -> {
//
//            }
            R.id.nav_settings -> {

            }
            R.id.nav_logout -> {
                preferenceHelper?.removeValue(ConstantsDirectory.PREFS_ACCESS_TOKEN)
                preferenceHelper?.save(ConstantsDirectory.IS_LOGGED_IN, false)
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
