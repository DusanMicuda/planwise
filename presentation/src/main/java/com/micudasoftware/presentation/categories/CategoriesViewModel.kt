package com.micudasoftware.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micudasoftware.data.repository.TasksRepository
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.categories.componets.model.CategoryModel
import com.micudasoftware.presentation.common.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Categories screen.
 *
 * @param repository The repository to access tasks data.
 */
@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: TasksRepository,
) : ViewModel(), ComposeViewModel<CategoriesState, CategoriesEvent> {

    private val _viewState = MutableStateFlow(CategoriesState())
    override val viewState = _viewState.asStateFlow()

    init {
        loadCategories()
    }

    override fun onEvent(event: CategoriesEvent) = when (event) {
        is CategoriesEvent.AddCategory -> addCategory(event.category)
        is CategoriesEvent.RemoveCategory -> removeCategory(event.category)
        CategoriesEvent.ConfirmDialog -> confirmDialog()
    }

    /**
     * Loads the categories.
     */
    private fun loadCategories() {
        viewModelScope.launch {
            val categories = repository.getTaskCategories().map {
                CategoryModel.fromTaskCategory(it)
            }
            _viewState.update { it.copy(categories = categories) }
        }
    }

    /**
     * Adds a new category.
     *
     * @param category The category to add.
     */
    private fun addCategory(category: CategoryModel) {
        viewModelScope.launch {
            val taskCategory = category.toTaskCategory()
            repository.saveTaskCategory(taskCategory)
            loadCategories()
        }
    }

    /**
     * Removes an existing category.
     *
     * @param category The category to remove.
     */
    private fun removeCategory(category: CategoryModel) {
        viewModelScope.launch {
            val isCategoryUsed = repository.isCategoryUsed(category.id)
            if (isCategoryUsed) {
                _viewState.update { it.copy(alertDialogTextRes = R.string.text_category_is_used) }
                return@launch
            }

            repository.deleteTaskCategoryById(category.id)
            loadCategories()
        }
    }

    /**
     * Confirms the dialog.
     */
    private fun confirmDialog() {
        _viewState.update { it.copy(alertDialogTextRes = null) }
    }
}