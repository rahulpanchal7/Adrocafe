package com.adrosonic.adrocafe.adrocafe.ui.interfaces

import android.view.View
import com.adrosonic.adrocafe.adrocafe.data.Product

interface AlterCartInterface {

    fun onAddItem(view: View, product: Product)

    fun onMinusItem(view: View, product: Product)
}