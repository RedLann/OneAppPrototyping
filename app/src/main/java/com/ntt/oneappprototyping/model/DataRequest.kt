package com.ntt.oneappprototyping.model

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.coroutineScope

sealed class DataRequest<out S> {
    inline fun <T> fold(failed: (ApiError) -> T, succeeded: (S) -> T, loading: () -> T): T =
        when (this) {
            is Failure -> failed(failure)
            is Success -> succeeded(success)
            is Loading -> loading()
        }

    fun <R> ifSuccess(block: (S) -> R): DataRequest<S> {
        if (this is Success) block(success)
        return this
    }

    fun <R> ifFailure(block: (ApiError) -> R): DataRequest<S> {
        if (this is Failure) block(failure)
        return this
    }

    fun <R> ifLoading(block: () -> R): DataRequest<S> {
        if (this is Loading) block()
        return this
    }

    fun data(): S? {
        return if (this is Success) success else null
    }

    fun error(): ApiError? {
        return if (this is Failure) failure else null
    }

    fun isSuccessful(): Boolean {
        return this is Success
    }

    fun isFailure(): Boolean {
        return this is Failure
    }
}

object None

data class Failure(val failure: ApiError) : DataRequest<Nothing>()
data class Success<out S>(val success: S) : DataRequest<S>()
object Loading : DataRequest<Nothing>()

typealias EmptyResponse = DataRequest<None>
typealias DeferredEmptyResponse = Deferred<EmptyResponse>

