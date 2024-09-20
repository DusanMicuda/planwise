package com.micudasoftware.presentation.taskdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.categories.componets.model.CategoryModel

@Composable
fun CategorySelectorDialog(
    categories: List<CategoryModel>,
    onSelect: (CategoryModel) -> Unit,
    onAddNew: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.title_select_category),
                style = MaterialTheme.typography.titleMedium
            )
            if (categories.isEmpty()) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.text_no_categories),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            LazyColumn {
                items(categories) {
                    CategorySelectorDialogRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp, vertical = 12.dp),
                        model = it,
                        onClick = { onSelect(it) }
                    )
                }
            }
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = onAddNew,
            ) {
                Text(text = stringResource(R.string.button_create_new_category))
            }
        }
    }
}

@Composable
private fun CategorySelectorDialogRow(
    model: CategoryModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(modifier),
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(
                    color = model.color,
                    shape = MaterialTheme.shapes.extraSmall,
                ),
            content = {}
        )
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            text = model.name,
        )
    }
}

@Preview
@Composable
private fun CategorySelectorDialogRowPreview() {
    PlanWiseTheme {
        CategorySelectorDialogRow(
            model = CategoryModel(
                id = 0,
                name = "Work",
                color = Color.Blue
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CategorySelectorDialogPreview() {
    PlanWiseTheme {
        CategorySelectorDialog(
            categories = listOf(
                CategoryModel(
                    id = 0,
                    name = "Work",
                    color = Color.Blue
                ),
                CategoryModel(
                    id = 1,
                    name = "Personal",
                    color = Color.Yellow
                ),
                CategoryModel(
                    id = 2,
                    name = "Shopping",
                    color = Color.Green
                ),
            ),
            onSelect = {},
            onAddNew = {},
            onDismiss = {}
        )
    }
}

@Preview
@Composable
private fun CategorySelectorDialogEmptyPreview() {
    PlanWiseTheme {
        CategorySelectorDialog(
            categories = emptyList(),
            onSelect = {},
            onAddNew = {},
            onDismiss = {}
        )
    }
}