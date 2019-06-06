package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.beverages

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.adrosonic.adrocafe.adrocafe.R

class BeveragesFoodFragment : Fragment() {

    companion object {
        fun newInstance() = BeveragesFoodFragment()
    }

    private lateinit var viewModel: BeveragesFoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beverages_food, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BeveragesFoodViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
