package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.Orders
import com.adrosonic.adrocafe.adrocafe.databinding.ItemOrderListBinding
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory

class OrderListAdapter(ordersList: List<Orders>, orderType: String): RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    var ordersList = when (orderType){
        ConstantsDirectory.all -> ordersList
        ConstantsDirectory.completed -> ordersList.filter { it.status == ConstantsDirectory.completed }
        ConstantsDirectory.inprogress -> ordersList.filter { it.status == ConstantsDirectory.inprogress }
        else -> ordersList.filter { it.status == ConstantsDirectory.cancelled }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemOrderListBinding = DataBindingUtil.inflate(inflater, R.layout.item_order_list, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ordersList[position])
    }

    fun swap(ordersList: List<Orders>, orderType: String){
        val diffCallback = OrderDiffCallback(this.ordersList, ordersList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.ordersList = when (orderType){
            ConstantsDirectory.all -> ordersList
            ConstantsDirectory.completed -> ordersList.filter { it.status == ConstantsDirectory.completed }
            ConstantsDirectory.inprogress -> ordersList.filter { it.status == ConstantsDirectory.inprogress }
            else -> ordersList.filter { it.status == ConstantsDirectory.cancelled }
        }
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemOrderListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(orders: Orders){
            binding.order = orders
            binding.executePendingBindings()
        }
    }

}