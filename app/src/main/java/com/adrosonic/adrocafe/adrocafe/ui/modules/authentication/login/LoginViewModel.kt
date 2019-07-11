package com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.login

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adrosonic.adrocafe.adrocafe.AdrocafeApp
import com.adrosonic.adrocafe.adrocafe.data.SingleLiveEvent
import com.adrosonic.adrocafe.adrocafe.data.User
import com.adrosonic.adrocafe.adrocafe.data.UserLoginModel
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.repository.remote.API
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceHelper = PreferenceHelper(application)

    private val appDatabase = (application.applicationContext as AdrocafeApp).appDatabase

    private val _navigateTo = MutableLiveData<SingleLiveEvent<String>>()

    val navigateTo : LiveData<SingleLiveEvent<String>> = _navigateTo

    var editTextUsername = MutableLiveData<String>()

    var editTextPassword = MutableLiveData<String>()

    val valid = ObservableBoolean(true)

    fun onLoginClick(){
        if (!editTextPassword.value.isNullOrEmpty() || !editTextUsername.value.isNullOrEmpty())
        API
            .login()
            .login(UserLoginModel(editTextUsername.value,editTextPassword.value))
            .enqueue(object: Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    val loginResponse = response.body()
                    loginResponse?.let {user ->
                        user.jwtToken.let {jwt ->
                            preferenceHelper.save(ConstantsDirectory.PREFS_ACCESS_TOKEN, "Bearer $jwt")
                            preferenceHelper.save(ConstantsDirectory.PREFS_USERNAME, editTextUsername.value!!)
                            preferenceHelper.save(ConstantsDirectory.PREFS_PASSWORD, editTextPassword.value!!)
                        }
                        user.isvaliduser.let {isValid ->
                            if (!isValid) {
                                preferenceHelper.save(ConstantsDirectory.IS_LOGGED_IN, true)
                                _navigateTo.value = SingleLiveEvent(ConstantsDirectory.landingactivity)
                            } else {
                                _navigateTo.value = SingleLiveEvent(ConstantsDirectory.resetpasswordfragment)
                            }
                        }
                        appDatabase?.UserDao()?.insert(user)
                            ?.subscribeOn(Schedulers.io())
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe(object : CompletableObserver{
                                override fun onComplete() {
                                    Log.i("insert user", "completed")
                                }

                                override fun onSubscribe(d: Disposable) {
                                    if (d.isDisposed) Log.i("Disposed", "true")
                                }

                                override fun onError(e: Throwable) {
                                    Log.e("insert user", e.message)
                                }

                            }
                            )
                    }
                }
            }
        })
    }

    fun onForgetClick(){
        _navigateTo.value = SingleLiveEvent(ConstantsDirectory.forgetpasswordfragment)
    }
}
