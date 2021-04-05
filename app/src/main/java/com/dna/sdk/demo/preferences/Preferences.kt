package com.dna.sdk.demo.preferences

import java.util.*

interface Preferences {
    fun setAllTransactions(value: ArrayDeque<TxnData>)
    fun getAllTransactions(): ArrayDeque<TxnData>
    fun setTerminalIds(value: ArrayList<String>)
    fun getTerminalIds(): ArrayList<String>
    fun setTerminalId(value: String)
    fun getTerminalId(): String
}