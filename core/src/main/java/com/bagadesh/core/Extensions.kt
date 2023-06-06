@file:Suppress("NOTHING_TO_INLINE")

package com.bagadesh.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by bagadesh on 07/06/23.
 */


inline fun Any?.isNull(): Boolean {
    return this == null
}

inline fun Any?.isNotNull(): Boolean {
    return this != null
}


/**
 * This is can be used where you don't need to care about failure case
 */
fun <T, R> BaseUseCase<T, R>.flowIt(param: T): Flow<R> {
    return flow {
        when (val result = execute(param)) {
            is Data.Failure -> {}
            is Data.Success -> {
                emit(result.data)
            }
        }
    }
}

fun <T, R, Q> BaseUseCase<T, R>.flowIt(param: T, errorHandling: ((Exception) -> Q)? = null, transform: (R) -> Q): Flow<Q> {
    return flow {
        when (val result = execute(param)) {
            is Data.Failure -> {
                if (errorHandling != null) {
                    emit(errorHandling(result.exception))
                }
            }

            is Data.Success -> {
                emit(transform(result.data))
            }
        }
    }
}
