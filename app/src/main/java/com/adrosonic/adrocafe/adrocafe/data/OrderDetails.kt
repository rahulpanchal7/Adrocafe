package com.adrosonic.adrocafe.adrocafe.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(entity = Orders::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("orderId"),
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE)))
data class OrderDetails(
    var amount: String?,
    var quantity: String?,
    var productId: String?,
    var datemodified: String?,
    @PrimaryKey
    var id: String,
    var datecreated: String?,
    var orderId: String?
)