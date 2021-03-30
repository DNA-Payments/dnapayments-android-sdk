package com.dna.sdk.demo.ui.payment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dna.sdk.demo.R
import com.dna.sdk.demo.compose.ViewModelFragment
import com.dna.sdk.demo.databinding.FragmentPaymentBinding
import com.dna.sdk.dnapayments.models.network.AuthToken

class PaymentAuthFragment : ViewModelFragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: PaymentAuthViewModel
    private var amount: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(PaymentAuthViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = binding(
            inflater,
            R.layout.fragment_payment, container
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        populateCurrencySpinner()

        binding.btnAuthorise.setOnClickListener {
            amount = binding.etAmount.text.toString().toDoubleOrNull()
            amount?.let {
                viewModel.sendAuthRequest(it)
            } ?: showToast(requireContext(), getString(R.string.invalid_amount))
        }

        return binding.root
    }

    private fun initObservers() {
        viewModel.loadAuthTokenLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { authToken ->
                handleAuthToken(authToken)
            }
        })

        viewModel.onErrorLiveData.observe(viewLifecycleOwner, Observer {
            showToast(requireContext(), it)
        })

        binding.etAmount.addTextChangedListener {
            amount = it.toString().toDoubleOrNull()
        }
    }

    private fun handleAuthToken(it: AuthToken) {
        val token = it.accessToken
        if (!token.isNullOrBlank()) {
            launchPaymentFormFragment(token)
        } else {
            showToast(requireContext(), getString(R.string.authorization_error))
        }
    }

    private fun launchPaymentFormFragment(token: String) {
        val directions =
            PaymentAuthFragmentDirections.actionPaymentFragmentToPaymentFormFragment(
                token,
                amount!!.toFloat(),
                viewModel.selectedCurrency,
                viewModel.invoiceId
            )
        findNavController().navigate(directions)
    }

    private fun populateCurrencySpinner() {
        binding.currencySpinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currency_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.currencySpinner.adapter = adapter
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        viewModel.selectedCurrency = parent.getItemAtPosition(pos) as String
    }
}