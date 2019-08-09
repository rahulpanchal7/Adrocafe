package com.adrosonic.adrocafe.adrocafe.utils

import androidx.databinding.BindingConversion
import com.adrosonic.adrocafe.adrocafe.data.Orders
import com.adrosonic.adrocafe.adrocafe.data.Product

object BindingAdapters {

    @JvmStatic
    @BindingConversion
    fun convertDateTimeToDate(datetimeString: String?): String?{
        return datetimeString?.substringBefore("T")
    }

    @JvmStatic
    @BindingConversion
    fun convertDateTimeToTime(datetimeString: String?): String?{
        val timeString = datetimeString?.substringAfter("T")
        return timeString?.substringBefore(".")
    }

    @JvmStatic
    @BindingConversion
    fun getTotalValue(orders: Orders?): String{
        var amountTotal = 0f
        orders?.orderDetails?.forEach {orderDetails ->
            amountTotal += (orderDetails.amount?.toFloat() ?: 1f) * (orderDetails.quantity?.toFloat() ?: 1f)
        }
        return amountTotal.toString()
    }

    @JvmStatic
    @BindingConversion
    fun getTotalCartItems(products: List<Product>?): String{
        return "( ${products?.size} items in cart )"
    }

    @JvmStatic
    @BindingConversion
    fun getTotalAmount(products: List<Product>?): String{
        var total = 0.0
        products?.forEach {product ->
            total += product.sellingprice * product.ordered_qty
        }
        return total.toString()
    }
}