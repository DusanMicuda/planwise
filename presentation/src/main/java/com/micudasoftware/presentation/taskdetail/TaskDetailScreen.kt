package com.micudasoftware.presentation.taskdetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.categories.componets.CreateCategoryDialog
import com.micudasoftware.presentation.categories.componets.model.CategoryModel
import com.micudasoftware.presentation.common.ComposeViewModel
import com.micudasoftware.presentation.common.PreviewViewModel
import com.micudasoftware.presentation.common.component.TransparentTextField
import com.micudasoftware.presentation.common.navigation.EmptyNavigator
import com.micudasoftware.presentation.common.navigation.Navigator
import com.micudasoftware.presentation.common.utils.padding
import com.micudasoftware.presentation.taskdetail.components.DateTimeRow
import com.micudasoftware.presentation.taskdetail.components.ReminderRow
import com.micudasoftware.presentation.taskdetail.components.model.ReminderModel
import com.micudasoftware.presentation.common.theme.DarkGray
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.taskdetail.components.CategorySelectorDialog
import com.micudasoftware.presentation.taskdetail.components.CustomReminderPickerDialog
import com.micudasoftware.presentation.taskdetail.components.DatePickerDialog
import com.micudasoftware.presentation.taskdetail.components.ReminderPickerDialog
import com.micudasoftware.presentation.taskdetail.components.TimePickerDialog
import com.micudasoftware.presentation.taskdetail.components.model.DatePickerDialogState
import com.micudasoftware.presentation.taskdetail.components.model.TimePickerDialogState
import kotlinx.coroutines.launch
import java.time.temporal.ChronoUnit

/**
 * Composable function for the Task Detail screen.
 *
 * @param viewModel The [ComposeViewModel] for the Task Detail screen.
 * @param navigator The [Navigator] for the Task Detail screen.
 */
@Composable
fun TaskDetailScreen(
    viewModel: ComposeViewModel<TaskDetailState, TaskDetailEvent>,
    navigator: Navigator,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    var timePickerState: TimePickerDialogState? by remember { mutableStateOf(null) }
    var datePickerState: DatePickerDialogState? by remember { mutableStateOf(null) }
    var showReminderPickerDialog by remember { mutableStateOf(false) }
    var showCustomReminderPickerDialog by remember { mutableStateOf(false) }
    var showCategorySelectorDialog by remember { mutableStateOf(false) }
    var showCreateCategoryDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (viewState.isEditable) {
                    IconButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = { viewModel.onEvent(TaskDetailEvent.CancelEdit) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = stringResource(R.string.button_close)
                        )
                    }
                } else {
                    IconButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = { coroutineScope.launch { navigator.navigateUp() } }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = stringResource(R.string.button_back)
                        )
                    }
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = viewState.startDateTime.formattedDateLong,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )
                if (viewState.isEditable) {
                    val context = LocalContext.current
                    val taskName = viewState.title.ifBlank {
                        stringResource(R.string.text_unnamed)
                    }
                    val toastText = stringResource(R.string.text_task_saved, taskName)
                    IconButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            viewModel.onEvent(TaskDetailEvent.SaveTask)
                            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = stringResource(R.string.button_save)
                        )
                    }
                } else {
                    IconButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = { viewModel.onEvent(TaskDetailEvent.EditTask) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = stringResource(R.string.button_edit)
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
                        .clickable(
                            enabled = viewState.isEditable,
                            onClick = { showCategorySelectorDialog = true }
                        )
                        .padding(top = 24.dp, bottom = 8.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = viewState.category?.color
                                    ?: MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.extraSmall,
                            )
                            .border(
                                width = 1.dp,
                                color = viewState.category?.color ?: DarkGray,
                                shape = MaterialTheme.shapes.extraSmall
                            ),
                        content = {}
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .weight(1f),
                        text = viewState.category?.name ?: stringResource(R.string.title_select_category),
                        style = MaterialTheme.typography.labelMedium,
                        color = DarkGray
                    )
                    if (viewState.isEditable) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = stringResource(R.string.button_edit)
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(vertical = 8.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = viewState.isCompleted,
                        onCheckedChange = { viewModel.onEvent(TaskDetailEvent.ToggleDoneState(it)) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color.Black
                        )
                    )
                    TransparentTextField(
                        modifier = Modifier.weight(1f),
                        value = viewState.title,
                        placeholder = {
                            Text(
                                text = stringResource(R.string.text_enter_title),
                                style = MaterialTheme.typography.titleLarge,
                            )
                      },
                        onValueChange = { viewModel.onEvent(TaskDetailEvent.ChangeTitle(it)) },
                        textStyle = MaterialTheme.typography.titleLarge,
                        singleLine = true,
                        readOnly = !viewState.isEditable
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                TransparentTextField(
                    modifier = Modifier.padding(16.dp),
                    value = viewState.description,
                    placeholder = {
                        Text(
                            text = "Description...",
                            style = MaterialTheme.typography.bodyLarge,
                            fontStyle = FontStyle.Italic
                        )
                    },
                    onValueChange = { viewModel.onEvent(TaskDetailEvent.ChangeDescription(it)) },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    minLines = 4,
                    readOnly = !viewState.isEditable
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                DateTimeRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = stringResource(R.string.text_date_from),
                    time = viewState.startDateTime.formattedTime,
                    date = viewState.startDateTime.formattedDateShort,
                    isEditable = viewState.isEditable,
                    onEditTime = {
                        timePickerState = TimePickerDialogState(
                            onConfirm = {
                                viewModel.onEvent(TaskDetailEvent.ChangeStartTime(it))
                                timePickerState = null
                            },
                            onDismiss = { timePickerState = null }
                        )
                    },
                    onEditDate = {
                        datePickerState = DatePickerDialogState(
                            onConfirm = {
                                viewModel.onEvent(TaskDetailEvent.ChangeStartDate(it))
                                datePickerState = null
                            },
                            onDismiss = { datePickerState = null }
                        )
                    },
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                DateTimeRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = stringResource(R.string.text_date_to),
                    time = viewState.endDateTime.formattedTime,
                    date = viewState.endDateTime.formattedDateShort,
                    isEditable = viewState.isEditable,
                    onEditTime = {
                        timePickerState = TimePickerDialogState(
                            onConfirm = {
                                viewModel.onEvent(TaskDetailEvent.ChangeEndTime(it))
                                timePickerState = null
                            },
                            onDismiss = { timePickerState = null }
                        )
                    },
                    onEditDate = {
                        datePickerState = DatePickerDialogState(
                            onConfirm = {
                                viewModel.onEvent(TaskDetailEvent.ChangeEndDate(it))
                                datePickerState = null
                            },
                            onDismiss = { datePickerState = null }
                        )
                    },

                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, horizontal = 16.dp)
                        .heightIn(min = 48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.title_reminders),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    if (viewState.isEditable) {
                        IconButton(
                            onClick = { showReminderPickerDialog = true },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.button_add)
                            )
                        }
                    }
                }
                LazyColumn {
                    items(viewState.reminders) { reminder ->
                        ReminderRow(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            title = reminder.title,
                            isEditable = viewState.isEditable,
                            onRemove = { viewModel.onEvent(TaskDetailEvent.RemoveReminder(reminder)) }
                        )
                    }
                }
                if (viewState.id != null && !viewState.isEditable) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        val context = LocalContext.current
                        val taskName = viewState.title.ifBlank {
                            stringResource(R.string.text_unnamed)
                        }
                        val toastText = stringResource(R.string.text_task_deleted, taskName)
                        Box(
                            modifier = Modifier.clickable(
                                onClick = {
                                    viewModel.onEvent(TaskDetailEvent.DeleteTask)
                                    Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                                }
                            )
                        ) {
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
                                    text = stringResource(R.string.button_delete_task),
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
    timePickerState?.let {
        TimePickerDialog(state = it)
    }

    datePickerState?.let {
        DatePickerDialog(state = it)
    }

    if (showReminderPickerDialog) {
        ReminderPickerDialog(
            onConfirm = {
                viewModel.onEvent(TaskDetailEvent.AddReminder(it))
                showReminderPickerDialog = false
            },
            onCustom = {
                showReminderPickerDialog = false
                showCustomReminderPickerDialog = true
            },
            onDismiss = { showReminderPickerDialog = false }
        )
    }

    if (showCustomReminderPickerDialog) {
        CustomReminderPickerDialog(
            onConfirm = {
                viewModel.onEvent(TaskDetailEvent.AddReminder(it))
                showCustomReminderPickerDialog = false
            },
            onDismiss = { showCustomReminderPickerDialog = false }
        )
    }

    if (showCategorySelectorDialog) {
        CategorySelectorDialog(
            categories = viewState.categories,
            onSelect = {
                viewModel.onEvent(TaskDetailEvent.ChangeCategory(it))
                showCategorySelectorDialog = false
            },
            onAddNew = {
                showCategorySelectorDialog = false
                showCreateCategoryDialog = true
            },
            onDismiss = { showCategorySelectorDialog = false }
        )
    }

    if (showCreateCategoryDialog) {
        CreateCategoryDialog(
            onConfirm = {
                viewModel.onEvent(TaskDetailEvent.CreateCategory(it))
                showCreateCategoryDialog = false
                showCategorySelectorDialog = true
            },
            onDismiss = { showCreateCategoryDialog = false }
        )
    }

    viewState.alertDialogTextRes?.let { textRes ->
        AlertDialog(
            title = { Text(stringResource(R.string.title_warning)) },
            text = { Text(stringResource(textRes)) },
            onDismissRequest = { viewModel.onEvent(TaskDetailEvent.ConfirmDialog) },
            confirmButton = {
                Button(onClick = { viewModel.onEvent(TaskDetailEvent.ConfirmDialog) }) {
                    Text(stringResource(R.string.button_confirm))
                }
            },
        )
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
                    category = CategoryModel(
                        id = 0,
                        name = "Work",
                        color = Color.Green
                    ),
                    title = "Project X",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    reminders = listOf(
                        ReminderModel(
                            value = 15,
                            unit = ChronoUnit.MINUTES
                        ),
                        ReminderModel(
                            value = 1,
                            unit = ChronoUnit.HOURS
                        ),
                    )
                )
            ),
            navigator = EmptyNavigator
        )
    }
}

/**
 * Preview for [TaskDetailScreen] in editable mode.
 */
@Preview
@Composable
private fun TaskDetailScreenEditablePreview() {
    PlanWiseTheme {
        TaskDetailScreen(
            viewModel = PreviewViewModel(
                TaskDetailState(
                    isEditable = true,
                    category = CategoryModel(
                        id = 0,
                        name = "Work",
                        color = Color.Green
                    ),
                    title = "Project X",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    reminders = listOf(
                        ReminderModel(
                            value = 15,
                            unit = ChronoUnit.MINUTES
                        ),
                        ReminderModel(
                            value = 1,
                            unit = ChronoUnit.HOURS
                        ),
                    )
                )
            ),
            navigator = EmptyNavigator
        )
    }
}

@Preview
@Composable
private fun TaskDetailScreenEmptyPreview() {
    PlanWiseTheme {
        TaskDetailScreen(
            viewModel = PreviewViewModel(
                TaskDetailState(isEditable = true)
            ),
            navigator = EmptyNavigator
        )
    }
}