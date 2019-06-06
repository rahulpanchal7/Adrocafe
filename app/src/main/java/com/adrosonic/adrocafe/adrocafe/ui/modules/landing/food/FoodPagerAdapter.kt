package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.all.AllFoodFragment
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.beverages.BeveragesFoodFragment
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.others.OtherFoodFragment
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.snacks.SnacksFoodFragment

class FoodPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                AllFoodFragment()
            }
            1 -> {
                SnacksFoodFragment()
            }
            2 -> {
                BeveragesFoodFragment()
            }
            else -> {
                OtherFoodFragment()
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
                "Snacks"
            }
            2 -> {
                "Beverages"
            }
            else -> {
                "Other"
            }
        }
    }
}