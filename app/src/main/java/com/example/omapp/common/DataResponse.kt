package com.example.omapp.common

sealed class DataResponse<out T> {
    data class Success<out T>(val data: T) : DataResponse<T>()
    data class Failure(val error: ErrorResponse) : DataResponse<Nothing>()

    val isFailure get() = this is Failure
    val isSuccess get() = this is Success
}

/**
 * Lift a function into DataResponse context
 * Ex: (Int) -> String function will be a (DataResponse<Int>) -> DataResponse<String>
 *     after map function
 * In functional programming this is called a Functor
 */
fun <T, R> DataResponse<R>.map(fn: (R) -> T): DataResponse<T> =
    when (this) {
        is DataResponse.Failure -> DataResponse.Failure(error)
        is DataResponse.Success -> DataResponse.Success(fn(data))
    }

/**
 * Adds the capability of chaining functions that produces DataResponse objects
 * Ex: flatMap takes the value out of DataResponse object, applies a function and pack the result
 *     inside a DataResponse again. dataresponseObject.flatMap(useCaseA).flatMap(useCaseB)...
 * In functional programming this is called a Monad
 */
fun <T, R> DataResponse<R>.flatMap(fn: (R) -> DataResponse<T>): DataResponse<T> =
    when (this) {
        is DataResponse.Failure -> DataResponse.Failure(error)
        is DataResponse.Success -> fn(data)
    }

/**
 * Adds the capability to deal with failures in a pure way
 * Ex: Provide 2 functions that return the same type. In first one, deal with the Throwable object.
 *     In the second one deal with the success value.
 * In functional programming this is called a Foldable (not exactly)
 */
fun <T, R> DataResponse<R>.fold(foldFailure: (Throwable) -> T, foldSuccess: (R) -> T): T =
    when (this) {
        is DataResponse.Failure -> foldFailure(error)
        is DataResponse.Success -> foldSuccess(data)
    }

sealed class ErrorResponse(msg: String? = null) : Throwable(msg) {
    object NetworkError : ErrorResponse()
    class Unexpected(val msg: String? = null) : ErrorResponse(msg)
    class MalformedError(val msg: String? = null) : ErrorResponse(msg)
    object GenericError : ErrorResponse()
    object UnauthorizedError : ErrorResponse()
    object PullingError : ErrorResponse()
}

