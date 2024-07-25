package com.micudasoftware.planwise.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micudasoftware.planwise.presentation.component.model.AgendaDayModel
import com.micudasoftware.planwise.ui.theme.PlanWiseTheme

/**
 * A composable that displays a day in the agenda.
 *
 * @param model The model that contains the data to display
 * @param modifier The modifier to apply to this layout
 */
@Composable
fun AgendaDay(
    model: AgendaDayModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = if (model.selected) MaterialTheme.colorScheme.secondary else Color.Unspecified,
                shape = MaterialTheme.shapes.large
            )
            .clickable(onClick = model.onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = model.dayOfWeek,
            style = MaterialTheme.typography.labelSmall,
            color = if (model.selected) Color.Unspecified else Color(0xFFA9B4BE)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = model.dayOfMonth.toString(), style = MaterialTheme.typography.labelMedium)
    }
}

/**
 * Preview for [AgendaDay].
 */
@Preview
@Composable
private fun AgendaDayPreview() {
    PlanWiseTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Row {
                AgendaDay(
                    model = AgendaDayModel(
                        dayOfWeek = "Mo",
                        dayOfMonth = 1,
                        selected = true,
                        onClick = {}
                    )
                )
                AgendaDay(
                    model = AgendaDayModel(
                        dayOfWeek = "Tu",
                        dayOfMonth = 2,
                        selected = false,
                        onClick = {}
                    )
                )
            }
        }
    }
}