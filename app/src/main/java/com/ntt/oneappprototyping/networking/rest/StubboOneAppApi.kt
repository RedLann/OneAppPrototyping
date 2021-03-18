package com.ntt.oneappprototyping.networking.rest

import arrow.core.Either
import com.ntt.oneappprototyping.model.ApiError
import com.ntt.oneappprototyping.model.User
import retrofit2.http.*

@JvmSuppressWildcards
interface StubboOneAppApi {

    @GET("/anymock/OneAppTests/getUser.json")
    suspend fun getUser(): Either<ApiError, User>
}