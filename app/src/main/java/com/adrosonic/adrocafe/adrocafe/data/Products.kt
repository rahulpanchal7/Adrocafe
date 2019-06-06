package com.adrosonic.adrocafe.adrocafe.data

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.adrosonic.adrocafe.adrocafe.repository.remote.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Products: BaseObservable() {

    private var status: String ?= null

    private var productList: ArrayList<Product> ?= arrayListOf()

    private var products: MutableLiveData<List<Product>> ?= MutableLiveData()

    fun addProduct(product: Product){
        productList?.add(product)
    }

    fun getProducts(): MutableLiveData<List<Product>>?{
        return products
    }

    fun fetchList() {

        val callback: Callback<Products> = object : Callback<Products>{
            override fun onFailure(call: Call<Products>, t: Throwable) {
                Log.e("Product Fetch",t.message, t)
            }

            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                val body = response.body()
                status = body?.status
                products?.value = body?.productList
            }
        }

        API.product().getALLProduct("").enqueue(callback)
    }
}