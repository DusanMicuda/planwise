package com.micudasoftware.presentation.categories

import com.micudasoftware.presentation.common.UIEvent
import com.micudasoftware.presentation.categories.componets.model.CategoryModel

/**
 * Sealed class representing the events that can occur on the Categories screen.
 */
sealed class CategoriesEvent: UIEvent {

    /**
     * Event to add a new category.
     *
     * @param category The category to be added.
     */
    data class AddCategory(val category: CategoryModel): CategoriesEvent()

    /**
     * Event to remove an existing category.
     *
     * @param category The category to be removed.
     */
    data class RemoveCategory(val category: CategoryModel): CategoriesEvent()

    /**
     * Event to confirm a dialog action.
     */
    data object ConfirmDialog: CategoriesEvent()
}