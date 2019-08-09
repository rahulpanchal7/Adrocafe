package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food

import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.adrosonic.adrocafe.adrocafe.AdrocafeApp

import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.*
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.ui.modules.cart.CartActivity
import com.adrosonic.adrocafe.adrocafe.utils.BadgeDrawable
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_food.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FoodFragment : Fragment() {

    companion object {
        fun newInstance() = FoodFragment()
    }

    private var cartMenu: Menu ?= null
    private var viewModel: FoodViewModel ?= null
    private var isCartEmpty: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(FoodViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        childFragmentManager.let {
            viewPager_food.adapter = FoodPagerAdapter(it)
            tabLayout_food.setupWithViewPager(viewPager_food)
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAlterBadgeCount(event: AlterBadgeEvent){
        updateOrderQuantity(event.product)
    }

    private fun updateOrderQuantity(product: Product){
        (activity?.applicationContext as AdrocafeApp).appDatabase
            ?.ProductDao()
            ?.update(product)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : CompletableObserver{
                override fun onComplete() {
                    Log.i("update product","onComplete")
                    updateBadgeCount()
                }

                override fun onSubscribe(d: Disposable) {
                    Log.i("update product","onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Log.i("update product",e.message)
                }

            })
    }

    private fun updateBadgeCount(){
        (activity?.applicationContext as AdrocafeApp).appDatabase
            ?.ProductDao()
            ?.getTotalBadgeCount()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<Int>{
                override fun onSuccess(t: Int) {
                    isCartEmpty = t == 0
                    setBadgeCount(t.toString())
                }

                override fun onSubscribe(d: Disposable) {
                    Log.i("getTotalBadgeCount", "onSubscribe")
                }

                override fun onError(e: Throwable) {
                    e.stackTrace
                }

            })
    }

    private fun setBadgeCount(count: String){
        cartMenu?.let {menu ->
            val icon = menu.findItem(R.id.action_cart).icon as LayerDrawable
            val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
            val badge: BadgeDrawable = if (reuse is BadgeDrawable){
                reuse
            } else {
                BadgeDrawable(this.requireContext())
            }
            badge.setCount(count)
            icon.mutate()
            icon.setDrawableByLayerId(R.id.ic_badge, badge)
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.landing, menu)
        cartMenu = menu
        updateBadgeCount()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_cart -> {
                if (isCartEmpty)
                    Toast.makeText(context, "Cart is empty", Toast.LENGTH_SHORT).show()
                else
                    startActivity(Intent(this.context, CartActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}
