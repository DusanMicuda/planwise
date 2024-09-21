package com.micudasoftware.presentation.categories

import androidx.lifecycle.ViewModel
import com.micudasoftware.presentation.common.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(): ViewModel(), ComposeViewModel<CategoriesState, CategoriesEvent> {

    private val _viewState = MutableStateFlow(CategoriesState())
    override val viewState = _viewState.asStateFlow()

    override fun onEvent(event: CategoriesEvent) {

    }
}