package com.bagadesh.core

import kotlinx.coroutines.flow.Flow

/**
 * Created by bagadesh on 06/06/23.
 */
abstract class BaseBareFlowUseCase<Param, Result> {

    fun execute(param: Param): Flow<Result> {
        return try {
            executeOnBackground(param)
        }
        catch (exception: Exception) {
            throw exception
        }
    }

    protected abstract fun executeOnBackground(param: Param): Flow<Result>

}