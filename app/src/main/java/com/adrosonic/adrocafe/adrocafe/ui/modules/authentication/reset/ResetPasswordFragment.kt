package com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.reset

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
import com.adrosonic.adrocafe.adrocafe.databinding.FragmentResetPasswordBinding
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import kotlinx.android.synthetic.main.fragment_reset_password.*
import org.greenrobot.eventbus.EventBus

class ResetPasswordFragment : Fragment() {

    companion object {
        fun newInstance(message: String) = ResetPasswordFragment()
    }

    private var viewModel: ResetPasswordViewModel ?= null
    private var binding: FragmentResetPasswordBinding ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel::class.java)
        // TODO: Use the ViewModel
        binding?.model = viewModel
        viewModel?.editTextConfirmPassword?.observe(this, Observer {confirm_password ->
            viewModel?.editTextNewPassword?.value?.let {new_password ->
                if (confirm_password == new_password)
                    viewModel?.isResetEnable?.set(true)
                else
                    viewModel?.isResetEnable?.set(false)
            }
        })
        viewModel?.navigateTo?.observe(this, Observer {event ->
            event.getContentIfNotHandled()?.let {
                EventBus.getDefault().post(MessageEvent(it))
            }
        })
    }

}
