package com.adrosonic.adrocafe.adrocafe.repository.remote.dao

import com.adrosonic.adrocafe.adrocafe.data.Orders
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface OrdersDao {

    @Headers("Accept: application/json")
    @GET("order/getUserOrders")
    fun fetchOrderUser(@Header("Authorization")token: String?,
                       @Query("email") emailId:String?): Call<List<Orders>>

}