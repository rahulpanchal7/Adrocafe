package com.adrosonic.adrocafe.adrocafe.ui.modules.staff

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.ChangeStatusEvent
import com.adrosonic.adrocafe.adrocafe.data.Orders
import com.adrosonic.adrocafe.adrocafe.databinding.ItemStaffListBinding
import com.adrosonic.adrocafe.adrocafe.repository.remote.API
import com.adrosonic.adrocafe.adrocafe.ui.interfaces.OrderAcceptRejectInterface
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.OrderDiffCallback
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StaffListAdapter(ordersList: List<Orders>, private val token: String): RecyclerView.Adapter<StaffListAdapter.ViewHolder>(), OrderAcceptRejectInterface {

    var orders = ordersList.sortedWith(compareBy( { it.status }, {it.datecreated})).reversed()

    override fun cancelOrder(orders: Orders) {

        API.order().cancelOrder(orders.id.toInt(), token).enqueue(object : Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("canceledOrder",t.message)
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.body() == true) {
                    orders.isProgress = false
                    orders.newStatus = ConstantsDirectory.cancelled
                    changeOrderStatus(orders)
                }
            }
        })
    }

    override fun deliverOrder(orders: Orders) {
        API.order().deliverOrder(orders.id.toInt(), token).enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("deliverOrder",t.message)
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.body() == true) {
                    orders.isProgress = false
                    orders.newStatus = ConstantsDirectory.delivered
                    changeOrderStatus(orders)
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemStaffListBinding = DataBindingUtil.inflate(inflater, R.layout.item_staff_list, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = orders.size

    private fun changeOrderStatus(orders: Orders){
        EventBus.getDefault().post(ChangeStatusEvent(orders))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    fun swap(ordersList: List<Orders>){
        val orders = ordersList.sortedWith(compareBy( { it.status }, {it.datecreated})).reversed()
        val diffCallback = OrderDiffCallback(this.orders, orders)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.orders = orders
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemStaffListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(orders: Orders) {
            binding.order = orders
            binding.event = this@StaffListAdapter
            binding.executePendingBindings()
        }
    }
}