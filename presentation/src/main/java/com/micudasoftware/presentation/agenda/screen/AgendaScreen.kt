package com.micudasoftware.presentation.agenda.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.common.ComposeViewModel
import com.micudasoftware.presentation.agenda.component.AgendaDay
import com.micudasoftware.presentation.agenda.component.AgendaItem
import com.micudasoftware.presentation.agenda.component.CircularItemSelectorDialog
import com.micudasoftware.presentation.agenda.component.DropDownButton
import com.micudasoftware.presentation.agenda.component.model.AgendaDayModel
import com.micudasoftware.presentation.agenda.component.model.AgendaItemModel
import com.micudasoftware.presentation.agenda.viewmodel.AgendaScreenEvent
import com.micudasoftware.presentation.agenda.viewmodel.AgendaScreenState
import com.micudasoftware.presentation.common.PreviewViewModel
import com.micudasoftware.presentation.common.navigation.Destination
import com.micudasoftware.presentation.common.navigation.EmptyNavigator
import com.micudasoftware.presentation.common.navigation.Navigator
import com.micudasoftware.presentation.common.theme.Alizarin
import com.micudasoftware.presentation.common.theme.Emerald
import com.micudasoftware.presentation.common.theme.PeterRiver
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.common.theme.SunFlower
import com.micudasoftware.presentation.common.theme.Turquoise
import com.micudasoftware.presentation.common.utils.toFormatedFullDate
import kotlinx.coroutines.launch

/**
 * A composable that displays the agenda screen.
 *
 * @param viewModel The view model to use.
 */
@Composable
fun AgendaScreen(
    viewModel: ComposeViewModel<AgendaScreenState, AgendaScreenEvent>,
    navigator: Navigator,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    var showMonthSelectorDialog by remember { mutableStateOf(false) }
    var showYearSelectorDialog by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onEvent(AgendaScreenEvent.Refresh)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DropDownButton(
                    modifier = Modifier.padding(8.dp),
                    text = viewState.months.firstOrNull { it.selected }?.name ?: "",
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { showMonthSelectorDialog = true }
                )
                DropDownButton(
                    modifier = Modifier.padding(8.dp),
                    text = viewState.years.firstOrNull { it.selected }?.year.toString(),
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { showYearSelectorDialog = true }
                )
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = stringResource(R.string.button_more)
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { coroutineScope.launch { navigator.navigateTo(Destination.TaskDetail()) }},
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.button_add))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        )
                ) {
                    if (viewState.days.isNotEmpty()) {
                        val listState = rememberLazyListState(
                            initialFirstVisibleItemIndex = viewState.days.firstOrNull { it.selected }
                                ?.let { viewState.days.indexOf(it) } ?: 0
                        )
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(16.dp),
                            state = listState
                        ) {
                            items(viewState.days) { model ->
                                AgendaDay(modifier = Modifier.padding(4.dp), model = model)
                            }
                        }
                    }
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = viewState.date.toFormatedFullDate(),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            if (viewState.tasks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(R.string.text_no_tasks),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 16.dp)
                ) {
                    items(viewState.tasks) { model ->
                        val context = LocalContext.current
                        val taskName = model.title.ifBlank {
                            stringResource(R.string.text_unnamed)
                        }
                        val toastText = stringResource(R.string.text_task_deleted, taskName)
                        AgendaItem(
                            modifier = Modifier.padding(bottom = 10.dp),
                            model = model,
                            onComplete = { viewModel.onEvent(AgendaScreenEvent.ChangeDoneState(model.id, it)) },
                            onOpen = {
                                coroutineScope.launch {
                                    navigator.navigateTo(Destination.TaskDetail(model.id))
                                }
                            },
                            onEdit = {
                                coroutineScope.launch {
                                    navigator.navigateTo(Destination.TaskDetail(model.id, true))
                                }
                            },
                            onRemove = {
                                viewModel.onEvent(AgendaScreenEvent.RemoveTask(model.id))
                                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                }
            }
        }
    }

    if (showMonthSelectorDialog) {
        CircularItemSelectorDialog(
            items = viewState.months,
            initialItem = viewState.months.firstOrNull { it.selected } ?: viewState.months.first(),
            title = stringResource(R.string.title_select_month),
            onConfirm = {
                viewModel.onEvent(AgendaScreenEvent.SelectMonth(it.value))
                showMonthSelectorDialog = false
            },
            onDismiss = { showMonthSelectorDialog = false }
        )
    }

    if (showYearSelectorDialog) {
        CircularItemSelectorDialog(
            items = viewState.years,
            initialItem = viewState.years.firstOrNull { it.selected } ?: viewState.years.first(),
            title = stringResource(R.string.title_select_year),
            onConfirm = {
                viewModel.onEvent(AgendaScreenEvent.SelectYear(it.year))
                showYearSelectorDialog = false
            },
            onDismiss = { showYearSelectorDialog = false }
        )
    }
}

/**
 * Preview for [AgendaScreen].
 */
@Preview
@Composable
private fun AgendaScreenPreview() {
    val agendaDaysMock = listOf(
        AgendaDayModel("Mo", 1, false, onClick = {}),
        AgendaDayModel("Tu", 2, false, onClick = {}),
        AgendaDayModel("We", 3, false, onClick = {}),
        AgendaDayModel("Th", 4, false, onClick = {}),
        AgendaDayModel("Fr", 5, true, onClick = {}),
        AgendaDayModel("Sa", 6, false, onClick = {}),
        AgendaDayModel("Su", 7, false, onClick = {}),
        AgendaDayModel("Mo", 8, false, onClick = {}),
        AgendaDayModel("Tu", 9, false, onClick = {}),
    )

    val agendaItemsMock = listOf(
        AgendaItemModel(0, "Meeting with Team", "Discuss project updates", "09:00 AM", false, Turquoise),
        AgendaItemModel(0, "Doctor Appointment", "Annual health checkup", "11:30 AM", false, Emerald),
        AgendaItemModel(0, "Lunch with Mentor", "Career advice session", "01:00 PM", true, SunFlower),
        AgendaItemModel(0, "Workout", "Gym session", "06:00 PM", false, PeterRiver),
        AgendaItemModel(0, "Book Reading", "Read 'Atomic Habits'", "08:00 PM", true, Alizarin)
    )

    PlanWiseTheme {
        AgendaScreen(
            viewModel = PreviewViewModel(
                AgendaScreenState(
                    days = agendaDaysMock,
                    tasks = agendaItemsMock
                )
            ),
            navigator = EmptyNavigator,
        )
    }
}

@Preview
@Composable
private fun AgendaScreenEmptyPreview() {
    val agendaDaysMock = listOf(
        AgendaDayModel("Mo", 1, false, onClick = {}),
        AgendaDayModel("Tu", 2, false, onClick = {}),
        AgendaDayModel("We", 3, false, onClick = {}),
        AgendaDayModel("Th", 4, false, onClick = {}),
        AgendaDayModel("Fr", 5, true, onClick = {}),
        AgendaDayModel("Sa", 6, false, onClick = {}),
        AgendaDayModel("Su", 7, false, onClick = {}),
        AgendaDayModel("Mo", 8, false, onClick = {}),
        AgendaDayModel("Tu", 9, false, onClick = {}),
    )

    PlanWiseTheme {
        AgendaScreen(
            viewModel = PreviewViewModel(
                AgendaScreenState(
                    days = agendaDaysMock,
                    tasks = emptyList()
                )
            ),
            navigator = EmptyNavigator,
        )
    }
}
