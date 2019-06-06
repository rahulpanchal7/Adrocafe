package com.adrosonic.adrocafe.adrocafe.repository.remote.dao

import com.adrosonic.adrocafe.adrocafe.data.Products
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ProductsDao {

    @Headers("Accept: application/json")
    @GET("product/getall")
    fun getALLProduct(@Header("Authorization") token:String?): Call<Products>
}