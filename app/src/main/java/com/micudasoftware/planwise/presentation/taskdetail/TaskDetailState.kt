package com.micudasoftware.planwise.presentation.taskdetail

import com.micudasoftware.planwise.presentation.taskdetail.components.model.CategoryModel
import com.micudasoftware.planwise.presentation.common.UIState
import com.micudasoftware.planwise.presentation.taskdetail.components.model.ReminderModel

data class TaskDetailState(
    val isEditable: Boolean = false,
    val date: String = "",
    val category: CategoryModel,
    val title: String = "",
    val description: String = "",
    val reminders: List<ReminderModel> = emptyList()
): UIState
