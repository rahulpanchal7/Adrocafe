package com.adrosonic.adrocafe.adrocafe.ui.modules.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.MessageEvent
import com.adrosonic.adrocafe.adrocafe.data.Orders
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.LandingActivity
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CartFragment.newInstance())
                .commitNow()
        }
    }

    @Subscribe
    fun onEvent(event: MessageEvent){
        startActivity(Intent(this, LandingActivity::class.java))
        finishAffinity()
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
