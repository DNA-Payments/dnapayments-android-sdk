package com.dna.sdk.demo.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class PreferencesImpl(context: Context) : Preferences {

    private val TXN_LIST = "TXN_LIST"

    private val sp: SharedPreferences =
        context.getSharedPreferences("PreferencesImpl", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sp.edit()

    override fun setAllTransactions(value: ArrayDeque<TxnData>) {
        val json = Gson().toJson(value)
        editor.putString(TXN_LIST, json).apply()
    }

    override fun getAllTransactions(): ArrayDeque<TxnData> {
        val summaryReport = sp.getString(TXN_LIST, "")
        val type = genericType<ArrayDeque<TxnData>>()
        val reportList: ArrayDeque<TxnData>? =
            Gson().fromJson(summaryReport, type)

        return if (reportList.isNullOrEmpty()) {
            ArrayDeque<TxnData>()
        } else {
            reportList
        }
    }

    inline fun <reified T> genericType() = object : TypeToken<T>() {}.type
}