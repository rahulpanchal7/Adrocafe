package com.adrosonic.adrocafe.adrocafe.ui.modules.landing

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.adrosonic.adrocafe.adrocafe.R
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.app_bar_landing.*

class LandingActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        setSupportActionBar(toolbar_landing)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar_landing, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)

        setDrawerCustomProperties()

        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun setDrawerCustomProperties(){
        drawer_layout.useCustomBehavior(Gravity.START)
        drawer_layout.setViewScale(Gravity.START, 0.9f)
        drawer_layout.setViewElevation(Gravity.START, 20f)
        drawer_layout.setViewScrimColor(Gravity.START, Color.TRANSPARENT)
        drawer_layout.setDrawerElevation(Gravity.START, 25f)
        drawer_layout.setRadius(Gravity.START, 25f)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.landing, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
