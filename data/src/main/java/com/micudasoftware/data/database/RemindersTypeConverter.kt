package com.micudasoftware.data.database

import androidx.room.TypeConverter

/**
 * A type converter class for converting between a list of reminders (as Long values) and a String.
 * This is used by Room to store lists of reminders in a single database column.
 */
class RemindersTypeConverter {

    /**
     * Converts a list of Long reminders to a single String.
     *
     * @param reminders The list of Long reminders to convert.
     * @return A String representation of the list of reminders, with each reminder separated by a comma.
     */
    @TypeConverter
    fun fromRemindersList(reminders: List<Long>): String {
        return reminders.joinToString(separator = ",")
    }

    /**
     * Converts a String representation of a list of reminders back to a list of Long values.
     *
     * @param data The String representation of the list of reminders.
     * @return A list of Long reminders. If the input string is empty, returns an empty list.
     */
    @TypeConverter
    fun toRemindersList(data: String): List<Long> {
        return if (data.isEmpty()) {
            emptyList()
        } else {
            data.split(",").map { it.toLong() }
        }
    }
}