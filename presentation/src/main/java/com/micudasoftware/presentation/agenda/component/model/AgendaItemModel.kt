package com.micudasoftware.presentation.agenda.component.model

import androidx.compose.ui.graphics.Color
import com.micudasoftware.data.repository.model.Task
import com.micudasoftware.data.repository.model.TaskCategory

data class AgendaItemModel(
    val id: Long,
    val title: String,
    val description: String,
    val time: String,
    val isDone: Boolean,
    val color: Color,
) {
    companion object {
        fun fromTask(task: Task, category: TaskCategory) = AgendaItemModel(
            id = task.id ?: 0,
            title = task.name,
            description = task.description,
            time = task.startDateTime.toString(), // Todo: Format the time
            isDone = task.isCompleted,
            color = Color(category.color),
        )
    }
}