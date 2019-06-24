package com.adrosonic.adrocafe.adrocafe

import android.app.Application
import com.adrosonic.adrocafe.adrocafe.repository.local.AppDatabase

class AdrocafeApp: Application() {

    var appDatabase: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabase(this)
    }
}