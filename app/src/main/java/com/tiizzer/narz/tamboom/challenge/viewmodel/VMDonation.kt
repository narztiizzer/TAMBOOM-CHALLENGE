package com.tiizzer.narz.tamboom.challenge.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.omise.android.ui.CardNameEditText
import com.tiizzer.narz.tamboom.challenge.R
import com.tiizzer.narz.tamboom.challenge.model.Donation
import com.tiizzer.narz.tamboom.challenge.provider.AppRequestProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    fun onShowMessage(): LiveData<String> = this.showMessage
    fun onShowLoadingDialog(): LiveData<Void> = this.showLoadingDialog
    fun onHideLoadingDialog(): LiveData<Void> = this.hideLoadingDialog

    fun setIntentData(intent: Intent){
        this.charityId = intent.getLongExtra(ID, 0)
    }

    fun validateInput(name: CardNameEditText, amount: String?) {
        return when {
            !name.isValid -> {
                val message = getApplication<Application>().getString(R.string.donation_validate_name_fail)
                this.showMessage.postValue(message)
            }
            amount.isNullOrEmpty() -> {
                val message = getApplication<Application>().getString(R.string.donation_validate_amount_empty)
                this.showMessage.postValue(message)
            }
            else -> {
                val value = this.reformatAmount(amount)
                if(value > 0) {
                    this.makeDonate(Donation(
                        name = name.cardName,
                        token = getApplication<Application>().getString(R.string.app_token),
                        amount = value
                    ))
                } else {
                    val message = getApplication<Application>().getString(R.string.donation_validate_amount_fail)
                    this.showMessage.postValue(message)
                }
            }
        }
    }

    private fun reformatAmount(amount: String): Double {
        var tempValue = amount
        if(amount.endsWith(".")) tempValue = "${tempValue}0"
        if(tempValue.startsWith(".")) tempValue = "0$tempValue"
        return tempValue.toDouble()
    }

    private fun makeDonate(donate: Donation) {
        viewModelScope.launch(Dispatchers.Default){
            this@VMDonation.showLoadingDialog.postValue(null)
            try {
                val response = AppRequestProvider.getDonationRequest(this@VMDonation.getApplication(), donate)
                if(response.statusCode == 200){
                    this@VMDonation.requestDonationSuccess.postValue(null)
                } else {
                    val message = getApplication<Application>().getString(R.string.donation_error)
                    this@VMDonation.showMessage.postValue(message)
                }
            } catch (e: Exception){
                val message = getApplication<Application>().getString(R.string.donation_error)
                this@VMDonation.showMessage.postValue(message)
            }
            this@VMDonation.hideLoadingDialog.postValue(null)
        }
    }
}