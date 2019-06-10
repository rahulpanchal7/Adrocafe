package com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.MessageEvent
import com.adrosonic.adrocafe.adrocafe.databinding.FragmentLoginBinding
import com.adrosonic.adrocafe.adrocafe.ui.modules.landing.food.FoodListAdapter
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import kotlinx.android.synthetic.main.fragment_login.*
import org.greenrobot.eventbus.EventBus

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private var viewModel: LoginViewModel ?= null
    private var binding: FragmentLoginBinding ?= null
    private var foodListAdapter: FoodListAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
        binding?.model = viewModel

        viewModel?.navigateTo?.observe(this, Observer {event ->
            event.getContentIfNotHandled()?.let {
                EventBus.getDefault().post(MessageEvent(it))
            }
        })
    }

}
