package com.tiizzer.narz.tamboom.challenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tiizzer.narz.tamboom.challenge.R
import com.tiizzer.narz.tamboom.challenge.viewmodel.VMDonation
import kotlinx.android.synthetic.main.donation_fragment.view.*

class DonationFragment : Fragment() {
    private val vmFragment: VMDonation by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this.activity!!.application!!).create(
        VMDonation::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.donation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setAction()
    }

    private fun setAction() {
        view!!.cancel_button.setOnClickListener {
            this.activity!!.finish()
        }

        view!!.confirm_button.setOnClickListener {
            this.getViewModel().validateInput(view!!.name_input.cardName, view!!.amount_input.text.toString())
        }


    }

    private fun setObserver() {
        getViewModel().setIntentData(this.activity!!.intent)
        getViewModel().onValidateInputPass().observe(this.activity!!, Observer {

        })
    }



    private fun getViewModel() = this.vmFragment
}