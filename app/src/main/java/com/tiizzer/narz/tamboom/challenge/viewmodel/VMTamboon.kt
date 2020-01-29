package com.tiizzer.narz.tamboom.challenge.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.tiizzer.narz.tamboom.challenge.R
import com.tiizzer.narz.tamboom.challenge.extension.gson.parse
import com.tiizzer.narz.tamboom.challenge.model.Charity
import com.tiizzer.narz.tamboom.challenge.model.CharityViewData
import com.tiizzer.narz.tamboom.challenge.provider.AppRequestProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMTamboon(application: Application): AndroidViewModel(application) {
    private val charitiesRetrieveSuccess = MutableLiveData<List<CharityViewData>>()
    private val charitiesRetrieveFail = MutableLiveData<ArrayList<Charity>>()
    private val showLoadingDialog = MutableLiveData<Void>()
    private val hideLoadingDialog = MutableLiveData<Void>()
    private val showMessage = MutableLiveData<String>()

    init {
        getCharities()
    }

    fun onCharitiesRetrieveSuccess(): LiveData<List<CharityViewData>> = this.charitiesRetrieveSuccess
    fun onShowLoadingDialog(): LiveData<Void> = this.showLoadingDialog
    fun onHideLoadingDialog(): LiveData<Void> = this.hideLoadingDialog
    fun onShowMessage(): LiveData<String> = this.showMessage

    fun getCharities(){
        viewModelScope.launch {
            this.launch(Dispatchers.Default) {
                this@VMTamboon.showLoadingDialog.postValue(null)
                try {
                    val response = AppRequestProvider.getCharitiesRequest(getApplication())
                    if(response.statusCode == 200) {
                        val charities = Gson().parse<ArrayList<Charity>>(response.text)
                        this@VMTamboon.charitiesRetrieveSuccess.postValue(charities.map { CharityViewData(it.id, it.name, it.logo_url) })
                    } else {
                        val message = this@VMTamboon.getApplication<Application>().getString(R.string.request_charities_error_message)
                        this@VMTamboon.showMessage.postValue(message)
                    }
                } catch (e: Exception){
                    val message = this@VMTamboon.getApplication<Application>().getString(R.string.request_charities_error_message)
                    this@VMTamboon.showMessage.postValue(message)
                }
                this@VMTamboon.hideLoadingDialog.postValue(null)
            }
        }
    }
}