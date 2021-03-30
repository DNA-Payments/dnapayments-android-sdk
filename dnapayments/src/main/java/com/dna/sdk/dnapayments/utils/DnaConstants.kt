package com.dna.sdk.dnapayments.utils

internal object DnaConstants {
    const val DEV_WEB_SOCKET_URL = "wss://test-api.dnapayments.com/ws/"
    const val DEV_URL_BASE = "https://test-api.dnapayments.com/"
    const val DEV_AUTH_BASE = "https://test-oauth.dnapayments.com/"

    const val PROD_WEB_SOCKET_URL = "wss://api.dnapayments.com/ws/"
    const val PROD_URL_BASE = "https://api.dnapayments.com/"
    const val PROD_AUTH_BASE = "https://oauth.dnapayments.com/"

    const val userTokenScope = "payment integration_seamless"
    const val operationsScope = "webapi"
    const val grantType = "client_credentials"
}