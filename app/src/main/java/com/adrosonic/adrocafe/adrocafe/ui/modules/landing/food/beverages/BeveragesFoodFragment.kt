package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.beverages

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.Product
import com.adrosonic.adrocafe.adrocafe.databinding.FragmentBeveragesFoodBinding
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.FoodListAdapter
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.FoodViewModel
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import kotlinx.android.synthetic.main.fragment_beverages_food.*

class BeveragesFoodFragment : Fragment() {

    companion object {
        fun newInstance() = BeveragesFoodFragment()
    }

    private var viewModel: FoodViewModel ?= null
    private var binding: FragmentBeveragesFoodBinding ?= null
    private var foodListAdapter: FoodListAdapter?= null
    private var products = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodListAdapter = FoodListAdapter(products, ConstantsDirectory.beverages)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(FoodViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beverages_food, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel?.getProducts()?.observe(this, Observer {products ->
            foodListAdapter?.swap(products, ConstantsDirectory.beverages)
        })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView(){

        recyclerView_beverages.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = foodListAdapter
        }
    }

}
