package com.test.fdj.utils

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val exception: Exception) : Result<Nothing>()
}
