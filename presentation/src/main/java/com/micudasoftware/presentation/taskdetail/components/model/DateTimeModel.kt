package com.micudasoftware.presentation.taskdetail.components.model

import com.micudasoftware.presentation.common.utils.toFormatedLongDate
import com.micudasoftware.presentation.common.utils.toFormatedShortDate
import com.micudasoftware.presentation.common.utils.toFormatedTime
import java.time.OffsetDateTime
import java.time.OffsetTime

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
    val formattedDateLong: String = offsetDateTime.toFormatedLongDate().uppercase()
    val formattedDateShort: String = offsetDateTime.toFormatedShortDate()
    val formattedTime: String = offsetDateTime.toFormatedTime()

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
