package com.adrosonic.adrocafe.adrocafe.repository

import android.content.Context
import android.content.SharedPreferences
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory

class PreferenceHelper(val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(ConstantsDirectory.PREFS_NAME, Context.MODE_PRIVATE)

    fun save(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun save(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getValueString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun getValueBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearSharedPreference() {
        sharedPreferences.edit().clear().apply()
    }

    fun removeValue(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}