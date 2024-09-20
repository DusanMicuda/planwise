package com.micudasoftware.presentation.categories.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.categories.componets.model.CategoryModel

@Composable
fun CategoryRow(
    model: CategoryModel,
    onRemove: (CategoryModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(20.dp)
                    .background(
                        color = model.color,
                        shape = MaterialTheme.shapes.extraSmall,
                    ),
                content = {}
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f),
                text = model.name,
                style = MaterialTheme.typography.labelMedium,
            )
            IconButton(
                modifier = Modifier,
                onClick = { onRemove(model) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = stringResource(R.string.button_remove)
                )
            }
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
private fun CategoryRowPreview() {
    PlanWiseTheme {
        CategoryRow(
            model = CategoryModel(
                id = 1,
                name = "Category",
                color = Color.Blue
            ),
            onRemove = {}
        )
    }
}