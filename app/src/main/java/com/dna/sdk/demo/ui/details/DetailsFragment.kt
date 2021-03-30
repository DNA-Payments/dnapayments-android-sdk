package com.dna.sdk.demo.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dna.sdk.demo.R
import com.dna.sdk.demo.compose.ViewModelFragment
import com.dna.sdk.demo.databinding.FragmentDetailsBinding
import com.dna.sdk.dnapayments.models.network.payments.TransactionDetailsResponse

class DetailsFragment : ViewModelFragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    private var token = ""
    private var amount = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(DetailsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val args = DetailsFragmentArgs.fromBundle(it)
            viewModel.txnId = args.txnId
            initObservers()
            viewModel.sendAuthRequest()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(
            inflater,
            R.layout.fragment_details, container
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun initObservers() {
        viewModel.getAuthLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                token = it.accessToken.orEmpty()

                if (!token.isNullOrBlank()) {
                    viewModel.getTransactionById(token)
                }
            }
        })

        viewModel.getTransactionByIdLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { details ->
                populateViews(details)
                viewModel.setProgress(false)
            }
        })
    }

    private fun populateViews(data: TransactionDetailsResponse) {
        amount = data.amount ?: 0.0
        binding.tvAmount.text = "Amount: ${data.amount}"
        binding.tvInvoiceId.text = "Invoice ID: ${data.invoiceId}"
        binding.tvRrn.text = "RRN: ${data.rrn}"
        binding.tvId.text = "ID: ${data.id}"
        binding.tvCurrency.text = "Currency: ${data.currency}"
        binding.tvAccountId.text = "AccountId: ${data.accountId}"
        binding.tvAuthDateTimeUTC.text = "AuthDateTimeUTC: ${data.authDateTimeUTC}"
        binding.tvResponseCode.text = "ResponseCode: ${data.responseCode}"
        binding.tvAuthCode.text = "AuthCode: ${data.authCode}"
        binding.tvAvsResult.text = "AvsResult: ${data.avsResult}"
        binding.tvCscResult.text = "CscResult: ${data.cscResult}"
        binding.tvPayerAuthenticationResult.text =
            "PayerAuthenticationResult: ${data.payerAuthenticationResult}"
        binding.tvCardExpiryDate.text = "CardExpiryDate: ${data.cardExpiryDate}"
        binding.tvCardPanStarred.text = "CardPanStarred: ${data.cardPanStarred}"
        binding.tvCardTokenId.text = "CardTokenId: ${data.cardTokenId}"
        binding.tvCardSchemeName.text = "CardSchemeName: ${data.cardSchemeName}"
        binding.tvCardIssuingCountry.text = "CrdIssuingCountry: ${data.cardIssuingCountry}"
        binding.tvTransactionState.text = "TransactionState: ${data.transactionState}"
        binding.tvTransactionType.text = "TransactionType: ${data.transactionType}"
        binding.tvPaymentMethod.text = "PaymentMethod: ${data.paymentMethod}"
        binding.tvPayoutAmount.text = "PayoutAmount: ${data.payoutAmount}"
    }
}