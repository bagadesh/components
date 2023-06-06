package com.bagadesh.core

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by bagadesh on 06/06/23.
 */

abstract class BaseFlowUseCase<Param, Result> {

    fun execute(param: Param): Flow<Data<Result>> {
        return try {
            executeOnBackground(param)
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (exception: Exception) {
            flow {
                emit(Data.Failure(exception))
            }
        }
    }

    protected abstract fun executeOnBackground(param: Param): Flow<Data<Result>>

}