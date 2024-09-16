package com.micudasoftware.presentation.categories

import com.micudasoftware.presentation.common.UIEvent
import com.micudasoftware.presentation.categories.componets.model.CategoryModel

sealed class CategoriesEvent: UIEvent {

    data class AddCategory(val category: CategoryModel): CategoriesEvent()

    data class RemoveCategory(val category: CategoryModel): CategoriesEvent()

    data object NavigateBack: CategoriesEvent()
}