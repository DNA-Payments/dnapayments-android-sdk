package com.dna.sdk.dnapayments.preference

import android.content.Context
import android.content.SharedPreferences

class PreferencesImpl(context: Context) : Preferences {

    private val AUTH_TOKEN = "AUTH_TOKEN"

    private var sp: SharedPreferences =
        context.getSharedPreferences("PreferencesImpl", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sp.edit()

    override fun setAuthToken(value: String) {
        editor.putString(AUTH_TOKEN, value).apply()
    }

    override fun getAuthToken() = sp.getString(AUTH_TOKEN, "")!!

    override fun logout() {
        setAuthToken("")
    }
}
