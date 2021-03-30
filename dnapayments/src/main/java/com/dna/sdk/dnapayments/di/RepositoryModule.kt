package com.dna.sdk.dnapayments.di

import com.dna.sdk.dnapayments.repository.AuthRepository
import com.dna.sdk.dnapayments.repository.PaymentsRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    single { PaymentsRepository() }
    single { AuthRepository() }
}