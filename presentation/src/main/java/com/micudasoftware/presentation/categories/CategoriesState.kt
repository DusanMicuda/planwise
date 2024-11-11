package com.micudasoftware.presentation.categories

import androidx.annotation.StringRes
import com.micudasoftware.presentation.common.UIState
import com.micudasoftware.presentation.categories.componets.model.CategoryModel

/**
 * State for the Categories screen.
 *
 * @param categories The list of categories.
 * @param alertDialogTextRes The text resource ID for the alert dialog.
 */
data class CategoriesState(
    val categories: List<CategoryModel> = emptyList(),
    @StringRes val alertDialogTextRes: Int? = null,
): UIState
