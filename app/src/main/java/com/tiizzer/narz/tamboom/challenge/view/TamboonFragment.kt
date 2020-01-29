package com.tiizzer.narz.tamboom.challenge.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tiizzer.narz.tamboom.challenge.DonationActivity
import com.tiizzer.narz.tamboom.challenge.MainActivity
import com.tiizzer.narz.tamboom.challenge.R
import com.tiizzer.narz.tamboom.challenge.model.CharityViewData
import com.tiizzer.narz.tamboom.challenge.viewmodel.VMDonation
import com.tiizzer.narz.tamboom.challenge.viewmodel.VMTamboon
import kotlinx.android.synthetic.main.tamboon_fragment.*
import kotlinx.android.synthetic.main.tamboon_fragment.view.*

class TamboonFragment: Fragment() {
    private val vmFragment: VMTamboon by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(this.activity!!.application!!).create(
        VMTamboon::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tamboon_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setAction()
    }

    private fun setObserver(){
        getViewModel().onCharitiesRetrieveSuccess().observe(this.activity!!, Observer { charities ->
            setCharitiesList(charities)
        })

        getViewModel().onShowMessage().observe(this.activity!!, Observer {
            Snackbar.make(view!!.fragment_container, it, Snackbar.LENGTH_LONG).apply {
                setAction(getString(R.string.app_dissmiss)) {
                    this.dismiss()
                }
            }.show()
        })

        getViewModel().onShowLoadingDialog().observe(this.activity!!, Observer {
            (this.activity!! as MainActivity).startLoading()
        })

        getViewModel().onHideLoadingDialog().observe(this.activity!!, Observer {
            (this.activity!! as MainActivity).stopLoading()
        })
    }

    private fun setAction() {
        charities_list.setOnItemClickListener { parent, view, position, id ->
            startActivity(Intent(this.context, DonationActivity::class.java).apply {
                putExtra(VMDonation.ID, id)
            })
        }
    }

    private fun setCharitiesList(charities: List<CharityViewData>){
        if(charities_list.adapter != null) {
            (charities_list.adapter as CharityAdapter).apply {
                this.clear()
                this.addItems(charities)
                this.notifyDataSetChanged()
            }
        } else {
            charities_list.adapter = CharityAdapter().apply {
                this.addItems(charities)
            }
        }
    }

    private fun getViewModel() = this.vmFragment
}