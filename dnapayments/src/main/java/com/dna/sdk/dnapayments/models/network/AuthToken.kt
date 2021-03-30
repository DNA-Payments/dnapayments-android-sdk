package com.dna.sdk.dnapayments.models.network

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Keep
@Parcelize
data class AuthToken (

    @SerializedName("access_token")
    var accessToken: String? = null,

    @SerializedName("refresh_token")
    var refreshToken: String? = null,

    var scope: String? = null,

    @SerializedName("token_type")
    var tokenType: String? = null,

    @SerializedName("expires_in")
    var expiresIn: Long? = null
) : Parcelable
