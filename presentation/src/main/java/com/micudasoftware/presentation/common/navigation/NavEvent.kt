package com.micudasoftware.presentation.common.navigation

import androidx.navigation.NavOptionsBuilder

/**
 * A sealed class representing navigation events.
 */
sealed class NavEvent {

    /**
     * A navigation event to a specific destination.
     *
     * @property destination The target destination for the navigation event.
     * @property navOptions A lambda to configure navigation options.
     */
    data class To(
        val destination: Destination,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : NavEvent()

    /**
     * A navigation event to go back to the previous screen.
     */
    data object Back : NavEvent()
}