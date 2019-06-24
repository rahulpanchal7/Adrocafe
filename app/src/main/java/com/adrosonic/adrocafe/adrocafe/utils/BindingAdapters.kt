package com.adrosonic.adrocafe.adrocafe.utils

import androidx.databinding.BindingConversion
import com.adrosonic.adrocafe.adrocafe.data.Orders

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
            orderDetails.amount?.let {amount ->
                amountTotal += amount.toFloat()
            }
        }
        return amountTotal.toString()
    }
}