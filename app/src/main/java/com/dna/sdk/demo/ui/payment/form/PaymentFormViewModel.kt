package com.dna.sdk.demo.ui.payment.form

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dna.sdk.demo.compose.DispatchViewModel
import com.dna.sdk.demo.utils.Event
import com.dna.sdk.dnapayments.api.ApiResponse
import com.dna.sdk.dnapayments.api.errorResponse
import com.dna.sdk.dnapayments.domain.PaymentInteractor
import com.dna.sdk.dnapayments.models.network.payments.BillingAddress
import com.dna.sdk.dnapayments.models.network.payments.Card
import com.dna.sdk.dnapayments.models.network.payments.EnrollmentResponse
import com.dna.sdk.dnapayments.models.network.payments.PaymentData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.math.BigDecimal

class PaymentFormViewModel : DispatchViewModel() {

    val onErrorLiveData: MutableLiveData<Event<String>> = MutableLiveData()

    var progress = ObservableBoolean(false)

    var token = ""
    var amount = BigDecimal(0.0)
    var selectedCurrency = ""
    var invoiceId = ""

    var descriptionField = ObservableField("Order description")
    var phoneField = ObservableField("013213210303")
    var emailField = ObservableField("test@gmail.com")
    var cardNumberField = ObservableField("5283901669012672")
    var cardCvvField = ObservableField("004")
    var countryField = ObservableField("GB")
    var cityField = ObservableField("London")
    var addressOneField = ObservableField("13 New Burlington St")
    var addressTwoField = ObservableField("14 New Burlington St")
    var firstNameField = ObservableField("John")
    var lastNameField = ObservableField("Doe")
    var postalCodeField = ObservableField("6546546")
    var selectedMonth = ""
    var selectedYear = ""
    var selectedPeriodicType = ""
    var selectedRecurringTxnType = ""
    var selectedTxnType = ""

    private val _loadPaymentsLiveData = MutableLiveData<Event<EnrollmentResponse>>()
    val loadPaymentsLiveData: LiveData<Event<EnrollmentResponse>> get() = _loadPaymentsLiveData

    /**
     * InvoiceId - must be unique for this transaction. This value must match the value provided during authorisation or the request will be rejected.
     */
    private fun formPaymentData(): PaymentData {
        val card = Card.Builder()
            .setAccountNumber(cardNumberField.get().toString())
            .setCsc(cardCvvField.get().toString())
            .setExpirationMonth(selectedMonth)
            .setExpirationYear(selectedYear)
            .build()

        val address = BillingAddress.Builder()
            .setStreetAddress1(addressOneField.get().toString())
            .setStreetAddress2(addressTwoField.get().toString())
            .setFirstName(firstNameField.get().toString())
            .setLastName(lastNameField.get().toString())
            .setCity(cityField.get().toString())
            .setCountry(countryField.get().toString())
            .setPostalCode(postalCodeField.get().toString())
            .build()

        return PaymentData.Builder()
            .setAccountId("testuser123")
            .setAmount(amount)
            .setCurrency(selectedCurrency)
            .setDescription(descriptionField.get().toString())
            .setEmail(emailField.get().toString())
            .setInvoiceId(invoiceId)
            .setPhone(phoneField.get().toString())
            .setRecurringTransactionType(selectedRecurringTxnType)
            .setPeriodicType(selectedPeriodicType)
            .setTransactionType(selectedTxnType)
            //.setPostLink("test Post Link")
            //.setFailurePostLink("test Failure Post Link")
            //get ip address from device
            .setIpAddress("192.168.0.11")
            .setBillingAddress(address)
            .setCard(card).build()
    }

    fun onConfirmClick() {
        progress.set(true)
        sendEnrollmentRequest()
    }

    private fun sendEnrollmentRequest() {
        launchEnrollmentRequest {
            PaymentInteractor.getInstance().enrollmentPayment(
                token, formPaymentData()
            )
        }
    }

    private fun launchEnrollmentRequest(block: suspend () -> ApiResponse<EnrollmentResponse>): Job {
        return viewModelScope.launch {
            try {
                when (val response = block()) {
                    /**
                     * You can check success response by cardholderAuthenticationStatus. Description is in document
                     */
                    is ApiResponse.Success -> {
                        _loadPaymentsLiveData.postValue(Event(response.data!!))
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

    fun showErrorMessage(message: String) {
        onErrorLiveData.postValue(Event(message))
    }
}
