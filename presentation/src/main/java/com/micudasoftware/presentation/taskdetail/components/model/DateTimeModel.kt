package com.micudasoftware.presentation.taskdetail.components.model

import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter

/**
 * A model class for handling date and time formatting.
 *
 * @property offsetDateTime The OffsetDateTime instance to be formatted.
 * @property formattedDateLong The formatted date in the pattern "dd LLLL yyyy".
 * @property formattedDateShort The formatted date in the pattern "MMM dd, yyyy".
 * @property formattedTime The formatted time in the pattern "HH:mm".
 */
class DateTimeModel(
    val offsetDateTime: OffsetDateTime,
) {
    val formattedDateLong: String
    val formattedDateShort: String
    val formattedTime: String

    init {
        val longDateFormatter = DateTimeFormatter.ofPattern("dd LLLL yyyy")
        val shortDateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        formattedDateLong = offsetDateTime.format(longDateFormatter).uppercase()
        formattedDateShort = offsetDateTime.format(shortDateFormatter)
        formattedTime = offsetDateTime.format(timeFormatter)
    }

    /**
     * Creates a copy of the current DateTimeModel with a new time.
     *
     * @param time The new OffsetTime to be set.
     * @return A new DateTimeModel instance with the updated time.
     */
    fun copy(time: OffsetTime): DateTimeModel {
        return DateTimeModel(offsetDateTime.with(time))
    }

    /**
     * Creates a copy of the current DateTimeModel with a new date.
     *
     * @param date The new OffsetDateTime to be set.
     * @return A new DateTimeModel instance with the updated date.
     */
    fun copy(date: OffsetDateTime): DateTimeModel {
        return DateTimeModel(offsetDateTime = date.with(offsetDateTime.toLocalTime()))
    }
}
