package com.adrosonic.adrocafe.adrocafe.repository.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.adrosonic.adrocafe.adrocafe.data.OrderWithDetails
import io.reactivex.Flowable

@Dao
interface OrderWithDetailsDao {

    @Transaction
    @Query("SELECT * FROM orders")
    fun loadOrderWithDetails(): Flowable<OrderWithDetails>
}