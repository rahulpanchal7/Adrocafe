package com.adrosonic.adrocafe.adrocafe.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Product(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var quantity: Int,
    var sellingprice: Double,
    var name: String,
    @SerializedName("producttype")
    var product_type: Int
)