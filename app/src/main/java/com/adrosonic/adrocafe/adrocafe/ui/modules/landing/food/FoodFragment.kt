package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.Product
import kotlinx.android.synthetic.main.fragment_all_food.*
import kotlinx.android.synthetic.main.fragment_food.*

class FoodFragment : Fragment() {

    companion object {
        fun newInstance() = FoodFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        childFragmentManager.let {
            viewPager_food.adapter = FoodPagerAdapter(it)
            tabLayout_food.setupWithViewPager(viewPager_food)
        }
    }



}
