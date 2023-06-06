package com.bagadesh.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Created by bagadesh on 07/06/23.
 */

@Preview
@Composable
fun RetryDialogScreenPreview() {
    RetryDialogScreen(
        show = { true },
        title = "API Failed",
        body = "Due to unexpected reason API failed",
        onDismissRequest = {

        },
        onConfirmClick = {

        }
    )
}

@Composable
fun RetryDialogScreen(
    modifier: Modifier = Modifier,
    show: () -> Boolean,
    title: String,
    body: String,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit
) {
    if (show()) {
        AlertDialog(
            modifier = modifier then Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(),
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = {
                    onDismissRequest()
                    onConfirmClick()
                }) {
                    Text(text = stringResource(id = R.string.retry_button_text))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = stringResource(id = R.string.dismiss_button_text))
                }
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = body)
            },
        )
    }
}