package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.adrosonic.adrocafe.adrocafe.R
import kotlinx.android.synthetic.main.fragment_food.*

class FoodFragment : Fragment() {

    companion object {
        fun newInstance() = FoodFragment()
    }

    private var viewModel: FoodViewModel ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FoodViewModel::class.java)
        // TODO: Use the ViewModel
        fragmentManager?.let {
            viewPager_food.adapter = FoodPagerAdapter(it)
            tabLayout_food.setupWithViewPager(viewPager_food)
        }
    }

}
