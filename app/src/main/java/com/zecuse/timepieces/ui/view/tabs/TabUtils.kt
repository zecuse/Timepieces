package com.zecuse.timepieces.ui.view.tabs

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Provides unique IDs for the [SubcomposeLayout] used in the [TabbedColumn] and [TabbedRow].
 */
enum class SubComposeID
{
	PRE_CALCULATE_ITEM, ITEM, INDICATOR
}

/**
 * Small utility class to store the measured values for the tabs in a [TabbedColumn] and [TabbedRow]
 *
 * @param offset The measured position within the Tabbed* composable.
 * @param size The measured amount of space within the Tabbed* composable.
 */
data class TabPosition(val offset: Dp, val size: Dp)

/**
 * A [Modifier] that animates a [TabbedColumn]'s or [TabbedRow]'s indicator to a [tabPosition].
 *
 * @param tabPosition The position of the now selected tab.
 * @param animationSpec The method by which to animate the transition.
 * @param isRow If this is being used in a [TabbedColumn] or [TabbedRow]. Defaults to true, [TabbedRow].
 */
fun Modifier.tabIndicator(tabPosition: TabPosition,
                          animationSpec: AnimationSpec<Dp>,
                          isRow: Boolean = true): Modifier =
	composed(inspectorInfo = debugInspectorInfo {
		name = "tabIndicatorOffset"
		value = tabPosition
	}) {
		val currentTabWidth by animateDpAsState(targetValue = tabPosition.size,
		                                        animationSpec = animationSpec,
		                                        label = "Tab width")
		val indicatorOffset by animateDpAsState(targetValue = tabPosition.offset,
		                                        animationSpec = animationSpec,
		                                        label = "Tab offset")
		wrapContentSize(if (isRow) Alignment.BottomStart else Alignment.TopEnd)
			.offset(x = if (isRow) indicatorOffset else 0.dp,
			        y = if (!isRow) indicatorOffset else 0.dp)
			.width(currentTabWidth)
	}