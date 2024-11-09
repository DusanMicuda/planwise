package com.micudasoftware.presentation.agenda.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.common.theme.PlanWiseTheme

/**
 * A composable that displays a circular item selector dialog.
 *
 * @param items The list of items to display.
 * @param initialItem The item to be selected initially.
 * @param title The title of the dialog.
 * @param onConfirm The callback to be called when the confirm button is clicked.
 * @param onDismiss The callback to be called when the dialog is dismissed.
 */
@Composable
fun <T> CircularItemSelectorDialog(
    items: List<T>,
    initialItem: T,
    title: String,
    onConfirm: (item: T) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var selectedItem by remember { mutableStateOf(initialItem) }

            Text(
                text = title,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
            CircularItemSelector(
                modifier = Modifier.padding(horizontal = 24.dp),
                items = items,
                initialItem = initialItem,
                textStyle = MaterialTheme.typography.labelMedium,
                onItemSelected = { selectedItem = it }
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = onDismiss) {
                    Text(text = stringResource(R.string.button_cancel))
                }
                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = { onConfirm(selectedItem) }
                ) {
                    Text(text = stringResource(R.string.button_confirm))
                }
            }
        }
    }
}

/**
 * Preview for the [CircularItemSelectorDialog].
 */
@Preview
@Composable
fun CircularItemSelectorDialogPreview() {
    PlanWiseTheme {
        CircularItemSelectorDialog(
            items = listOf("Item 1", "Item 2", "Item 3"),
            initialItem = "Item 2",
            title = "Select an item",
            onConfirm = {},
            onDismiss = {}
        )
    }
}
