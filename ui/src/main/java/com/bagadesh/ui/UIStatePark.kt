package com.bagadesh.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Created by bagadesh on 06/06/23.
 */

@Composable
fun <T> UIStatePark(
    state: UiState<T>,
    empty: @Composable (() -> Unit)? = null,
    failure: @Composable (() -> Unit)? = null,
    success: @Composable (T) -> Unit,
) {
    Column(modifier = Modifier.animateContentSize()) {
        when (state) {
            is UiState.Empty -> {
                empty?.invoke()
            }
            is UiState.Failure -> {
                failure?.invoke() ?: Text(text = state.message)
            }
            is UiState.Success -> {
                success(state.data)
            }
        }
    }
}