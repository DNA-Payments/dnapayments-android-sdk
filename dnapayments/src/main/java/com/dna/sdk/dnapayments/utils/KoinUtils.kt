package com.dna.sdk.dnapayments.utils

import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.KoinComponent

internal object MyKoinContext {
    var koinApp: KoinApplication? = null
}

internal abstract class CustomKoinComponent : KoinComponent {
    // Override default Koin instance, intially target on GlobalContext to yours
    override fun getKoin(): Koin = MyKoinContext?.koinApp!!.koin
}