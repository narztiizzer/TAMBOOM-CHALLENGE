package com.tiizzer.narz.tamboom.challenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tiizzer.narz.tamboom.challenge.R
import kotlinx.android.synthetic.main.success_fragment.view.*

class SuccessFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.success_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setAction()
    }

    private fun setAction(){
        view!!.back_button.setOnClickListener {
            this.activity!!.finish()
        }
    }
}