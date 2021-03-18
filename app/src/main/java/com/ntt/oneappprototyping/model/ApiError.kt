package com.ntt.oneappprototyping.model

sealed class ApiError

data class HttpError(val code: Int, val body: RawError) : ApiError()
data class NetworkError(val throwable: Throwable) : ApiError()
data class UnknownApiError(val throwable: Throwable) : ApiError()