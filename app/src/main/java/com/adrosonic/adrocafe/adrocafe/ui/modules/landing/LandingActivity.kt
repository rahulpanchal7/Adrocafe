package com.adrosonic.adrocafe.adrocafe.ui.modules.landing

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adrosonic.adrocafe.adrocafe.AdrocafeApp
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.MessageEvent
import com.adrosonic.adrocafe.adrocafe.data.User
import com.adrosonic.adrocafe.adrocafe.databinding.ActivityLandingBinding
import com.adrosonic.adrocafe.adrocafe.databinding.FragmentLoginBinding
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.repository.local.AppDatabase
import com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.login.LoginActivity
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.FoodFragment
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.OrderFragment
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.app_bar_landing.*
import kotlinx.android.synthetic.main.nav_header_landing.view.*


class LandingActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var preferenceHelper: PreferenceHelper ?= null

    private var activityBinding: ActivityLandingBinding ?= null

    private var appDatabase: AppDatabase ?= null  //initialize within scope

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_landing)
        setSupportActionBar(toolbar_landing)
        appDatabase = (application.applicationContext as AdrocafeApp).appDatabase
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
            R.id.nav_orders -> {
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
                appDatabase?.OrderDao()?.nukeOrders()
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : CompletableObserver {
                        override fun onComplete() {
                            Log.i("delete orders", "completed")
                        }

                        override fun onSubscribe(d: Disposable) {
                            if (d.isDisposed) Log.i("Disposed", "true")
                        }

                        override fun onError(e: Throwable) {
                            Log.e("delete orders", e.message)
                        }
                    })

                appDatabase?.ProductDao()?.nukeProducts()
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : CompletableObserver {
                        override fun onComplete() {
                            Log.i("delete products", "completed")
                        }

                        override fun onSubscribe(d: Disposable) {
                            if (d.isDisposed) Log.i("Disposed", "true")
                        }

                        override fun onError(e: Throwable) {
                            Log.e("delete products", e.message)
                        }

                    }
                    )

                appDatabase?.UserDao()?.nukeUsers()  //background task
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : CompletableObserver {
                        override fun onComplete() {
                            Log.i("delete user", "completed")
                        }

                        override fun onSubscribe(d: Disposable) {
                            if (d.isDisposed) Log.i("Disposed", "true")
                        }

                        override fun onError(e: Throwable) {
                            Log.e("delete user", e.message)
                        }
                    })

                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
