package com.dna.sdk.dnapayments.di

import com.dna.sdk.dnapayments.BuildConfig
import com.dna.sdk.dnapayments.DnaSdkCredentials
import com.dna.sdk.dnapayments.api.client.AuthClient
import com.dna.sdk.dnapayments.api.client.PaymentClient
import com.dna.sdk.dnapayments.api.service.AuthService
import com.dna.sdk.dnapayments.api.service.PaymentService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIMEOUT_IN_SECONDS = 60
private const val READ_TIMEOUT_IN_SECONDS = 60
private const val WRITE_TIMEOUT_IN_SECONDS = 60

internal val networkModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .addNetworkInterceptor(provideHttpLoggingInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(DnaSdkCredentials.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(DnaSdkCredentials.authBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(AuthService::class.java)
    }

    single { AuthClient(get()) }

    single {
        get<Retrofit>().create(PaymentService::class.java)
    }

    single { PaymentClient(get()) }

}


internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()

    if (BuildConfig.IS_DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }

    return interceptor
}
