package com.adrosonic.adrocafe.adrocafe.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Orders(
    @Ignore
    var orderDetails: Array<OrderDetails>?,
    var datemodified: String?,
    var datecreated: String?,
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @Embedded(prefix = "user_")
    var user: User?,
    var status: String?
){
    constructor(id: String, datemodified: String?, datecreated: String?, status: String?): this( arrayOf<OrderDetails>(), datemodified, datecreated, id, User(), status)
}

