package com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.snacks

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.adrosonic.adrocafe.adrocafe.R

class SnacksFoodFragment : Fragment() {

    companion object {
        fun newInstance() = SnacksFoodFragment()
    }

    private lateinit var viewModel: SnacksFoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_snacks_food, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SnacksFoodViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
