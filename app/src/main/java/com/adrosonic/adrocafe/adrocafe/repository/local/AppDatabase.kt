package com.adrosonic.adrocafe.adrocafe.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adrosonic.adrocafe.adrocafe.data.OrderDetails
import com.adrosonic.adrocafe.adrocafe.data.Orders
import com.adrosonic.adrocafe.adrocafe.data.Product
import com.adrosonic.adrocafe.adrocafe.data.User
import com.adrosonic.adrocafe.adrocafe.repository.local.dao.OrderDao
import com.adrosonic.adrocafe.adrocafe.repository.local.dao.ProductDao
import com.adrosonic.adrocafe.adrocafe.repository.local.dao.UserDao

@Database(
    entities = [User::class, Product::class, Orders::class, OrderDetails::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase(){

    abstract fun UserDao(): UserDao
    abstract fun ProductDao(): ProductDao
    abstract fun OrderDao(): OrderDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "adrocafe.db").build()
    }

}