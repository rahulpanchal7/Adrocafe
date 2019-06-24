package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.adrosonic.adrocafe.adrocafe.R
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment : Fragment() {

    companion object {
        fun newInstance() = OrderFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        childFragmentManager.let {
            viewPager_order.adapter = OrderPagerAdapter(it)
            tabLayout_order.setupWithViewPager(viewPager_order)
        }

    }

}
