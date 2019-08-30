package com.adrosonic.adrocafe.adrocafe.ui.modules.staff

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.adrosonic.adrocafe.adrocafe.AdrocafeApp
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.Orders
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.repository.local.AppDatabase
import com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.login.LoginActivity
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_staff_main.*
import java.io.File

class StaffMainActivity : AppCompatActivity() {

    private var preferenceHelper: PreferenceHelper?= null

    private var appDatabase: AppDatabase?= null  //initialize within scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_main)
        setSupportActionBar(toolbar_staff)

        appDatabase = (application.applicationContext as AdrocafeApp).appDatabase
        preferenceHelper = PreferenceHelper(application)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StaffMainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.staff, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_staff_logout -> {
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

                    }
                    )

                appDatabase?.ProductDao()?.nukeProducts()
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
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

