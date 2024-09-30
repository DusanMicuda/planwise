package com.micudasoftware.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.micudasoftware.presentation.R
import com.micudasoftware.presentation.categories.componets.CategoryRow
import com.micudasoftware.presentation.categories.componets.CreateCategoryDialog
import com.micudasoftware.presentation.common.ComposeViewModel
import com.micudasoftware.presentation.common.PreviewViewModel
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import com.micudasoftware.presentation.categories.componets.model.CategoryModel
import com.micudasoftware.presentation.common.navigation.EmptyNavigator
import com.micudasoftware.presentation.common.navigation.Navigator
import kotlinx.coroutines.launch

@Composable
fun CategoriesScreen(
    viewModel: ComposeViewModel<CategoriesState, CategoriesEvent>,
    navigator: Navigator,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    var showCreateCategoryDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.padding(8.dp),
                    onClick = { coroutineScope.launch { navigator.navigateUp() }}
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = stringResource(R.string.button_close)
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.title_categories),
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )
                IconButton(
                    modifier = Modifier.padding(8.dp),
                    onClick = { showCreateCategoryDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = stringResource(R.string.button_add)
                    )
                }
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                    )
            ) {
                LazyColumn {
                    items(viewState.categories) { category ->
                        CategoryRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            model = category,
                            onRemove = { viewModel.onEvent(CategoriesEvent.RemoveCategory(it)) }
                        )
                    }
                }
                if (viewState.categories.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(R.string.text_no_categories),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
    if (showCreateCategoryDialog) {
        CreateCategoryDialog(
            onConfirm = { viewModel.onEvent(CategoriesEvent.AddCategory(it)) },
            onDismiss = { showCreateCategoryDialog = false }
        )
    }
}

@Preview
@Composable
private fun CategoriesScreenPreview() {
    PlanWiseTheme {
        CategoriesScreen(
            viewModel = PreviewViewModel(
                CategoriesState(
                    categories = listOf(
                        CategoryModel(
                            id = 1,
                            name = "Category",
                            color = Color.Blue
                        ),
                        CategoryModel(
                            id = 2,
                            name = "Category",
                            color = Color.Green
                        ),
                        CategoryModel(
                            id = 3,
                            name = "Category",
                            color = Color.Yellow
                        ),
                    )
                )
            ),
            navigator = EmptyNavigator
        )
    }
}

@Preview
@Composable
private fun CategoriesScreenEmptyPreview() {
    PlanWiseTheme {
        CategoriesScreen(
            viewModel = PreviewViewModel(
                CategoriesState(
                    categories = emptyList()
                )
            ),
            navigator = EmptyNavigator
        )
    }
}