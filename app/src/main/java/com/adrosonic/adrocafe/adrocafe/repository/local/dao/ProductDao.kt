package com.adrosonic.adrocafe.adrocafe.repository.local.dao

import androidx.room.*
import com.adrosonic.adrocafe.adrocafe.data.Product
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAll(): Flowable<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(products: List<Product>?): Completable

    @Query("UPDATE product SET ordered_qty =:ordered_qty WHERE id =:id")
    fun updateById(id: Int, ordered_qty: Int): Int

    @Query("UPDATE product SET ordered_qty = 0")
    fun updateOrderPlaced(): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(product: Product): Completable

    @Query("SELECT SUM(ordered_qty) FROM product")
    fun getTotalBadgeCount(): Single<Int>

    @Query("SELECT * FROM product WHERE ordered_qty > 0")
    fun getOrderedProduct(): Flowable<List<Product>>
}