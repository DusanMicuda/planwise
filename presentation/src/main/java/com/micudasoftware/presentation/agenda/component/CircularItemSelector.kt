package com.micudasoftware.presentation.agenda.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.micudasoftware.presentation.common.utils.calculateScale
import com.micudasoftware.presentation.common.theme.PlanWiseTheme
import kotlinx.coroutines.launch

/**
 * A composable that displays a circular item selector.
 *
 * @param items The list of items to display.
 * @param initialItem The item to be selected initially.
 * @param textStyle The style to apply to the text.
 * @param modifier The modifier to apply to this layout.
 * @param numberOfDisplayedItems The number of items to display at once.
 * @param onItemSelected The callback to be called when an item is selected.
 */
@Composable
fun <T> CircularItemSelector(
    items: List<T>,
    initialItem: T,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    numberOfDisplayedItems: Int = 3,
    onItemSelected: (item: T) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    var lastSelectedIndex by remember { mutableIntStateOf(0) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    LaunchedEffect(items) {
        val targetIndex = items.indexOf(initialItem)
        lastSelectedIndex = targetIndex
        scrollState.scrollToItem(targetIndex)
    }

    LazyColumn(
        modifier = modifier.then(Modifier.height(itemHeight * numberOfDisplayedItems)),
        state = scrollState,
        flingBehavior = rememberSnapFlingBehavior(
            lazyListState = scrollState
        )
    ) {
        item { Spacer(modifier = Modifier.height(itemHeight)) }
        itemsIndexed(items) { index, item ->
            var fontScale by remember { mutableFloatStateOf(0.7f) }
            var opacity by remember { mutableFloatStateOf(0.3f) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minHeight = itemHeight)
                    .clickable {
                        if (index != lastSelectedIndex) {
                            coroutineScope.launch {
                                scrollState.animateScrollToItem(index)
                            }
                        }
                    }
                    .onGloballyPositioned { coordinates ->
                        itemHeight = maxOf(
                            itemHeight,
                            with(density) { coordinates.size.height.toDp() }
                        )
                        val itemHalfHeight = coordinates.size.height / 2
                        val y = coordinates.positionInParent().y + itemHalfHeight
                        val parentHalfHeight = (itemHalfHeight * numberOfDisplayedItems)
                        fontScale = calculateScale(y, parentHalfHeight, 0.7f)
                        opacity = calculateScale(y, parentHalfHeight, 0.3f)
                        val isSelected = (y > parentHalfHeight - itemHalfHeight && y < parentHalfHeight + itemHalfHeight)
                        if (isSelected && lastSelectedIndex != index) {
                            onItemSelected(item)
                            lastSelectedIndex = index
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = item.toString(),
                    style = textStyle,
                    color = textStyle.color.copy(alpha = opacity),
                    fontSize = textStyle.fontSize * fontScale,
                    textAlign = TextAlign.Center,
                )
            }
        }
        item { Spacer(modifier = Modifier.height(itemHeight)) }
    }
}

/**
 * Preview for [CircularItemSelector].
 */
@Preview
@Composable
fun LazyCircularColumnPreview() {
    PlanWiseTheme {
        CircularItemSelector(
            items = listOf(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
            ),
            initialItem = "June",
            textStyle = MaterialTheme.typography.labelMedium,
        )
    }
}
