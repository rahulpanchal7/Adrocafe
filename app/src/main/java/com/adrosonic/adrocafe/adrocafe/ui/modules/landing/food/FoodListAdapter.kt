package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.MessageEvent
import com.adrosonic.adrocafe.adrocafe.data.Product
import com.adrosonic.adrocafe.adrocafe.databinding.ItemFoodListBinding
import com.adrosonic.adrocafe.adrocafe.ui.interfaces.AlterCartInterface
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import org.greenrobot.eventbus.EventBus

class FoodListAdapter(products: List<Product>, productType: String): RecyclerView.Adapter<FoodListAdapter.ViewHolder>(), AlterCartInterface {

    companion object {
        var badgeCount: Int = 0
    }

    var products = when (productType) {
        ConstantsDirectory.all -> products
        ConstantsDirectory.beverages -> products.filter { it.product_type == 1 }
        ConstantsDirectory.snacks -> products.filter { it.product_type == 2 }
        else -> products.filter { it.product_type == 3 }
    }

    override fun onAddItem(product: Product) {
        badgeCount += 1
        product.plusminusqty += 1
        alterBadgeCount(badgeCount)
    }

    override fun onMinusItem(product: Product) {
        if (badgeCount > 0){
            badgeCount -= 1
            if (product.plusminusqty > 0){
                product.plusminusqty -= 1
            }
            alterBadgeCount(badgeCount)
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
        val diffCallback = FoodDiffCallback(this.products, products)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.products = when (productType) {
            ConstantsDirectory.all -> products
            ConstantsDirectory.beverages -> products.filter { it.product_type == 1 }
            ConstantsDirectory.snacks -> products.filter { it.product_type == 2 }
            else -> products.filter { it.product_type == 3 }
        }
        diffResult.dispatchUpdatesTo(this)
    }

    private fun alterBadgeCount(count: Int) {
        EventBus.getDefault().post(MessageEvent(count.toString()))
    }

    inner class ViewHolder(private val binding: ItemFoodListBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product
            binding.event = this@FoodListAdapter
            binding.executePendingBindings()
        }
    }

}