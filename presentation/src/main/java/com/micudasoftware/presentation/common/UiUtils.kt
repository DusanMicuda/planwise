package com.micudasoftware.presentation.common

import androidx.compose.ui.graphics.Color

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