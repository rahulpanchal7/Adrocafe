package com.adrosonic.adrocafe.adrocafe.data

data class MessageEvent(var message: String)

data class AlterBadgeEvent(var product: Product, var toAdd: Boolean)

data class ChangeStatusEvent(var orders: Orders)
