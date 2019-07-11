package com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.reset

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adrosonic.adrocafe.adrocafe.data.SingleLiveEvent
import com.adrosonic.adrocafe.adrocafe.data.UserResetModel
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.repository.remote.API
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceHelper = PreferenceHelper(application)

    private val _navigateTo = MutableLiveData<SingleLiveEvent<String>>()

    val navigateTo : LiveData<SingleLiveEvent<String>> = _navigateTo

    var editTextNewPassword = MutableLiveData<String>()

    var editTextConfirmPassword = MutableLiveData<String>()

    val isResetEnable = ObservableBoolean(false)

    fun onResetClick(newpassword: String?){
        newpassword?.let {newpassword ->
            val jwt = preferenceHelper.getValueString(ConstantsDirectory.PREFS_ACCESS_TOKEN)
            val username = preferenceHelper.getValueString(ConstantsDirectory.PREFS_USERNAME).let { it } ?: return
            val password = preferenceHelper.getValueString(ConstantsDirectory.PREFS_PASSWORD).let { it } ?: return
            jwt?.let {token ->
                API
                    .login()
                    .validateUser(UserResetModel(username, password, newpassword))
                    .enqueue(object : Callback<String>{
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            t.printStackTrace()
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful){
                                preferenceHelper.save(ConstantsDirectory.IS_LOGGED_IN, true)
                                _navigateTo.value = SingleLiveEvent(ConstantsDirectory.landingactivity)
                            }
                        }
                    })
            }
        }

    }
}
