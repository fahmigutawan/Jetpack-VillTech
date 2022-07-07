package com.ub.villtech.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: StaggeredGridScope.() -> Unit
) {
    val scope = StaggeredGridScope()
    scope.apply(content)

    Layout(
        content = { scope.content.forEach { it() } },
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    columnCount: Int,
    content: StaggeredGridScope.() -> Unit
) {
    val scrWidth = LocalConfiguration.current.screenWidthDp
    val scope = StaggeredGridScope()
    scope.apply(content)

    Layout(
        content = { scope.content.forEach { it() } },
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columnWidth = (scrWidth.dp.toPx() / columnCount).toInt()
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columnCount) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columnCount) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}

class StaggeredGridScope {
    private val _data = mutableListOf<@Composable () -> Unit>()
    val content get() = _data.toList()

    fun item(content: @Composable () -> Unit) {
        _data.add(content)
    }

    fun items(count: Int, itemContent: @Composable (index: Int) -> Unit) {
        repeat(count) {
            _data.add {
                itemContent(it)
            }
        }
    }

    fun <T> items(items: Array<T>, itemContent: @Composable (item: T) -> Unit) {
        items.forEach {
            _data.add {
                itemContent(it)
            }
        }
    }


    fun <T> items(items: List<T>, itemContent: @Composable (item: T) -> Unit) {
        items.forEach {
            _data.add {
                itemContent(it)
            }
        }
    }
}
