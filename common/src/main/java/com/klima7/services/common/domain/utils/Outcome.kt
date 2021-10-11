package com.klima7.services.common.domain.utils


sealed class Outcome<out L, out R> {

    data class Failure<out L>(val a: L) : Outcome<L, Nothing>()

    data class Success<out R>(val b: R) : Outcome<Nothing, R>()

    val isSuccess get() = this is Success<R>

    val isFailure get() = this is Failure<L>

    fun <T> fold(fnFailure: (L) -> T, fnSuccess: (R) -> T): T {
        return when (this) {
            is Failure -> fnFailure(a)
            is Success -> fnSuccess(b)
        }
    }

    suspend fun <T> foldS(fnFailure: suspend (L) -> T, fnSuccess: suspend (R) -> T): T {
        return when (this) {
            is Failure -> fnFailure(a)
            is Success -> fnSuccess(b)
        }
    }
}

fun <L, R> Outcome<L, R>.onFailure(fn: (failure: L) -> Unit): Outcome<L, R> {
    return apply { if (this is Outcome.Failure) fn(a) }
}

fun <L, R> Outcome<L, R>.onSuccess(fn: (success: R) -> Unit): Outcome<L, R> {
    return apply { if (this is Outcome.Success) fn(b) }
}

fun <L, R> Outcome<L, R>.getOrElse(value: R): R {
    return when (this) {
        is Outcome.Failure -> value
        is Outcome.Success -> b
    }
}

fun <T, L, R> Outcome<L, R>.map(fn: (R) -> (T)): Outcome<L, T> {
    return when (this) {
        is Outcome.Failure -> Outcome.Failure(a)
        is Outcome.Success -> Outcome.Success(fn(b))
    }
}

fun <T, L, R> Outcome<L, R>.flatMap(fn: (R) -> Outcome<L, T>): Outcome<L, T> {
    return when (this) {
        is Outcome.Failure -> Outcome.Failure(a)
        is Outcome.Success -> fn(b)
    }
}


class None