package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.completed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.Orders
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.OrderListAdapter
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.OrderViewModel
import kotlinx.android.synthetic.main.fragment_all_orders.*
import kotlinx.android.synthetic.main.fragment_completed_orders.*

class CompletedOrdersFragment : Fragment() {

    private var viewModel: OrderViewModel?= null
    private var orderListAdapter: OrderListAdapter?= null
    private var ordersList = mutableListOf<Orders>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderListAdapter = OrderListAdapter(ordersList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed_orders, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)

        viewModel?.getComplete()?.observe(this, Observer {ordersList ->
            orderListAdapter?.swap(ordersList)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView(){

        recyclerView_completed.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = orderListAdapter
        }
    }
}
