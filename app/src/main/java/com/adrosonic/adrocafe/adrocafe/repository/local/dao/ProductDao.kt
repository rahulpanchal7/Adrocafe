package com.adrosonic.adrocafe.adrocafe.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adrosonic.adrocafe.adrocafe.data.Product
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAll(): Flowable<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>?): Completable
}