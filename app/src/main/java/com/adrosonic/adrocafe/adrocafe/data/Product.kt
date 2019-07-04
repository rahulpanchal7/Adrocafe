package com.adrosonic.adrocafe.adrocafe.data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.adrosonic.adrocafe.adrocafe.BR
import com.google.gson.annotations.SerializedName

@Entity
data class Product(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var quantity: Int,
    var sellingprice: Double,
    var name: String,
    @SerializedName("producttype")
    var product_type: Int,
    @Ignore
    var ordered_qty: Int
) : BaseObservable() {
    constructor(id: Int, quantity: Int, sellingprice: Double, name: String, product_type: Int): this(id, quantity, sellingprice, name, product_type, 0)

    var plusminusqty: Int
    @Bindable get() = ordered_qty
    set(value) {
        ordered_qty = value
        notifyPropertyChanged(BR.plusminusqty)
    }
}