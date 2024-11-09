package com.micudasoftware.presentation.common.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Extension function for Flow to collect events in a Composable.
 *
 * This function collects events from the Flow and triggers the provided callback
 * whenever a new event is emitted. The collection is tied to the lifecycle of the
 * Composable, starting when the lifecycle is in the STARTED state.
 *
 * @param T The type of the events emitted by the Flow.
 * @param onEvent A callback function to handle the events emitted by the Flow.
 */
@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsEvent(onEvent: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                collect(onEvent)
            }
        }
    }
}