package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adrosonic.adrocafe.adrocafe.AdrocafeApp
import com.adrosonic.adrocafe.adrocafe.data.Product
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.repository.remote.API
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.FlowableSubscriber
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.notifyAll
import org.reactivestreams.Subscription
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceHelper = PreferenceHelper(application)

    private val appDatabase = (application.applicationContext as AdrocafeApp).appDatabase

    private val products: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>().also {
            loadProducts()
        }
    }

    fun getProducts(): LiveData<List<Product>> {
        return products
    }

    private fun loadProducts(){
        val jwt = preferenceHelper.getValueString(ConstantsDirectory.PREFS_ACCESS_TOKEN)
        API.product().getALLProduct(jwt)
            .enqueue(object : Callback<List<Product>> {

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    Log.e("Product Fetch", t.localizedMessage)
                    appDatabase?.ProductDao()?.getAll()
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(object : FlowableSubscriber<List<Product>>{
                            override fun onComplete() {
                                Log.i("getall product", "onComplete")
                            }

                            override fun onSubscribe(s: Subscription) {
                                Log.i("getall product", "onSubscribe")
                                s.request(Long.MAX_VALUE)
                            }

                            override fun onNext(t: List<Product>?) {
                                products.value = t
                            }

                            override fun onError(t: Throwable?) {
                                Log.e("getall product", t?.message)
                            }

                        })
                }

                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    if (response.isSuccessful) {
                        appDatabase?.ProductDao()?.insertAll(response.body())
                            ?.subscribeOn(Schedulers.io())
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe(object : CompletableObserver{
                                override fun onComplete() {
                                    Log.i("insert product", "Complete")
                                    appDatabase.ProductDao().getAll()
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        ?.subscribe(object : FlowableSubscriber<List<Product>>{
                                            override fun onComplete() {
                                                Log.i("getall product", "onComplete")
                                            }

                                            override fun onSubscribe(s: Subscription) {
                                                Log.i("getall product", "onSubscribe")
                                                s.request(Long.MAX_VALUE)
                                            }

                                            override fun onNext(t: List<Product>?) {
                                                products.value = t
                                            }

                                            override fun onError(t: Throwable?) {
                                                Log.e("getall product", t?.message)
                                            }

                                        })
                                }

                                override fun onSubscribe(d: Disposable) {
                                    if (d.isDisposed) Log.i("Disposed", "True")
                                }

                                override fun onError(e: Throwable) {
                                    Log.e("insert product",e.message)
                                }
                            })
                    } else {
                        appDatabase?.ProductDao()?.getAll()
                            ?.subscribeOn(Schedulers.io())
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe(object : FlowableSubscriber<List<Product>>{
                                override fun onComplete() {
                                    Log.i("getall product", "onComplete")
                                }

                                override fun onSubscribe(s: Subscription) {
                                    Log.i("getall product", "onSubscribe")
                                }

                                override fun onNext(t: List<Product>?) {
                                    products.value = t
                                }

                                override fun onError(t: Throwable?) {
                                    Log.e("getall product", t?.message)
                                }

                            })
                    }
                }

            })
    }

}
