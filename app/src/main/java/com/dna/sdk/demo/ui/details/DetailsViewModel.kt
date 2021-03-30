package com.dna.sdk.demo.ui.details

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dna.sdk.demo.compose.DispatchViewModel
import com.dna.sdk.demo.utils.Event
import com.dna.sdk.dnapayments.api.ApiResponse
import com.dna.sdk.dnapayments.api.errorResponse
import com.dna.sdk.dnapayments.domain.AuthInteractor
import com.dna.sdk.dnapayments.domain.PaymentInteractor
import com.dna.sdk.dnapayments.models.network.AuthToken
import com.dna.sdk.dnapayments.models.network.payments.TransactionDetailsResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel : DispatchViewModel() {

    var progress = ObservableBoolean(false)

    var resultField = ObservableField("")

    private val _getAuthTokenLiveData = MutableLiveData<Event<AuthToken>>()
    val getAuthLiveData: LiveData<Event<AuthToken>> get() = _getAuthTokenLiveData

    private var _getTransactionByIdLiveData = MutableLiveData<Event<TransactionDetailsResponse>>()
    val getTransactionByIdLiveData: LiveData<Event<TransactionDetailsResponse>> get() = _getTransactionByIdLiveData

    var token = ""
    var txnId = ""

    fun sendAuthRequest() {
        setResultMessage("")
        progress.set(true)
        launchAuthRequest {
            AuthInteractor.getInstance().getOperationsToken()
        }
    }

    private fun launchAuthRequest(block: suspend () -> ApiResponse<AuthToken>): Job {
        return viewModelScope.launch {
            try {
                when (val response = block()) {
                    is ApiResponse.Success -> {
                        _getAuthTokenLiveData.postValue(Event(response.data!!))
                    }
                    is ApiResponse.Failure.Error -> setResultMessage(response.errorResponse().message)
                    is ApiResponse.Failure.Exception -> setResultMessage(response.errorResponse().message)
                }
            } catch (error: Exception) {
                setResultMessage(error.message.orEmpty())
            } finally {
                progress.set(false)
            }
        }
    }

    private fun sendDetailsRequest() {
        launchDetailsRequest {
            PaymentInteractor.getInstance().getTransactionById(
                token, txnId
            )
        }
    }

    private fun launchDetailsRequest(block: suspend () -> ApiResponse<TransactionDetailsResponse>): Job {
        return viewModelScope.launch {
            try {
                when (val response = block()) {
                    is ApiResponse.Success -> {
                        _getTransactionByIdLiveData.postValue(Event(response.data!!))
                    }
                    is ApiResponse.Failure.Error -> setResultMessage(response.errorResponse().message)
                    is ApiResponse.Failure.Exception -> setResultMessage(response.errorResponse().message)
                }
            } catch (error: Exception) {
                setResultMessage(error.message.orEmpty())
            }
        }
    }

    fun getTransactionById(token: String) {
        this.token = token
        sendDetailsRequest()
    }

    private fun setResultMessage(message: String) {
        resultField.set(message)
    }

    fun setProgress(isVisible: Boolean) {
        progress.set(isVisible)
    }

}
