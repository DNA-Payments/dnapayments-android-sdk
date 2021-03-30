package com.dna.sdk.dnapayments.utils

import com.dna.sdk.dnapayments.models.network.PaymentResult
import com.google.gson.Gson

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.net.URLEncoder
import java9.util.function.BiConsumer

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

fun formPostData(pareq: String, md: String, token: String, termUrl: String): String {
    return java.lang.String.format(
        "PaReq=%s&MD=%s&TermUrl=%s",
        URLEncoder.encode(pareq, "UTF-8"),
        URLEncoder.encode(md, "UTF-8"),
        URLEncoder.encode(
            "${termUrl}?Access=${token}",
            "UTF-8"
        )
    )
}

fun convertIntoPaymentResult(message: String) : PaymentResult? {
    return try {
        Gson().fromJson(message, PaymentResult::class.java)
    } catch (e: java.lang.Exception) {
        null
    }
}

@JvmOverloads
fun <R> getContinuation(onFinished: BiConsumer<R?, Throwable?>, dispatcher: CoroutineDispatcher = Dispatchers.Default): Continuation<R> {
    return object : Continuation<R> {
        override val context: CoroutineContext
            get() = dispatcher

        override fun resumeWith(result: Result<R>) {
            onFinished.accept(result.getOrNull(), result.exceptionOrNull())
        }
    }
}
