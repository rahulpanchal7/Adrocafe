package com.adrosonic.adrocafe.adrocafe.repository.remote.dao

import com.adrosonic.adrocafe.adrocafe.data.Orders
import retrofit2.Call
import retrofit2.http.*

interface OrdersDao {

    @Headers("Accept: application/json")
    @GET("order/getUserOrders")
    fun fetchOrderUser(@Header("Authorization")token: String?,
                       @Query("email") emailId:String?): Call<List<Orders>>

    @Headers("Accept: application/json")
    @POST("order/createOrder")
    fun createOrder(@Body orders: Orders,
                    @Header("Authorization")token: String?): Call<Orders>

    @GET("order/delivered")
    fun deliverOrder(@Query("id") id: Int,
                     @Header("Authorization")token: String?): Call<Boolean>

    @GET("order/cancelOrder")
    fun cancelOrder(@Query("id") id: Int,
                    @Header("Authorization")token: String?): Call<Boolean>
}