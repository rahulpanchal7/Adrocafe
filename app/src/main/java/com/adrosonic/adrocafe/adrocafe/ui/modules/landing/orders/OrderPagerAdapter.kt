package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.all.AllOrdersFragment
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.cancelled.CancelledOrdersFragment
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.inprogress.InProgressOrdersFragment
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.orders.completed.CompletedOrdersFragment

class OrderPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                AllOrdersFragment()
            }
            1 -> {
                CompletedOrdersFragment()
            }
            2 -> {
                InProgressOrdersFragment()
            }
            else -> {
                CancelledOrdersFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "All"
            }
            1 -> {
                "Completed"
            }
            2 -> {
                "In Progress"
            }
            else -> {
                "Cancelled"
            }
        }
    }

}