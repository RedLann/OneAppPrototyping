package com.ntt.oneappprototyping.networking

import androidx.lifecycle.MutableLiveData
import com.ntt.oneappprototyping.model.DataRequest
import com.ntt.oneappprototyping.model.Failure
import com.ntt.oneappprototyping.model.Success
import com.ntt.oneappprototyping.model.User
import com.ntt.oneappprototyping.networkFold
import com.ntt.oneappprototyping.networking.rest.StubboOneAppApi
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class OnlineRepository(val api: StubboOneAppApi): DataRepository {
    override val userLiveData = MutableLiveData<DataRequest<User>>()

    override suspend fun refreshUser() = coroutineScope {
        api.getUser().networkFold(
            ifFail = {
                Timber.d("ENZOOO Qua")
                userLiveData.value = Failure(it)
            },
            ifSuccess = {
                Timber.d("ENZOOO Quo")
                userLiveData.value = Success(it)
            }
        )
        Unit
    }
}