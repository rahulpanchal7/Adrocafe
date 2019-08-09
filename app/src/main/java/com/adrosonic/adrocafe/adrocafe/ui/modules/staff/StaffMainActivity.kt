package com.adrosonic.adrocafe.adrocafe.ui.modules.staff

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.login.LoginActivity
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import kotlinx.android.synthetic.main.activity_staff_main.*

class StaffMainActivity : AppCompatActivity() {

    private var preferenceHelper: PreferenceHelper?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_main)
        setSupportActionBar(toolbar_staff)

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
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
