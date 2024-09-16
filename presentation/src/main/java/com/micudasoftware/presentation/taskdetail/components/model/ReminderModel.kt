package com.micudasoftware.presentation.taskdetail.components.model

import java.time.Duration
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

/**
 * Model for a reminder of a task.
 *
 * @property value The value of the reminder.
 * @property unit The unit of the reminder.
 * @property title The title of the reminder.
 */
data class ReminderModel(
    val value: Int,
    val unit: ChronoUnit
) {
    val title: String = "$value ${unit.name.lowercase()} before" // Todo localize
    val titleWithoutValue: String = "${unit.name.lowercase()} before" // Todo localize

    companion object {
        fun fromDateTimeDifference(startDateTime: OffsetDateTime, reminderDateTime: OffsetDateTime): ReminderModel {
            val difference = Duration.between(reminderDateTime, startDateTime)
            val differanceMinutes = difference.toMinutes()
            val (value, unit) = when {
                differanceMinutes < 60 -> differanceMinutes.toInt() to ChronoUnit.MINUTES
                differanceMinutes % 60 == 0L -> difference.toHours().toInt() to ChronoUnit.HOURS
                differanceMinutes % 1440 == 0L -> difference.toDays().toInt() to ChronoUnit.DAYS
                else -> differanceMinutes.toInt() to ChronoUnit.MINUTES
            }
            return ReminderModel(value, unit)
        }
    }

    fun toOffsetDateTime(startDateTime: OffsetDateTime): OffsetDateTime {
        return startDateTime.minus(value.toLong(), unit)
    }
}
