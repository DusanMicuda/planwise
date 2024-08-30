package com.micudasoftware.planwise.presentation.taskdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.micudasoftware.planwise.presentation.taskdetail.components.model.CategoryModel
import com.micudasoftware.planwise.presentation.common.ComposeViewModel
import com.micudasoftware.planwise.presentation.common.PreviewViewModel
import com.micudasoftware.planwise.presentation.common.padding
import com.micudasoftware.planwise.presentation.taskdetail.components.DateTimeRow
import com.micudasoftware.planwise.presentation.taskdetail.components.ReminderRow
import com.micudasoftware.planwise.presentation.taskdetail.components.model.ReminderModel
import com.micudasoftware.planwise.ui.theme.DarkGray
import com.micudasoftware.planwise.ui.theme.PlanWiseTheme

@Composable
fun TaskDetailScreen(
    viewModel: ComposeViewModel<TaskDetailState, TaskDetailEvent>
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.padding(8.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "Close"
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = viewState.date,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )
                if (viewState.isEditable) {
                    IconButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = "Done"
                        )
                    }
                } else {
                    IconButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = "Edit"
                        )
                    }
                }
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .clickable(enabled = viewState.isEditable, onClick = { /*TODO*/ })
                        .padding(top = 24.dp, bottom = 8.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = viewState.category.color,
                                shape = MaterialTheme.shapes.extraSmall,
                            ),
                        content = {}
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .weight(1f),
                        text = viewState.category.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = DarkGray
                    )
                    if (viewState.isEditable) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Edit"
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .clickable(enabled = viewState.isEditable, onClick = { /*TODO*/ })
                        .padding(vertical = 8.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color.Black
                        )
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = viewState.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                    if (viewState.isEditable) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Edit"
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .clickable(enabled = viewState.isEditable, onClick = { /*TODO*/ })
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = viewState.description,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    if (viewState.isEditable) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Edit"
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                DateTimeRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = "From",
                    time = "08:00",
                    date = "Jul 12, 2022",
                    isEditable = viewState.isEditable,
                    onEditTime = { /*TODO*/ },
                    onEditDate = { /*TODO*/ },
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                DateTimeRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = "To",
                    time = "08:30",
                    date = "Jul 12, 2022",
                    isEditable = viewState.isEditable,
                    onEditTime = { /*TODO*/ },
                    onEditDate = { /*TODO*/ },

                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Text(
                    modifier = Modifier.padding(top = 24.dp, horizontal = 16.dp),
                    text = "Reminders",
                    style = MaterialTheme.typography.titleMedium,
                )
                LazyColumn(
                    modifier = Modifier.padding(top = 16.dp),
                ) {
                    items(viewState.reminders) { reminder ->
                        ReminderRow(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            title = reminder.title,
                            isEditable = viewState.isEditable,
                            onClick = { /*TODO*/ }
                        )
                    }
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Box(modifier = Modifier.clickable(onClick = { /*TODO*/ })) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            )
                            Text(
                                modifier = Modifier.padding(bottom = 16.dp),
                                text = "Delete Task",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Preview for [TaskDetailScreen].
 */
@Preview
@Composable
private fun TaskDetailScreenPreview() {
    PlanWiseTheme {
        TaskDetailScreen(
            viewModel = PreviewViewModel(
                TaskDetailState(
                    isEditable = false,
                    date = "01 MARCH 2022",
                    category = CategoryModel(
                        name = "Work",
                        color = Color.Green
                    ),
                    title = "Project X",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    reminders = listOf(
                        ReminderModel(
                            title = "15 minutes before"
                        ),
                        ReminderModel(
                            title = "1 hour before"
                        ),
                    )
                )
            )
        )
    }
}

/**
 * Preview for [TaskDetailScreen].
 */
@Preview
@Composable
private fun TaskDetailScreenEditablePreview() {
    PlanWiseTheme {
        TaskDetailScreen(
            viewModel = PreviewViewModel(
                TaskDetailState(
                    isEditable = true,
                    date = "01 MARCH 2022",
                    category = CategoryModel(
                        name = "Work",
                        color = Color.Green
                    ),
                    title = "Project X",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    reminders = listOf(
                        ReminderModel(
                            title = "15 minutes before"
                        ),
                        ReminderModel(
                            title = "1 hour before"
                        ),
                    )
                )
            )
        )
    }
}