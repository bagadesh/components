package com.bagadesh.ui

import androidx.compose.runtime.Stable

/**
 * Created by bagadesh on 06/06/23.
 */

@Stable
sealed interface UiState<T> {

    data class Success<T>(val data: T) : UiState<T>

    data class Failure<T>(val message: String) : UiState<T>

    data class Empty<T>(val emptyMessage: String = "") : UiState<T>
}