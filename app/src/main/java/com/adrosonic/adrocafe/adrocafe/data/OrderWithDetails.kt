package com.adrosonic.adrocafe.adrocafe.data

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithDetails(
    @Embedded
    var order: Orders,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId",
        entity = OrderDetails::class
    )
    var orderDetails: List<OrderDetails>
)