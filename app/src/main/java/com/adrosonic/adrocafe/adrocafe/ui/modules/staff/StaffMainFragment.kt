package com.adrosonic.adrocafe.adrocafe.ui.modules.staff

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrosonic.adrocafe.adrocafe.AdrocafeApp
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.ChangeStatusEvent
import com.adrosonic.adrocafe.adrocafe.data.MessageEvent
import com.adrosonic.adrocafe.adrocafe.data.Orders
import com.adrosonic.adrocafe.adrocafe.repository.PreferenceHelper
import com.adrosonic.adrocafe.adrocafe.repository.local.AppDatabase
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.OrderViewModel
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_staff_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class StaffMainFragment : Fragment() {

    companion object {
        fun newInstance() = StaffMainFragment()
    }

    private var viewModel: OrderViewModel?= null

    private var staffListAdapter: StaffListAdapter?= null

    private var ordersList = listOf<Orders>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        staffListAdapter = StaffListAdapter(ordersList, PreferenceHelper(context ?: return).getValueString(ConstantsDirectory.PREFS_ACCESS_TOKEN) ?: "")
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(OrderViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_staff_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel?.getOrders()?.observe(this, Observer {ordersList ->
            this.ordersList = ordersList
            staffListAdapter?.swap(ordersList)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        recyclerView_staff.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = staffListAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onChangeStatus(event: ChangeStatusEvent) {
        updateOrderQuantity(event.orders)
    }

    private fun updateOrderQuantity(orders: Orders){
        (activity?.applicationContext as AdrocafeApp).appDatabase
            ?.OrderDao()
            ?.updateStatus(orders.id, orders.status ?: "")
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : CompletableObserver{
                override fun onComplete() {
                    Log.i("updateStatus", "onComplete")
                    staffListAdapter?.swap(ordersList)
                    recyclerView_staff.smoothScrollToPosition(0)
                }

                override fun onSubscribe(d: Disposable) {
                    Log.i("updateStatus", "onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e("updateStatus", e.message)
                }

            })
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
