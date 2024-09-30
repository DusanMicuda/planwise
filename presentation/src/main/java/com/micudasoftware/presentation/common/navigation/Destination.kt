package com.micudasoftware.presentation.common.navigation

import kotlinx.serialization.Serializable

/**
 * Represents a destinations in the app.
 */
sealed class Destination {

    @Serializable
    data object Agenda : Destination()

    @Serializable
    data class TaskDetail(val id: Long? = null) : Destination()

    @Serializable
    data object Categories : Destination()
}