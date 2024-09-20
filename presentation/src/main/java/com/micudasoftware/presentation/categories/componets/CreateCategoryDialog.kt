package com.micudasoftware.presentation.categories.componets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.categories.componets.model.CategoryModel
import com.micudasoftware.presentation.common.theme.Alizarin
import com.micudasoftware.presentation.common.theme.Amethyst
import com.micudasoftware.presentation.common.theme.Carrot
import com.micudasoftware.presentation.common.theme.Clouds
import com.micudasoftware.presentation.common.theme.Concrete
import com.micudasoftware.presentation.common.theme.Emerald
import com.micudasoftware.presentation.common.theme.PeterRiver
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.common.theme.SunFlower
import com.micudasoftware.presentation.common.theme.Turquoise
import com.micudasoftware.presentation.common.theme.WetAsphalt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateCategoryDialog(
    onConfirm: (CategoryModel) -> Unit,
    onDismiss: () -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var selectedColor by rememberSaveable { mutableIntStateOf(Color.Blue.toArgb()) }
    val colors = listOf(
        Turquoise,
        Emerald,
        PeterRiver,
        Amethyst,
        WetAsphalt,
        SunFlower,
        Carrot,
        Alizarin,
        Clouds,
        Concrete,
    )

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(R.string.title_create_new_category),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = name,
                label = { Text(text = stringResource(R.string.text_name)) },
                onValueChange = { name = it },
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.title_select_color),
                style = MaterialTheme.typography.labelMedium,
            )
            FlowRow(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)) {
                colors.forEach { color ->
                    ColorBox(
                        color = color,
                        selected = color.toArgb() == selectedColor,
                        onClick = { selectedColor = color.toArgb() },
                    )
                }
            }
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
                    onClick = {
                        onConfirm(
                            CategoryModel(
                                name = name,
                                color = Color(selectedColor)
                            )
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.button_confirm))
                }
            }
        }
    }
}

@Composable
private fun ColorBox(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedIconButton(
        modifier = modifier,
        border = BorderStroke(2.dp, color = if (selected) color else Color.Transparent),
        shape = MaterialTheme.shapes.extraSmall,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = color,
                    shape = MaterialTheme.shapes.extraSmall,
                ),
            content = {}
        )
    }
}

@Preview
@Composable
private fun ColorBoxPreview() {
    PlanWiseTheme {
        ColorBox(color = Color.Blue, selected = true, onClick = {})
    }
}

@Preview
@Composable
private fun CreateCategoryDialogPreview() {
    PlanWiseTheme {
        CreateCategoryDialog(
            onConfirm = {},
            onDismiss = {},
        )
    }
}