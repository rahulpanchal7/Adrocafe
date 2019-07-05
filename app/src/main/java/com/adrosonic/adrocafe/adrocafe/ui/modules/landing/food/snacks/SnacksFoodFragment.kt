package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.snacks

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
import com.adrosonic.adrocafe.adrocafe.databinding.FragmentSnacksFoodBinding
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.FoodListAdapter
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.FoodViewModel
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import kotlinx.android.synthetic.main.fragment_snacks_food.*

class SnacksFoodFragment : Fragment() {

    companion object {
        fun newInstance() = SnacksFoodFragment()
    }

    private var viewModel: FoodViewModel ?= null
    private var binding: FragmentSnacksFoodBinding ?= null
    private var foodListAdapter: FoodListAdapter?= null
    private var products = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodListAdapter = FoodListAdapter(products, ConstantsDirectory.snacks)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(FoodViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_snacks_food, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel?.getProducts()?.observe(this, Observer {products ->
            foodListAdapter?.swap(products, ConstantsDirectory.snacks)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView(){

        recyclerView_snacks.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = foodListAdapter
        }
    }
}
