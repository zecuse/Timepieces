package com.zecuse.timepieces.ui.view.tabs

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp

enum class SubComposeID
{
	PRE_CALCULATE_ITEM, ITEM, INDICATOR
}

data class TabPosition(val offset: Dp, val size: Dp)

fun Modifier.tabIndicator(
	tabPosition: TabPosition,
	animationSpec: AnimationSpec<Dp>,
): Modifier = composed(inspectorInfo = debugInspectorInfo {
	name = "tabIndicatorOffset"
	value = tabPosition
}) {
	val currentTabWidth by animateDpAsState(targetValue = tabPosition.size,
	                                        animationSpec = animationSpec,
	                                        label = "Tab width")
	val indicatorOffset by animateDpAsState(targetValue = tabPosition.offset,
	                                        animationSpec = animationSpec,
	                                        label = "Tab offset")
	fillMaxWidth()
		.wrapContentSize(Alignment.BottomStart)
		.offset(x = indicatorOffset)
		.width(currentTabWidth)
		.fillMaxHeight()
}