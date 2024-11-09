package com.micudasoftware.presentation.common.utils

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * Extension function to format an OffsetDateTime to a string with the pattern "dd LLL, HH:mm".
 *
 * @return The formatted date and time.
 */
fun OffsetDateTime.toFormatedDateTime(): String {
    return this.format(DateTimeFormatter.ofPattern("dd LLL, HH:mm"))
}

/**
 * Extension function to format an OffsetDateTime to a string with the pattern "eeee, dd LLLL yyyy".
 *
 * @return The formatted full date.
 */
fun OffsetDateTime.toFormatedFullDate(): String {
    return this.format(DateTimeFormatter.ofPattern("eeee, dd LLLL yyyy"))
}

/**
 * Extension function to format an OffsetDateTime to a string with the pattern "dd LLLL yyyy".
 *
 * @return The formatted long date.
 */
fun OffsetDateTime.toFormatedLongDate(): String {
    return this.format(DateTimeFormatter.ofPattern("dd LLLL yyyy"))
}

/**
 * Extension function to format an OffsetDateTime to a string with the pattern "MMM dd, yyyy".
 *
 * @return The formatted short date.
 */
fun OffsetDateTime.toFormatedShortDate(): String {
    return this.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
}

/**
 * Extension function to format an OffsetDateTime to a string with the pattern "HH:mm".
 *
 * @return The formatted time.
 */
fun OffsetDateTime.toFormatedTime(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}