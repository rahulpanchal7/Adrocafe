package com.adrosonic.adrocafe.adrocafe.repository.remote.dao

import com.adrosonic.adrocafe.adrocafe.data.UserLoginModel
import com.adrosonic.adrocafe.adrocafe.data.UserResetModel
import com.adrosonic.adrocafe.adrocafe.repository.remote.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginDao {

    @POST("auth/signin")
    fun login(@Body userLoginModel: UserLoginModel): Call<LoginResponse>

    @POST("auth/validateuser")
    fun validateUser(@Body userResetModel: UserResetModel): Call<String>

    @POST("auth/resetpassword")
    fun resetPassword(@Body userResetModel: UserResetModel, @Header("Authorization")token: String): Call<String>

    @GET("auth/signout")
    fun logout(): Call<Void>
}