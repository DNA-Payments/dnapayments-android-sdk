package com.dna.sdk.dnapayments.models.network

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ErrorResponse(
    @SerializedName("errorCode") val code: Int,
    @SerializedName("message") val message: String
)
