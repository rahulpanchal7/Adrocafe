package com.adrosonic.adrocafe.adrocafe.ui.modules.landing

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.MessageEvent
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.login.LoginActivity
import com.adrosonic.adrocafe.adrocafe.ui.modules.cart.CartActivity
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.FoodFragment
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.OrderFragment
import com.adrosonic.adrocafe.adrocafe.utils.BadgeDrawable
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.app_bar_landing.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LandingActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var preferenceHelper: PreferenceHelper ?= null
    private var cartMenu: Menu ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
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
    }

    private fun setDrawerCustomProperties(){
        drawer_layout.useCustomBehavior(Gravity.START)
        drawer_layout.setViewScale(Gravity.START, 0.9f)
        drawer_layout.setViewElevation(Gravity.START, 20f)
        drawer_layout.setViewScrimColor(Gravity.START, Color.TRANSPARENT)
        drawer_layout.setDrawerElevation(Gravity.START, 25f)
        drawer_layout.setRadius(Gravity.START, 25f)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAlterBadgeCount(event: MessageEvent){
        cartMenu?.let {menu ->
            Log.i("onAlter","Reached")
            val icon = menu.findItem(R.id.action_cart).icon as LayerDrawable
            val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
            val badge: BadgeDrawable = if (reuse is BadgeDrawable){
                reuse
            } else {
              BadgeDrawable(this)
            }
            badge.setCount(event.message)
            icon.mutate()
            icon.setDrawableByLayerId(R.id.ic_badge, badge)
        }
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
        cartMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
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
            R.id.nav_history -> {

            }
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
