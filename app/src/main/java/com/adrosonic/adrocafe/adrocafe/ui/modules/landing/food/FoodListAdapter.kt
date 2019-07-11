package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.AlterBadgeEvent
import com.adrosonic.adrocafe.adrocafe.data.Product
import com.adrosonic.adrocafe.adrocafe.databinding.ItemFoodListBinding
import com.adrosonic.adrocafe.adrocafe.ui.interfaces.AlterCartInterface
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import org.greenrobot.eventbus.EventBus

class FoodListAdapter(products: List<Product>, productType: String): RecyclerView.Adapter<FoodListAdapter.ViewHolder>(), AlterCartInterface {

    var products = when (productType) {
        ConstantsDirectory.all -> products
        ConstantsDirectory.beverages -> products.filter { it.product_type == 1 }
        ConstantsDirectory.snacks -> products.filter { it.product_type == 2 }
        else -> products.filter { it.product_type == 3 }
    }

    override fun onAddItem(product: Product) {
        product.plusminusqty += 1
        product.ordered_qty = product.plusminusqty
        alterBadgeCount(product, true)
    }

    override fun onMinusItem(product: Product) {
        if (product.plusminusqty > 0){
            product.plusminusqty -= 1
            product.ordered_qty = product.plusminusqty
            alterBadgeCount(product, false)
        }
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

    fun swap(products: List<Product>, productType: String) {
        val newProducts = when (productType) {
            ConstantsDirectory.all -> products
            ConstantsDirectory.beverages -> products.filter { it.product_type == 1 }
            ConstantsDirectory.snacks -> products.filter { it.product_type == 2 }
            else -> products.filter { it.product_type == 3 }
        }
        val diffCallback = FoodDiffCallback(this.products, newProducts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.products = newProducts
        diffResult.dispatchUpdatesTo(this)
    }

    private fun alterBadgeCount(product: Product, doAdd: Boolean) {
        EventBus.getDefault().post(AlterBadgeEvent(product, doAdd))
    }

    inner class ViewHolder(private val binding: ItemFoodListBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product
            binding.event = this@FoodListAdapter
            binding.executePendingBindings()
        }
    }

}