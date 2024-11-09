package com.micudasoftware.presentation.agenda.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.agenda.component.model.AgendaItemModel
import com.micudasoftware.presentation.common.utils.isColorDark
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.common.utils.padding

/**
 * A composable that displays an agenda item.
 *
 * @param model The model that contains the data to display
 * @param modifier The modifier to apply to this layout
 * @param onComplete The callback to be called when the checkbox state changes
 * @param onOpen The callback to be called when the item is opened
 * @param onEdit The callback to be called when the item is edited
 * @param onRemove The callback to be called when the item is removed
 */
@Composable
fun AgendaItem(
    model: AgendaItemModel,
    modifier: Modifier = Modifier,
    onComplete: (Boolean) -> Unit,
    onOpen: () -> Unit,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
) {
    var menuIsExpanded by rememberSaveable { mutableStateOf(false) }

    Card(modifier = modifier) {
        Column(modifier = Modifier
            .clickable(onClick = onOpen)
            .background(color = model.color)
            .padding(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = model.isDone,
                    onCheckedChange = onComplete,
                    colors = if (isColorDark(model.color)) {
                        CheckboxDefaults.colors(
                            checkedColor = Color.White,
                            uncheckedColor = Color.White,
                            checkmarkColor = Color.Black,
                        )
                    } else {
                        CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color.Black,
                            checkmarkColor = Color.White,
                        )
                    }
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = model.title,
                    color = if (isColorDark(model.color)) Color.White else Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = { menuIsExpanded = true }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_more_horiz),
                        tint = if (isColorDark(model.color)) Color.White else Color.Black,
                        contentDescription = stringResource(R.string.button_more)
                    )
                    DropdownMenu(
                        expanded = menuIsExpanded,
                        onDismissRequest = { menuIsExpanded = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Open") },
                            onClick = {
                                onOpen()
                                menuIsExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Edit") },
                            onClick = {
                                onEdit()
                                menuIsExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Remove") },
                            onClick = {
                                onRemove()
                                menuIsExpanded = false
                            }
                        )
                    }
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp)
                    .padding(start = 48.dp, end = 16.dp),
                text = model.description,
                color = if (isColorDark(model.color)) Color.White else Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, end = 16.dp),
                text = model.time,
                textAlign = TextAlign.End,
                color = if (isColorDark(model.color)) Color.White else Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


/**
 * Preview for [AgendaItem].
 */
@Preview
@Composable
private fun AgendaItemPreview() {
    PlanWiseTheme {
        AgendaItem(
            model = AgendaItemModel(
                id = 0,
                title = "Title",
                description = "Description",
                time = "Mar 6, 12:00 - 13:00",
                isDone = false,
                color = Color(0xFF279F70),
            ),
            onComplete = {},
            onOpen = {},
            onEdit = {},
            onRemove = {}
        )
    }
}