package com.adrosonic.adrocafe.adrocafe.ui.interfaces

import com.adrosonic.adrocafe.adrocafe.data.Orders


interface OrderAcceptRejectInterface {

    fun cancelOrder(orders: Orders)

    fun deliverOrder(orders: Orders)
}