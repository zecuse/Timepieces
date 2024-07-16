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
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class SubComposeID
{
	PRE_CALCULATE_ITEM, ITEM, INDICATOR
}

data class TabPosition(val offset: Dp, val size: Dp)

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