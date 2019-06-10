package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food

import androidx.recyclerview.widget.DiffUtil
import com.adrosonic.adrocafe.adrocafe.data.Product

class FoodDiffCallback(
    private val oldList: List<Product>,
    private val newList: List<Product>
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
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

}
