package com.dna.sdk.demo.ui.payment.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dna.sdk.demo.R
import com.dna.sdk.demo.compose.ViewModelFragment
import com.dna.sdk.demo.databinding.FragmentPaymentResultBinding
import com.dna.sdk.dnapayments.models.network.PaymentResult

class PaymentResultFragment : ViewModelFragment() {
    private lateinit var binding: FragmentPaymentResultBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val args = PaymentResultFragmentArgs.fromBundle(it)
            handleResult(args.paymentResult)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = binding(
            inflater,
            R.layout.fragment_payment_result, container
        )
        binding.lifecycleOwner = this

        binding.btnClose.setOnClickListener {
            val directions =
                PaymentResultFragmentDirections.actionPaymentResultFragmentToMainFragment()
            findNavController().navigate(directions)
        }

        return binding.root
    }

    private fun handleResult(paymentResult: PaymentResult) {
        val result = paymentResult.result?.result.orEmpty()

        if (result == "ok") {
            binding.ivResult.setImageResource(R.drawable.ic_success)
        } else {
            binding.ivResult.setImageResource(R.drawable.ic_failure)
        }
        binding.tvResult.text = paymentResult.result?.errorMessage ?: result
    }
}