package com.dna.sdk.demo.ui.payment.web

import androidx.databinding.ObservableBoolean
import com.dna.sdk.demo.compose.DispatchViewModel
import com.dna.sdk.dnapayments.models.network.payments.EnrollmentResponse

class PaymentWebViewModel : DispatchViewModel() {

    var progress = ObservableBoolean(false)

    var paymentData: EnrollmentResponse? = null
    var token = ""
}
