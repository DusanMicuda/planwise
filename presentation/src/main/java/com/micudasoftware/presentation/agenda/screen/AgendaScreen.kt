package com.micudasoftware.presentation.agenda.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.micudasoftware.presentation.common.ComposeViewModel
import com.micudasoftware.presentation.common.PreviewViewModel
import com.micudasoftware.presentation.agenda.component.AgendaDay
import com.micudasoftware.presentation.agenda.component.AgendaItem
import com.micudasoftware.presentation.agenda.component.DropDownButton
import com.micudasoftware.presentation.agenda.component.model.AgendaDayModel
import com.micudasoftware.presentation.agenda.component.model.AgendaItemModel
import com.micudasoftware.presentation.agenda.viewmodel.AgendaScreenEvent
import com.micudasoftware.presentation.agenda.viewmodel.AgendaScreenState
import com.micudasoftware.presentation.common.theme.PlanWiseTheme

/**
 * A composable that displays the agenda screen.
 *
 * @param viewModel The view model to use.
 */
@Composable
fun AgendaScreen(
    viewModel: ComposeViewModel<AgendaScreenState, AgendaScreenEvent>
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
                DropDownButton(
                    modifier = Modifier.padding(8.dp),
                    text = viewState.month,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { /*TODO*/ }
                )
                DropDownButton(
                    modifier = Modifier.padding(8.dp),
                    text = viewState.year,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { /*TODO*/ }
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
                            contentDescription = "More"
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
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
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(viewState.days) { model ->
                            AgendaDay(modifier = Modifier.padding(4.dp), model = model)
                        }
                    }
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Today",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 16.dp)
            ) {
                items(viewState.items) { model ->
                    AgendaItem(modifier = Modifier.padding(bottom = 10.dp), model = model)
                }
            }
        }
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
        AgendaItemModel("Meeting with Team", "Discuss project updates", "09:00 AM", false, Color.Blue, onChangeState = {}, onEdit = {}, onRemove = {}, onOpen = {}),
        AgendaItemModel("Doctor Appointment", "Annual health checkup", "11:30 AM", false, Color.LightGray, onChangeState = {}, onEdit = {}, onRemove = {}, onOpen = {}),
        AgendaItemModel("Lunch with Mentor", "Career advice session", "01:00 PM", true, Color.Cyan, onChangeState = {}, onEdit = {}, onRemove = {}, onOpen = {}),
        AgendaItemModel("Workout", "Gym session", "06:00 PM", false, Color.Green, onChangeState = {}, onEdit = {}, onRemove = {}, onOpen = {}),
        AgendaItemModel("Book Reading", "Read 'Atomic Habits'", "08:00 PM", true, Color.Magenta, onChangeState = {}, onEdit = {}, onRemove = {}, onOpen = {})
    )

    PlanWiseTheme {
        AgendaScreen(
            viewModel = PreviewViewModel(
                AgendaScreenState(
                    month = "November",
                    year = "2024",
                    days = agendaDaysMock,
                    items = agendaItemsMock
                )
            )
        )
    }
}