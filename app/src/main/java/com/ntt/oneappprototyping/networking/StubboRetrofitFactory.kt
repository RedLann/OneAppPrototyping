package com.ntt.oneappprototyping.networking

import com.ntt.oneappprototyping.BuildConfig
import com.ntt.oneappprototyping.networking.rest.StubboOneAppApi
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import retrofit2.converter.gson.GsonConverterFactory

object StubboRetrofitFactory: KoinComponent {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }
    private val eitherCallAdapterFactory = EitherCallAdapterFactory()
    private val gsonConverterFactory = GsonConverterFactory.create()

    fun createService(): StubboOneAppApi {
        return RetrofitFactory.Builder()
            .addInterceptors(loggingInterceptor)
            .addCallAdapterFactories(eitherCallAdapterFactory)
            .addConverterFactories(gsonConverterFactory)
            .build()
            .createService("https://stubbo.flwy.io", StubboOneAppApi::class.java)
    }
}