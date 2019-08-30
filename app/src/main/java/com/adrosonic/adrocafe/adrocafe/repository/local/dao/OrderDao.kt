package com.adrosonic.adrocafe.adrocafe.repository.local.dao

import androidx.room.*
import com.adrosonic.adrocafe.adrocafe.data.OrderDetails
import com.adrosonic.adrocafe.adrocafe.data.Orders
import com.adrosonic.adrocafe.adrocafe.repository.local.AppDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.DELETE

@Dao
interface OrderDao {

    @Query("SELECT * FROM orders")
    fun getAll(): Flowable<List<Orders>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ordersList: List<Orders>?): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetails(orderDetails: List<OrderDetails>?): Completable

    @Query("UPDATE orders SET status =:status WHERE id=:id")
    fun updateStatus(id: String, status: String): Completable

    @Query("DELETE FROM orders")
    fun nukeOrders() : Completable

}