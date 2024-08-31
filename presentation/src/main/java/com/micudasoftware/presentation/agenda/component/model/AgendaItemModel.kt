package com.micudasoftware.presentation.agenda.component.model

import androidx.compose.ui.graphics.Color

data class AgendaItemModel(
    val title: String,
    val description: String,
    val time: String,
    val isDone: Boolean,
    val color: Color,
    val onChangeState: (Boolean) -> Unit,
    val onOpen: () -> Unit,
    val onEdit: () -> Unit,
    val onRemove: () -> Unit,
)