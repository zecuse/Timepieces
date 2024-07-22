package com.zecuse.timepieces.ui.view.tabs

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Creates a vertical column of equally spaced tabs.
 *
 * The [indicatorShape] is drawn over the the [containerShape] and below the [tabs] and
 * can be removed by providing a lambda that doesn't draw anything. This lambda accepts a [Dp]
 * value that equals the height the tab will have available to it.
 *
 * @param modifier The [Modifier] to be applied to the column itself.
 * @param selectedTabIndex The index of the currently selected tab. Defaults to the first.
 * @param containerColor The background color for the column. Defaults to the current Material color scheme's background.
 * @param containerShape The shape to clip the column to. Defaults to [RectangleShape].
 * @param indicatorColor The background color for the [selectedTabIndex]. Defaults to the current Material color scheme's secondary.
 * @param indicatorShape The shape to clip the indicator to. Defaults to [CircleShape].
 * @param animationSpec The [AnimationSpec] used by the indicator. Defaults to a 250ms [tween].
 * @param tabs The tabs to display within the column.
 */
@Composable
fun TabbedColumn(modifier: Modifier = Modifier,
                 selectedTabIndex: Int = 0,
                 containerColor: Color = MaterialTheme.colorScheme.background,
                 containerShape: Shape = RectangleShape,
                 indicatorColor: Color = MaterialTheme.colorScheme.secondary,
                 indicatorShape: (Dp) -> Shape = {CircleShape},
                 animationSpec: AnimationSpec<Dp> = tween(durationMillis = 250),
                 tabs: @Composable () -> Unit)
{
    Surface(color = containerColor,
            shape = containerShape,
            modifier = Modifier.widthIn(min = 72.dp)) {
        SubcomposeLayout(modifier = modifier
            .selectableGroup()) {constraints ->
            val colHeight = constraints.maxHeight
            val measurables: List<Placeable> = subcompose(SubComposeID.PRE_CALCULATE_ITEM,
                                                          tabs).map {it.measure(constraints)}
            val tabHeight = colHeight / measurables.size
            val maxWidth = measurables.maxOf {it.width}

            val placeables = subcompose(SubComposeID.ITEM,
                                        tabs).map {
                it.measure(constraints.copy(minWidth = maxWidth,
                                            maxWidth = maxWidth,
                                            minHeight = tabHeight,
                                            maxHeight = tabHeight))
            }

            val positions = List(placeables.size) {
                TabPosition(offset = (it * tabHeight).toDp(),
                            size = tabHeight.toDp())
            }

            layout(width = maxWidth,
                   height = colHeight) {
                subcompose(SubComposeID.INDICATOR) {
                    Box(modifier = Modifier
                        .tabIndicator(tabPosition = positions[selectedTabIndex],
                                      animationSpec = animationSpec,
                                      isRow = false)
                        .size(maxWidth.toDp(), tabHeight.toDp())
                        .background(color = indicatorColor,
                                    shape = indicatorShape(tabHeight.toDp())))
                }.forEach {
                    it.measure(Constraints.fixed(width = maxWidth,
                                                 height = colHeight))
                        .placeRelative(x = 0,
                                       y = 0)
                }

                placeables.forEachIndexed {idx, placeable ->
                    placeable.placeRelative(x = 0,
                                            y = tabHeight * idx)
                }
            }
        }
    }
}