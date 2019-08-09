package com.adrosonic.adrocafe.adrocafe.ui.modules.cart

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adrosonic.adrocafe.adrocafe.AdrocafeApp
import com.adrosonic.adrocafe.adrocafe.data.*
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.repository.remote.API
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import io.reactivex.CompletableObserver
import io.reactivex.FlowableSubscriber
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val appDatabase = (application.applicationContext as AdrocafeApp).appDatabase
    private val preferenceHelper = PreferenceHelper(application)

    val products: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>().also {
            loadProducts()
        }
    }

    val showProgress =  ObservableBoolean(false)

    val _navigateTo = MutableLiveData<SingleLiveEvent<String>>()

    val navigateTo : LiveData<SingleLiveEvent<String>> = _navigateTo

    fun getProducts(): LiveData<List<Product>> {
        return products
    }

    private fun loadProducts(){
        appDatabase?.ProductDao()?.getOrderedProduct()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : FlowableSubscriber<List<Product>> {
                override fun onComplete() {
                    Log.i("getOrderedProduct", "onComplete")
                }

                override fun onSubscribe(s: Subscription) {
                    Log.i("getOrderedProduct", "onSubscribe")
                    s.request(Long.MAX_VALUE)
                }

                override fun onNext(t: List<Product>?) {
                    Log.i("getOrderedProduct", "onNext")
                    val productsList = arrayListOf<Product>()
                    t?.forEach {product ->
                        productsList.add(product)
                    }
                    products.value = productsList
                }

                override fun onError(t: Throwable?) {
                    Log.e("getOrderedProduct", t?.message)
                }

            })
    }

    fun placeOrder(){

        showProgress.set(true)
        val orderDetailsList = mutableListOf<OrderDetailsList>()
        products.value?.forEach {product ->
            orderDetailsList.add(OrderDetailsList(product.sellingprice.toString(), product.ordered_qty.toString(), product.id.toString()))
        }
        val useremail = preferenceHelper.getValueString(ConstantsDirectory.PREFS_USEREMAIL)
        val jwt = preferenceHelper.getValueString(ConstantsDirectory.PREFS_ACCESS_TOKEN)
        API.order().createOrder(Orders(null, useremail, orderDetailsList.toTypedArray(), "",  "", "", User(), ""), jwt)
            .enqueue(object: Callback<Orders> {
                override fun onFailure(call: Call<Orders>, t: Throwable) {
                    showProgress.set(false)
                    Log.e("createOrder", t.message)
                }

                override fun onResponse(call: Call<Orders>, response: Response<Orders>) {
                    showProgress.set(false)
                    Log.i("createOrder", response.message())
                    appDatabase?.ProductDao()?.updateOrderPlaced()
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(object : CompletableObserver{
                            override fun onComplete() {
                                _navigateTo.value = SingleLiveEvent(ConstantsDirectory.landingactivity)
                                Log.i("updateOrderPLaced", "onComplete")
                            }

                            override fun onSubscribe(d: Disposable) {
                                Log.i("updateOrderPlaced", "onSubscribe")
                            }

                            override fun onError(e: Throwable) {
                                Log.e("updateOrderPlaced", "on")
                            }

                        })
                }

            })
    }


}
