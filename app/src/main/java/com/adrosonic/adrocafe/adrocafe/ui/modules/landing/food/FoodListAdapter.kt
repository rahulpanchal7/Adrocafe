package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.Product
import com.adrosonic.adrocafe.adrocafe.databinding.ItemFoodListBinding

class FoodListAdapter(private var products: List<Product>): RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {

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

    inner class ViewHolder(private val binding: ItemFoodListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product){
            binding.executePendingBindings()
        }
    }

}