package com.micudasoftware.presentation.categories

import com.micudasoftware.presentation.common.UIState
import com.micudasoftware.presentation.categories.componets.model.CategoryModel

class CategoriesState(
    val categories: List<CategoryModel> = emptyList(),
): UIState
