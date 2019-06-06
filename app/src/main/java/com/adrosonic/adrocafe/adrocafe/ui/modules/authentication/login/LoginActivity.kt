package com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.MessageEvent
import com.adrosonic.adrocafe.adrocafe.databinding.ActivityLoginBinding
import com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.reset.ResetPasswordFragment
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.LandingActivity
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class LoginActivity : AppCompatActivity() {

    var binding: ActivityLoginBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    LoginFragment.newInstance()
                )
                .commitNow()
        }
    }

    @Subscribe
    public fun onEvent(event: MessageEvent){
        when (event.message){
            ConstantsDirectory.resetpasswordfragment ->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container,
                        ResetPasswordFragment.newInstance(ConstantsDirectory.resetpasswordfragment)
                    )
                    .addToBackStack(null)
                    .commit()
            }
            ConstantsDirectory.landingactivity ->{
                startActivity(Intent(this, LandingActivity::class.java))
                finish()
            }
            ConstantsDirectory.forgetpasswordfragment ->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container,
                        ResetPasswordFragment.newInstance(ConstantsDirectory.forgetpasswordfragment)
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
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
