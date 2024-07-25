package com.micudasoftware.planwise.presentation.agenda.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micudasoftware.planwise.R
import com.micudasoftware.planwise.presentation.agenda.component.model.AgendaItemModel
import com.micudasoftware.planwise.presentation.common.isColorDark
import com.micudasoftware.planwise.ui.theme.PlanWiseTheme

/**
 * A composable that displays an agenda item.
 *
 * @param model The model that contains the data to display
 * @param modifier The modifier to apply to this layout
 */
@Composable
fun AgendaItem(
    model: AgendaItemModel,
    modifier: Modifier = Modifier,
) {
    var menuIsExpanded by rememberSaveable { mutableStateOf(false) }

    Card(modifier = modifier) {
        Column(modifier = Modifier
            .background(color = model.color)
            .padding(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = model.isDone,
                    onCheckedChange = {},
                    colors = if (isColorDark(model.color)) {
                        CheckboxDefaults.colors(
                            checkedColor = Color.White,
                            uncheckedColor = Color.White
                        )
                    } else {
                        CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color.Black
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
                        contentDescription = "More"
                    )
                    DropdownMenu(
                        expanded = menuIsExpanded,
                        onDismissRequest = { menuIsExpanded = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Open") },
                            onClick = model.onOpen
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Edit") },
                            onClick = model.onEdit
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Remove") },
                            onClick = model.onRemove
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
                    .padding(top = 8.dp, end = 16.dp),
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
                title = "Title",
                description = "Description",
                time = "Mar 6, 12:00 - 13:00",
                isDone = false,
                color = Color(0xFF279F70),
                onChangeState = { },
                onOpen = {},
                onEdit = {},
                onRemove = {}
            )
        )
    }
}