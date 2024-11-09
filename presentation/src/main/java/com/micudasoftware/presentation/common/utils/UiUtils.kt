package com.micudasoftware.presentation.common.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

/**
 * Function to determine if a color is dark or light.
 *
 * @param color The color to check.
 * @return True if the color is dark, false otherwise.
 */
fun isColorDark(color: Color): Boolean {
    val darkness = 1 - (0.299 * color.red + 0.587 * color.green + 0.114 * color.blue)
    return darkness >= 0.5 // Dark colors will have a luminance value of 0.5 or higher
}

/**
 * Function to calculate the scale of an item in a list based on its position.
 *
 * @param currentPosition The current position of the item.
 * @param boxCenter The center of the box.
 * @param minScale The minimum scale of the item.
 * @return The scale of the item.
 */
fun calculateScale(currentPosition: Float, boxCenter: Int, minScale: Float): Float {
    val distanceFromCenter = abs(currentPosition - boxCenter)
    return minScale + (1f - minScale) * (1f - distanceFromCenter / boxCenter)
}