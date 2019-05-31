package com.adrosonic.adrocafe.adrocafe.ui.modules.authentication.reset

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.adrosonic.adrocafe.adrocafe.R
import com.adrosonic.adrocafe.adrocafe.data.MessageEvent
import com.adrosonic.adrocafe.adrocafe.utils.ConstantsDirectory
import kotlinx.android.synthetic.main.fragment_reset_password.*
import org.greenrobot.eventbus.EventBus

class ResetPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ResetPasswordFragment()
    }

    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel::class.java)
        // TODO: Use the ViewModel
        button_reset.setOnClickListener {
            EventBus.getDefault().post(MessageEvent(ConstantsDirectory.resetpasswordfragment))
        }
    }

}
