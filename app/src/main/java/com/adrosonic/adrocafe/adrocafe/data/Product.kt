package com.adrosonic.adrocafe.adrocafe.data

import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int,
    var quantity: Int,
    var sellingprice: Double,
    var name: String,
    @SerializedName("producttype")
    var product_type: Int
)