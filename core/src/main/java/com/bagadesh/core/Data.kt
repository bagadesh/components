package com.bagadesh.core

/**
 * Created by bagadesh on 06/06/23.
 */

sealed class Data<T> {

    data class Success<T>(val data: T) : Data<T>()

    data class Failure<T>(val exception: Exception) : Data<T>()

}