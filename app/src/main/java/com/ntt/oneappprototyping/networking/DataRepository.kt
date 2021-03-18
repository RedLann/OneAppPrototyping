package com.ntt.oneappprototyping.networking

import androidx.lifecycle.LiveData
import com.ntt.oneappprototyping.model.DataRequest
import com.ntt.oneappprototyping.model.User

interface DataRepository {
    val userLiveData: LiveData<DataRequest<User>>
    suspend fun refreshUser()
}