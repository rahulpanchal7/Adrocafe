package com.adrosonic.adrocafe.adrocafe.repository.remote

import com.adrosonic.adrocafe.adrocafe.repository.remote.dao.LoginDao
import com.adrosonic.adrocafe.adrocafe.repository.remote.dao.OrdersDao
import com.adrosonic.adrocafe.adrocafe.repository.remote.dao.ProductsDao
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    private fun <T> builder(endpoint: Class<T>): T {

        return Retrofit.Builder()
            .baseUrl(ConstantsDirectory.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .build()
            .create(endpoint)
    }

    fun product(): ProductsDao {
        return builder(ProductsDao::class.java)
    }

    fun login(): LoginDao {
        return builder(LoginDao::class.java)
    }

    fun order(): OrdersDao {
        return builder(OrdersDao::class.java)
    }

}