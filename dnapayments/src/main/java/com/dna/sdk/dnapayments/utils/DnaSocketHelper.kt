package com.dna.sdk.dnapayments.utils

import android.graphics.Bitmap
import android.net.UrlQuerySanitizer
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.Keep
import com.dna.sdk.dnapayments.DnaSdkCredentials
import com.dna.sdk.dnapayments.models.network.PaymentResult
import com.dna.sdk.dnapayments.models.network.Result
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFactory
import java.util.*
import javax.net.ssl.SSLContext

@Keep
interface DnaPaymentResult {
    fun onPaymentSuccess(paymentResult: PaymentResult)
    fun onPaymentFailure(paymentResult: PaymentResult)
    fun onWebViewPageStarted() {}
    fun onWebViewPageFinished() {}
}

interface DnaSocketHelper {
    fun initWebSocket(
        txnId: String,
        listener: DnaPaymentResult,
        webView: WebView,
        paymentFormUrl: String
    )
    fun close()
}

object DnaSocketHelperImpl : DnaSocketHelper {
    private lateinit var listener: DnaPaymentResult
    private var ws: WebSocket? = null
    private val timer = Timer()

    var paymentFormUrl = ""

    override fun initWebSocket(
        txnId: String,
        listener: DnaPaymentResult,
        webView: WebView,
        paymentFormUrl: String
    ) {
        this.listener = listener
        this.paymentFormUrl = paymentFormUrl
        setupWebViewClient(webView)
        setupWebSocketClient(DnaSdkCredentials.webSocketUrl + txnId)
    }

    private fun setupWebSocketClient(dnaSocketUrl: String) {
        try {
            val factory = WebSocketFactory()
            val context: SSLContext = NaiveSSLContext.getInstance("TLS")
            factory.sslContext = context
            factory.verifyHostname = false
            ws = factory.createSocket(dnaSocketUrl)
            ws?.addListener(object : WebSocketAdapter() {
                override fun onTextMessage(websocket: WebSocket?, text: String?) {
                    setSocketResult(text.toString())
                }

                override fun onConnectError(websocket: WebSocket?, exception: WebSocketException?) {
                    super.onConnectError(websocket, exception)
                }

                override fun onConnected(
                    websocket: WebSocket?,
                    headers: MutableMap<String, MutableList<String>>?
                ) {
                    super.onConnected(websocket, headers)
                    startTimer()
                }
            })

            ws?.connectAsynchronously()
        } catch (e: Exception) {
        }
    }

    private fun subscribe() {
        ws?.sendText("__ping__")
    }

    override fun close() {
        try {
            timer.cancel()
            ws?.disconnect()
        } catch (ex: Exception) {
        }
    }

    private fun setSocketResult(message: String?) {
        if (message != "__pong__") {
            val result = convertIntoPaymentResult(message.orEmpty())
            result?.let {
                handleResultStatus(it)
            }
        }
    }

    private fun handleResultStatus(result: PaymentResult) {
        close()
        Logger.logPaymentResult(result)
        if (result.result?.result == "ok") {
            listener.onPaymentSuccess(result)
        } else {
            listener.onPaymentFailure(result)
        }
    }

    private fun setupWebViewClient(webView: WebView) {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (url.contains(paymentFormUrl)) {
                    parsePaymentFormUrl(url)
                    true
                } else {
                    false
                }
            }

            override fun onLoadResource(view: WebView, url: String) {}
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                listener.onWebViewPageStarted()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                listener.onWebViewPageFinished()
            }
        }
    }

    private fun parsePaymentFormUrl(url: String) {
        val sanitizer = UrlQuerySanitizer()
        sanitizer.allowUnregisteredParamaters = true;
        sanitizer.parseUrl(url)
        val result = sanitizer.getValue("result")
        val paymentResult = PaymentResult()
        paymentResult.result = Result(result = result)
        handleResultStatus(paymentResult)
    }

    private fun startTimer() {
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    subscribe()
                }
            },
            0, 20000
        )
    }
}