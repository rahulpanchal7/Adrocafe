package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adrosonic.adrocafe.adrocafe.AdrocafeApp
import com.adrosonic.adrocafe.adrocafe.data.OrderDetails
import com.adrosonic.adrocafe.adrocafe.data.OrderWithDetails
import com.adrosonic.adrocafe.adrocafe.data.Orders
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

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceHelper = PreferenceHelper(application)
    private val appDatabase = (application.applicationContext as AdrocafeApp).appDatabase

    private val orders: MutableLiveData<List<Orders>> by lazy {
        MutableLiveData<List<Orders>>().also {
            loadOrders()
        }
    }

    private val completedOrders: MutableLiveData<List<Orders>> by lazy {
        MutableLiveData<List<Orders>>().also {
            loadOrders()
        }
    }

    private val inProgressOrders: MutableLiveData<List<Orders>> by lazy {
        MutableLiveData<List<Orders>>().also {
            loadOrders()
        }
    }

    private val cancelledOrders: MutableLiveData<List<Orders>> by lazy {
        MutableLiveData<List<Orders>>().also {
            loadOrders()
        }
    }

    fun getOrders(): LiveData<List<Orders>> {
        return orders
    }

    fun getComplete(): LiveData<List<Orders>>{
        return completedOrders
    }

    fun getInProgress(): LiveData<List<Orders>>{
        return inProgressOrders
    }

    fun getCancelled(): LiveData<List<Orders>>{
        return cancelledOrders
    }

    private fun loadOrders(){
        val jwt = preferenceHelper.getValueString(ConstantsDirectory.PREFS_ACCESS_TOKEN)
        val email = preferenceHelper.getValueString(ConstantsDirectory.PREFS_USERNAME)
        API.order().fetchOrderUser(jwt, email)
            .enqueue(object : Callback<List<Orders>> {
                override fun onFailure(call: Call<List<Orders>>, t: Throwable) {
                    Log.e("Order Fetch", t.localizedMessage)
                    appDatabase?.OrderDao()?.getAll()
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(object : FlowableSubscriber<List<Orders>>{
                            override fun onComplete() {
                                Log.i("getall orders", "onComplete")
                            }

                            override fun onSubscribe(s: Subscription) {
                                Log.i("getall orders", "onSubscribe")
                                s.request(Long.MAX_VALUE)
                            }

                            override fun onNext(t: List<Orders>?) {
                                orders.value = t
                                completedOrders.value = t?.filter { it.status == "Completed" }
                                inProgressOrders.value = t?.filter { it.status == "In Progress" }
                                cancelledOrders.value = t?.filter { it.status == "Cancelled" }
                            }

                            override fun onError(t: Throwable?) {
                                Log.e("getall orders", t?.message)
                            }

                        })
                    appDatabase?.OrderWithDetails()?.loadOrderWithDetails()
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(object : FlowableSubscriber<OrderWithDetails>{
                            override fun onComplete() {
                                Log.i("OrderWithDetails", "Complete")
                            }

                            override fun onSubscribe(s: Subscription) {
                                s.request(Long.MAX_VALUE)
                                Log.i("OrderWithDetails", "Subscribe")
                            }

                            override fun onNext(t: OrderWithDetails?) {
                                Log.i("OrderWithDetails", t?.order?.id)
                            }

                            override fun onError(t: Throwable?) {
                                t?.stackTrace
                            }

                        })
                }

                override fun onResponse(call: Call<List<Orders>>, response: Response<List<Orders>>) {
                    if (response.isSuccessful){
                        orders.value = response.body()
                        completedOrders.value = response.body()?.filter { it.status == "Completed" }
                        inProgressOrders.value = response.body()?.filter { it.status == "In Progress" }
                        cancelledOrders.value = response.body()?.filter { it.status == "Cancelled" }
                        val orderDetailList: MutableList<OrderDetails> ?= mutableListOf()
                        response.body()?.forEach {order ->
                            order.orderDetails?.forEach {orderDetails ->
                                orderDetails.orderId = order.id
                                orderDetailList?.add(orderDetails)
                            }
                        }
                        appDatabase?.OrderDao()?.insertAll(response.body())
                            ?.subscribeOn(Schedulers.io())
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe(object : CompletableObserver {
                                override fun onComplete() {
                                    Log.i("insert orders","complete")
                                }

                                override fun onSubscribe(d: Disposable) {
                                    if (d.isDisposed) Log.i("insert orders", "disposed")
                                }

                                override fun onError(e: Throwable) {
                                    Log.e("insert orders", e.message)
                                }
                            })

                        appDatabase?.OrderDao()?.insertDetails(orderDetailList)
                            ?.subscribeOn(Schedulers.io())
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe(object : CompletableObserver {
                                override fun onComplete() {
                                    Log.i("insert order details","complete")
                                }

                                override fun onSubscribe(d: Disposable) {
                                    if (d.isDisposed) Log.i("insert order details", "disposed")
                                }

                                override fun onError(e: Throwable) {
                                    Log.e("insert order details", e.message)
                                }

                            })

                    } else {
                        appDatabase?.OrderDao()?.getAll()
                            ?.subscribeOn(Schedulers.io())
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe(object : FlowableSubscriber<List<Orders>>{
                                override fun onComplete() {
                                    Log.i("getall orders", "onComplete")
                                }

                                override fun onSubscribe(s: Subscription) {
                                    Log.i("getall orders", "onSubscribe")
                                }

                                override fun onNext(t: List<Orders>?) {
                                    orders.value = t
                                    completedOrders.value = t?.filter { it.status == "Completed" }
                                    inProgressOrders.value = t?.filter { it.status == "In Progress" }
                                    cancelledOrders.value = t?.filter { it.status == "Cancelled" }
                                }

                                override fun onError(t: Throwable?) {
                                    Log.e("getall orders", t?.message)
                                }

                            })
                    }
                }

            })
    }
}
