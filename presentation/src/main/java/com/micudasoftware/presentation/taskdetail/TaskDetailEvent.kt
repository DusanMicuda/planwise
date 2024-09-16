package com.micudasoftware.presentation.taskdetail

import com.micudasoftware.presentation.common.UIEvent
import com.micudasoftware.presentation.taskdetail.components.model.ReminderModel
import java.time.OffsetDateTime
import java.time.OffsetTime

sealed class TaskDetailEvent: UIEvent {

    data object EditTask: TaskDetailEvent()

    data object SaveTask: TaskDetailEvent()

    data object DeleteTask: TaskDetailEvent()

    data object CancelEdit: TaskDetailEvent()

    data object NavigateBack: TaskDetailEvent()

    data class ToggleDoneState(val isDone: Boolean): TaskDetailEvent()

    data class ChangeTitle(val title: String): TaskDetailEvent()

    data class ChangeDescription(val description: String): TaskDetailEvent()

    data class ChangeStartTime(val time: OffsetTime): TaskDetailEvent()

    data class ChangeStartDate(val date: OffsetDateTime): TaskDetailEvent()

    data class ChangeEndTime(val time: OffsetTime): TaskDetailEvent()

    data class ChangeEndDate(val date: OffsetDateTime): TaskDetailEvent()

    data class AddReminder(val reminder: ReminderModel): TaskDetailEvent()

    data class RemoveReminder(val reminder: ReminderModel): TaskDetailEvent()
}
