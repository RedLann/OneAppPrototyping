package com.ntt.oneappprototyping.networking

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.google.gson.Gson
import com.ntt.oneappprototyping.model.*
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


internal class EitherCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != Either::class.java) return null
        check(responseType is ParameterizedType) { "Response type must be a parameterized type." }

        val leftType = getParameterUpperBound(0, responseType)
        if (getRawType(leftType) != ApiError::class.java) return null

        val rightType = getParameterUpperBound(1, responseType)
        return EitherCallAdapter<Any>(rightType)
    }
}

private class EitherCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, Call<Either<ApiError, R>>> {

    override fun adapt(call: Call<R>): Call<Either<ApiError, R>> = EitherCall(call, successType)

    override fun responseType(): Type = successType
}

private class EitherCall<R>(
    private val delegate: Call<R>,
    private val successType: Type
) : Call<Either<ApiError, R>> {
    private val gson by lazy { Gson() }

    override fun enqueue(callback: Callback<Either<ApiError, R>>) = delegate.enqueue(
        object : Callback<R> {

            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@EitherCall, Response.success(response.toEither(call)))
            }

            private fun Response<R>.toEither(call: Call<R>): Either<ApiError, R> {
                // Http error response (4xx - 5xx)
                if (!isSuccessful) {
                    val errorBody = errorBody()?.string() ?: ""
//                    try {
//                        val fromJson = gson.fromJson(errorBody, RawError::class.java)
//                        if (fromJson != null)
//                            return Left(HttpError(code(), fromJson))
//                        else
//                            onFailure(call, Throwable())
                        return Left(HttpError(code(), RawError(errorBody)))
//                    } catch (err: Exception) {
//                        onFailure(call, err)
//                    }
                }

                // Http success response with body
                body()?.let { body -> return Right(body) }

                // if we defined Unit as success type it means we expected no response body
                // e.g. in case of 204 No Content
                return if (successType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    Right(Unit) as Either<ApiError, R>
                } else {
                    @Suppress("UNCHECKED_CAST")
                    Left(UnknownError("Response body was null")) as Either<ApiError, R>
                }
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                val error = when (throwable) {
                    is IOException -> NetworkError(throwable)
                    else -> UnknownApiError(throwable)
                }
                callback.onResponse(this@EitherCall, Response.success(Left(error)))
            }
        }
    )

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun clone(): Call<Either<ApiError, R>> = EitherCall(delegate.clone(), successType)

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<Either<ApiError, R>> = throw UnsupportedOperationException()

    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
}