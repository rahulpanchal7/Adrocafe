package com.adrosonic.adrocafe.adrocafe.ui.modules.cart

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrosonic.adrocafe.adrocafe.AdrocafeApp
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.*
import com.adrosonic.adrocafe.adrocafe.databinding.FragmentCartBinding
import com.adrosonic.adrocafe.adrocafe.repository.remote.API
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.FoodListAdapter
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.coroutines.processNextEventInCurrentThread
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory.cartactivity as cartactivity

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private var viewModel: CartViewModel ?= null
    private var foodListAdapter: FoodListAdapter ?= null
    private var products = mutableListOf<Product>()
    private var binding: FragmentCartBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodListAdapter = FoodListAdapter(products, ConstantsDirectory.all)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(CartViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel?.getProducts()?.observe(this, Observer {products ->
            foodListAdapter?.swap(products, ConstantsDirectory.all)
        })
        viewModel?.navigateTo?.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                EventBus.getDefault().post(MessageEvent(it))
            }
        })
        viewModel?.showDialog?.observe(this, Observer { message ->
            val dialog = AlertDialog.Builder(activity)
                .setMessage("Are you sure?")
                .setPositiveButton("Yes"
                ) { dialog, which -> viewModel?.placeOrder() }
                .setNegativeButton("Cancel", null)
                .create()
            dialog.show()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    private fun setupRecyclerView(){
        recyclerView_order.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = foodListAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAlterCartItems(event: AlterBadgeEvent){
        updateOrderQuantity(event.product)
    }

    private fun updateOrderQuantity(product: Product){
        (activity?.applicationContext as AdrocafeApp).appDatabase
            ?.ProductDao()
            ?.update(product)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : CompletableObserver {
                override fun onComplete() {
                    Log.i("update product","onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.i("update product","onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Log.i("update product",e.message)
                }

            })
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}
