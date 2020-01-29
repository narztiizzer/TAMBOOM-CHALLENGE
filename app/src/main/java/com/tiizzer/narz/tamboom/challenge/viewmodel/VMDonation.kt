package com.tiizzer.narz.tamboom.challenge.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

class VMDonation(application: Application): AndroidViewModel(application) {
    private var charityId = 0L
    private val validateInputPass = MutableLiveData<Boolean>()
    private val showLoadingDialog = MutableLiveData<Void>()
    private val hideLoadingDialog = MutableLiveData<Void>()
    private val showMessage = MutableLiveData<String>()
    private val requestDonationSuccess = MutableLiveData<Void>()

    companion object {
        const val ID = "ID"
    }

    fun onValidateInputPass(): LiveData<Boolean> = this.validateInputPass
    fun onRequestDonationSuccess(): LiveData<Void> = this.requestDonationSuccess

    fun setIntentData(intent: Intent){
        this.charityId = intent.getLongExtra(ID, 0)
    }

    fun validateInput(name: String?, amount: String?): Boolean {
        return when {
            name.isNullOrEmpty() -> { false }
            amount.isNullOrEmpty() -> { false }
            else -> {
                var tempValue = amount
                if(amount.endsWith(".")) tempValue = "${tempValue}0"
                if(tempValue.startsWith(".")) tempValue = "0$tempValue"
                val value = tempValue.toDouble()
                value > 0
            }
        }
    }
}