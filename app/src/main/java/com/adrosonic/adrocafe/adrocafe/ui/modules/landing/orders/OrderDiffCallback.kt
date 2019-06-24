package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders

import androidx.recyclerview.widget.DiffUtil
import com.adrosonic.adrocafe.adrocafe.data.Orders

class OrderDiffCallback(
    private val oldList: List<Orders>,
    private val newList: List<Orders>
): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].status == newList[newItemPosition].status
    }

}