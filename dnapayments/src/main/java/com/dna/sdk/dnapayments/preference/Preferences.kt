package com.dna.sdk.dnapayments.preference


interface Preferences {
    fun setAuthToken(value: String)

    fun getAuthToken(): String

    fun logout()
}
