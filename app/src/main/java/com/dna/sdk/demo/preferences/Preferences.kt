package com.dna.sdk.demo.preferences

import java.util.*

interface Preferences {
    fun setAllTransactions(value: ArrayDeque<TxnData>)
    fun getAllTransactions(): ArrayDeque<TxnData>
}