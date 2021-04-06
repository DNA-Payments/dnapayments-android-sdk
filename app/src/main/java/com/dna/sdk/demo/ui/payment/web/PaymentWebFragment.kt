package com.dna.sdk.demo.ui.payment.web

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dna.sdk.demo.R
import com.dna.sdk.demo.compose.ViewModelFragment
import com.dna.sdk.demo.databinding.FragmentPaymentWebBinding
import com.dna.sdk.demo.preferences.Preferences
import com.dna.sdk.demo.preferences.TxnData
import com.dna.sdk.demo.utils.DnaDemoConstants
import com.dna.sdk.dnapayments.models.network.PaymentResult
import com.dna.sdk.dnapayments.models.network.payments.EnrollmentResponse
import com.dna.sdk.dnapayments.utils.DnaPaymentResult
import com.dna.sdk.dnapayments.utils.DnaSocketHelperImpl
import com.dna.sdk.dnapayments.utils.formPostData
import org.koin.android.ext.android.inject

class PaymentWebFragment : ViewModelFragment(), DnaPaymentResult {
    private var paymentData: EnrollmentResponse? = null
    private lateinit var binding: FragmentPaymentWebBinding
    private lateinit var viewModel: PaymentWebViewModel
    private val preferences: Preferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(PaymentWebViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val args = PaymentWebFragmentArgs.fromBundle(it)
            paymentData = args.paymentData
            viewModel.paymentData = paymentData
            viewModel.token = args.token
        }

        initObservers()
        setupWebview()
        loadWebViewData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = binding(
            inflater,
            R.layout.fragment_payment_web, container
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun loadWebViewData() {
        /**
         * You can use termUrl provided by Dna or use your own url
         */
        val termUrl = paymentData?.threeD?.termUrl.orEmpty()

        /**
         * orEmpty method used only for demo purposes. Values can't be empty
         */
        val postData = formPostData(
            paymentData?.threeD?.pareq.orEmpty(),
            paymentData?.threeD?.md.orEmpty(),
            viewModel.token,
            termUrl
        )
        binding.webView.postUrl(paymentData?.threeD?.acsUrl, postData.toByteArray())
    }

    override fun onResume() {
        super.onResume()
        paymentData?.id?.let { id ->
            DnaSocketHelperImpl.initWebSocket(
                id,
                this,
                binding.webView,
                DnaDemoConstants.paymentFormUrl
            )
        }
    }

    override fun onPause() {
        super.onPause()
        DnaSocketHelperImpl.close()
    }

    private fun initObservers() {

    }

    private fun setupWebview() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.allowFileAccess = true
        binding.webView.isHorizontalScrollBarEnabled = false
        binding.webView.isFocusable = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.setInitialScale(1)
        binding.webView.webChromeClient = WebChromeClient()
    }

    private fun handleResultStatus(result: PaymentResult) {
        val directions =
            PaymentWebFragmentDirections.actionPaymentWebFragmentToPaymentResultFragment(
                paymentResult = result
            )
        findNavController().navigate(directions)
    }

    override fun onPaymentSuccess(paymentResult: PaymentResult) {
        saveTxn()
        handleResultStatus(paymentResult)
    }

    override fun onPaymentFailure(paymentResult: PaymentResult) {
        saveTxn()
        handleResultStatus(paymentResult)
    }

    override fun onWebViewPageStarted() {
        super.onWebViewPageStarted()
        viewModel.progress.set(true)
    }

    override fun onWebViewPageFinished() {
        super.onWebViewPageFinished()
        viewModel.progress.set(false)
    }

    //saving transaction only for demo
    private fun saveTxn() {
        val reportList = preferences.getAllTransactions()
        if (reportList.size >= 100) {
            reportList.removeFirst()
        }
        reportList.addLast(
            TxnData(
                "${paymentData?.id}",
                "${paymentData?.amount} ${paymentData?.currency}",
                paymentData?.authDateTimeUTC.orEmpty()
            )
        )
        preferences.setAllTransactions(reportList)
    }

}