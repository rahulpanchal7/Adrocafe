package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adrosonic.adrocafe.adrocafe.data.Product
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.repository.remote.API
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceHelper = PreferenceHelper(application)

    private val products: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>().also {
            loadProducts()
        }
    }

    private val snacks: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>().also {
            loadProducts()
        }
    }

    private val beverages: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>().also {
            loadProducts()
        }
    }

    private val others: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>().also {
            loadProducts()
        }
    }

    fun getProducts(): LiveData<List<Product>> {
        return products
    }

    fun getSnacks(): LiveData<List<Product>> {
        return snacks
    }

    fun getBeverages(): LiveData<List<Product>> {
        return beverages
    }

    fun getOthers(): LiveData<List<Product>> {
        return others
    }

    private fun loadProducts(){
        val jwt = preferenceHelper.getValueString(ConstantsDirectory.PREFS_ACCESS_TOKEN)
        API.product().getALLProduct(jwt)
            .enqueue(object : Callback<List<Product>> {
                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    Log.e("Product Fetch", t.localizedMessage)
                }

                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    if (response.isSuccessful){
                        products.value = response.body()
                        snacks.value = response.body()?.filter { it.product_type == 2 }
                        beverages.value = response.body()?.filter { it.product_type == 1 }
                        others.value = response.body()?.filter { it.product_type == 3 }
                    }
                }

            })
    }

}
