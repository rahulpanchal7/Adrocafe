package com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.reset

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adrosonic.adrocafe.adrocafe.data.Event
import com.adrosonic.adrocafe.adrocafe.data.UserResetModel
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.repository.remote.API
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceHelper = PreferenceHelper(application)

    private val _navigateTo = MutableLiveData<Event<String>>()

    @get: Bindable
    val navigateTo : LiveData<Event<String>> = _navigateTo

    @Bindable
    var editTextNewPassword = MutableLiveData<String>()

    @Bindable
    var editTextConfirmPassword = MutableLiveData<String>()

    @get: Bindable
    val isResetEnable = ObservableBoolean(false)

    fun onResetClick(newpassword: String?){
        newpassword?.let {newpassword ->
            val jwt = preferenceHelper.getValueString(ConstantsDirectory.PREFS_ACCESS_TOKEN)
            val username = preferenceHelper.getValueString(ConstantsDirectory.PREFS_USERNAME).let { it } ?: return
            val password = preferenceHelper.getValueString(ConstantsDirectory.PREFS_PASSWORD).let { it } ?: return
            jwt?.let {token ->
                API
                    .login()
                    .resetPassword(UserResetModel(username, password, newpassword), token)
                    .enqueue(object : Callback<String>{
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            t.printStackTrace()
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful){
                                preferenceHelper.save(ConstantsDirectory.IS_LOGGED_IN, true)
                                _navigateTo.value = Event(ConstantsDirectory.landingactivity)
                            }
                        }
                    })
            }
        }

    }
}
