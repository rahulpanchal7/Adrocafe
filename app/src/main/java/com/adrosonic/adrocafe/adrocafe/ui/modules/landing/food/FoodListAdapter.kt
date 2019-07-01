package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.Product
import com.adrosonic.adrocafe.adrocafe.databinding.ItemFoodListBinding
import com.adrosonic.adrocafe.adrocafe.ui.interfaces.AlterCartInterface

class FoodListAdapter(private var products: List<Product>): RecyclerView.Adapter<FoodListAdapter.ViewHolder>(), AlterCartInterface {

    companion object{
        var badgeCount: Int = 0
    }

    override fun onAddItem(view: View, product: Product) {
        badgeCount += 1

    }

    override fun onMinusItem(view: View, product: Product) {
        badgeCount -= 1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemFoodListBinding = DataBindingUtil.inflate(inflater, R.layout.item_food_list, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun swap(products: List<Product>){
        val diffCallback = FoodDiffCallback(this.products, products)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.products = products
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemFoodListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product){
            binding.product = product
            binding.executePendingBindings()
        }
    }

}