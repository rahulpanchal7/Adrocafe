package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.others

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.Product
import com.adrosonic.adrocafe.adrocafe.databinding.FragmentOtherFoodBinding
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.FoodListAdapter
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.FoodViewModel

class OtherFoodFragment : Fragment() {

    companion object {
        fun newInstance() = OtherFoodFragment()
    }

    private var viewModel: FoodViewModel?= null
    private var binding: FragmentOtherFoodBinding ?= null
    private var foodListAdapter: FoodListAdapter?= null
    private var products = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodListAdapter = FoodListAdapter(products)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_other_food, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FoodViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel?.getOthers()?.observe(this, Observer {products ->
            foodListAdapter?.swap(products)
        })
    }

}
