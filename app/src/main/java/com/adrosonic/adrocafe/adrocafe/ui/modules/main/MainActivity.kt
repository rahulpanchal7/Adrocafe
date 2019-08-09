package com.adrosonic.adrocafe.adrocafe.ui.modules.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.login.LoginActivity
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.LandingActivity
import com.adrosonic.adrocafe.adrocafe.ui.modules.staff.StaffMainActivity
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (PreferenceHelper(application).getValueBoolean(ConstantsDirectory.IS_LOGGED_IN)) {
            if (PreferenceHelper(application).getValueString(ConstantsDirectory.PREFS_ROLE) == ConstantsDirectory.staff)
                startActivity(Intent(this, StaffMainActivity::class.java))
            else
                startActivity(Intent(this, LandingActivity::class.java))
        }
        else
            startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
