package com.micudasoftware.presentation.agenda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micudasoftware.data.repository.TasksRepository
import com.micudasoftware.presentation.agenda.component.model.AgendaDayModel
import com.micudasoftware.presentation.agenda.component.model.AgendaItemModel
import com.micudasoftware.presentation.common.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val repository: TasksRepository
) : ViewModel(), ComposeViewModel<AgendaScreenState, AgendaScreenEvent> {

    private val _viewState = MutableStateFlow(AgendaScreenState())
    override val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val currentDate = LocalDate.now()

            val dayFormatter = DateTimeFormatter.ofPattern("eee")
            val monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.getDefault())

            val monthString = currentDate.format(monthFormatter)

            val firstDayOfMonth = currentDate.withDayOfMonth(1)
            val lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())
            val days = (firstDayOfMonth.dayOfMonth..lastDayOfMonth.dayOfMonth).map { day ->
                val dayString = currentDate.withDayOfMonth(day).format(dayFormatter)
                AgendaDayModel(
                    dayString = dayString,
                    dayNumber = day,
                    selected = currentDate.dayOfMonth == day,
                    onClick = { /*TODO*/ }
                )
            }

            val tasks = repository.getTasksForDay(currentDate)
            val categories = repository.getTaskCategories()

            _viewState.update { state ->
                state.copy(
                    month = monthString,
                    year = currentDate.year.toString(),
                    days = days,
                    tasks = tasks.map { task ->
                        AgendaItemModel.fromTask(
                            task = task,
                            category = categories.first { it.id == task.categoryId }
                        )
                    }
                )
            }
        }
    }

    override fun onEvent(event: AgendaScreenEvent) {
        TODO("Not yet implemented")
    }


}