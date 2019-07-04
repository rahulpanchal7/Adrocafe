package com.adrosonic.adrocafe.adrocafe.ui.interfaces

import com.adrosonic.adrocafe.adrocafe.data.Product

interface AlterCartInterface {

    fun onAddItem(product: Product)

    fun onMinusItem(product: Product)
}