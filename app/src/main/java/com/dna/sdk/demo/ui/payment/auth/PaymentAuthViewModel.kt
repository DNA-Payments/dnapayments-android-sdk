package com.dna.sdk.demo.ui.payment.auth

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dna.sdk.demo.compose.DispatchViewModel
import com.dna.sdk.demo.utils.DnaDemoConstants
import com.dna.sdk.demo.utils.Event
import com.dna.sdk.dnapayments.api.ApiResponse
import com.dna.sdk.dnapayments.api.errorResponse
import com.dna.sdk.dnapayments.domain.AuthInteractor
import com.dna.sdk.dnapayments.models.network.AuthToken
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PaymentAuthViewModel : DispatchViewModel() {

    var progress = ObservableBoolean(false)

    private val _loadAuthTokenLiveData = MutableLiveData<Event<AuthToken>>()
    val loadAuthTokenLiveData: LiveData<Event<AuthToken>> get() = _loadAuthTokenLiveData

    val onErrorLiveData: MutableLiveData<String> = MutableLiveData()

    var selectedCurrency = "GBP"
    var invoiceId = ""

    /**
     * InvoiceId - must be unique for this transaction.
     * This value must match the value provided during authorisation or the request will be rejected.
     */
    fun sendAuthRequest(amount: Double) {
        invoiceId = System.currentTimeMillis().toString()
        progress.set(true)
        launchAuthRequest {
            AuthInteractor.getInstance().getUserToken(
                invoiceId = invoiceId,
                amount = amount,
                currency = selectedCurrency,
                paymentFormUrl = DnaDemoConstants.paymentFormUrl
            )
        }
    }

    private fun launchAuthRequest(block: suspend () -> ApiResponse<AuthToken>): Job {
        return viewModelScope.launch {
            try {
                when (val response = block()) {
                    is ApiResponse.Success -> {
                        _loadAuthTokenLiveData.postValue(Event(response.data!!))
                    }
                    is ApiResponse.Failure.Error -> showErrorMessage(response.errorResponse().message)
                    is ApiResponse.Failure.Exception -> showErrorMessage(response.errorResponse().message)
                }
            } catch (error: Exception) {
                showErrorMessage(error.message.orEmpty())
            } finally {
                progress.set(false)
            }
        }
    }

    private fun showErrorMessage(message: String) {
        onErrorLiveData.postValue(message)
    }

}
