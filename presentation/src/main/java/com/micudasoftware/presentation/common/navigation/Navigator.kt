package com.micudasoftware.presentation.common.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Interface defining navigation operations.
 */
interface Navigator {

    /**
     * A Flow of navigation events.
     *
     * Warning: This should be collected only once to avoid multiple navigation events.
     */
    val navEvent: Flow<NavEvent>

    /**
     * Navigate to a specified destination.
     *
     * @param destination The target destination for navigation.
     */
    suspend fun navigateTo(destination: Destination)

    /**
     * Navigate up in the navigation stack.
     */
    suspend fun navigateUp()

}

/**
 * Default implementation of the Navigator.
 */
internal class DefaultNavigator: Navigator {

    private val _navEvent: Channel<NavEvent> = Channel(Channel.CONFLATED)
    override val navEvent: Flow<NavEvent> = _navEvent.receiveAsFlow()

    override suspend fun navigateTo(destination: Destination) {
        _navEvent.send(NavEvent.To(destination))
    }

    override suspend fun navigateUp() {
        _navEvent.send(NavEvent.Back)
    }

}

/**
 * Empty implementation of the Navigator interface for use in compose previews or tests.
 */
object EmptyNavigator: Navigator {

    override val navEvent: Flow<NavEvent> = emptyFlow()

    override suspend fun navigateTo(destination: Destination) {}

    override suspend fun navigateUp() {}
}