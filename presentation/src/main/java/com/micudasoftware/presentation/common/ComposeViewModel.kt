package com.micudasoftware.presentation.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel interface for Compose UI.
 */
interface ComposeViewModel<T: UIState, E: UIEvent> {

    /**
     * State of the ViewModel.
     */
    val viewState: StateFlow<T>

    /**
     * Function to handle events.
     */
    fun onEvent(event: E)
}

/**
 * Preview ViewModel for Compose UI.
 */
class PreviewViewModel<T: UIState, E: UIEvent>(state: T): ComposeViewModel<T, E> {

    override val viewState: StateFlow<T> = MutableStateFlow(state)

    override fun onEvent(event: E) {}
}