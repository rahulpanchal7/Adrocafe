package com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.login

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adrosonic.adrocafe.adrocafe.data.Event
import com.adrosonic.adrocafe.adrocafe.data.UserLoginModel
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.repository.remote.API
import com.adrosonic.adrocafe.adrocafe.repository.remote.response.LoginResponse
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceHelper = PreferenceHelper(application)

    private val _navigateTo = MutableLiveData<Event<String>>()

    @get: Bindable
    val navigateTo : LiveData<Event<String>> = _navigateTo

    @Bindable
    var editTextUsername = MutableLiveData<String>()

    @Bindable
    var editTextPassword = MutableLiveData<String>()

    @get: Bindable
    val valid = ObservableBoolean(true)

    fun onLoginClick(){
        if (!editTextPassword.value.isNullOrEmpty() || !editTextUsername.value.isNullOrEmpty())
        API
            .login()
            .login(UserLoginModel(editTextUsername.value,editTextPassword.value))
            .enqueue(object: Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    val loginResponse = response.body()
                    loginResponse?.let {
                        it.jwtToken?.let {jwt ->
                            preferenceHelper.save(ConstantsDirectory.PREFS_ACCESS_TOKEN, jwt)
                            preferenceHelper.save(ConstantsDirectory.PREFS_USERNAME, editTextUsername.value!!)
                            preferenceHelper.save(ConstantsDirectory.PREFS_PASSWORD, editTextPassword.value!!)
                        } ?: Log.i("Access Token","Not Found")
                        it.isvaliduser?.let {
                            if (it){
                                preferenceHelper.save(ConstantsDirectory.IS_LOGGED_IN, true)
                                _navigateTo.value = Event(ConstantsDirectory.landingactivity)
                            } else {
                                _navigateTo.value = Event(ConstantsDirectory.resetpasswordfragment)
                            }
                        }
                    }
                }
            }
        })
    }

    fun onForgetClick(){
        _navigateTo.value = Event(ConstantsDirectory.forgetpasswordfragment)
    }
}
