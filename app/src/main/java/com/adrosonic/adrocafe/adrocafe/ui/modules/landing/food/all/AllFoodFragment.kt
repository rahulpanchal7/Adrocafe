package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.all

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.databinding.FragmentAllFoodBinding

class AllFoodFragment : Fragment() {

    companion object {
        fun newInstance() = AllFoodFragment()
    }

    private var viewModel: AllFoodViewModel ?= null
    private var binding: FragmentAllFoodBinding ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_food, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllFoodViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
