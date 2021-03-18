package com.ntt.oneappprototyping

import com.ntt.oneappprototyping.home.HomeFragmentViewModel
import com.ntt.oneappprototyping.networking.DataRepository
import com.ntt.oneappprototyping.networking.OnlineRepository
import com.ntt.oneappprototyping.networking.StubboRetrofitFactory
import com.ntt.oneappprototyping.networking.rest.StubboOneAppApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    single<StubboOneAppApi> { StubboRetrofitFactory.createService() }
    single<DataRepository> { OnlineRepository(get()) }

    viewModel { MainActivityViewModel() }
    viewModel { HomeFragmentViewModel(get()) }

}