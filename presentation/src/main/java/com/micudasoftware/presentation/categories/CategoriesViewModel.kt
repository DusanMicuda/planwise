package com.micudasoftware.presentation.categories

import com.micudasoftware.presentation.common.ComposeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoriesViewModel: ComposeViewModel<CategoriesState, CategoriesEvent> {

    private val _viewState = MutableStateFlow(CategoriesState())
    override val viewState = _viewState.asStateFlow()

    override fun onEvent(event: CategoriesEvent) {

    }
}