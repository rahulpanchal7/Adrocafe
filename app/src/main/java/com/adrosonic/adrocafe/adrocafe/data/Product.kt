package com.adrosonic.adrocafe.adrocafe.data

import java.util.*

data class Product(
    val product_id: Int,
    var product_image_url: String,
    var product_brand: String,
    var product_name: String,
    var product_description: String,
    var product_qty: Int,
    var cgst_sgst: Double,
    var mrp: Double,
    var selling_price: Double,
    var purchase_price: Double,
    var date_created: Date,
    var date_modified: Date
){
    constructor() : this(
        -1,
        "",
        "",
        "",
        "",
        -1,
        -1.00,
        -1.00,
        -1.00,
        -1.00,
        Date(),
        Date())
}