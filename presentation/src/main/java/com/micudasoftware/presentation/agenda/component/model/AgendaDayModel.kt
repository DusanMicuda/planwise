package com.micudasoftware.presentation.agenda.component.model

data class AgendaDayModel(
    val dayOfWeek: String,
    val dayOfMonth: Int,
    val selected: Boolean,
    val onClick: () -> Unit,
)