package com.zecuse.timepieces.ui.view.tabs

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp

@Composable
fun TabbedRow(modifier: Modifier = Modifier,
              selectedTabIndex: Int = 0,
              containerColor: Color = MaterialTheme.colorScheme.background,
              containerShape: Shape = CircleShape,
              indicatorColor: Color = MaterialTheme.colorScheme.primary,
              indicatorShape: Shape = CircleShape,
              animationSpec: AnimationSpec<Dp> = tween(durationMillis = 250),
              tab: @Composable () -> Unit)
{
	Surface(color = containerColor,
	        shape = containerShape) {
		SubcomposeLayout(modifier = modifier
			.selectableGroup()) {constraints ->
			val rowWidth = constraints.maxWidth
			val measurables: List<Placeable> = subcompose(SubComposeID.PRE_CALCULATE_ITEM,
			                                              tab).map {it.measure(constraints)}
			val tabWidth = rowWidth / measurables.size
			val maxHeight = measurables.maxOf {it.height}

			val placeables = subcompose(SubComposeID.ITEM,
			                            tab).map {
				it.measure(constraints.copy(minWidth = tabWidth,
				                            maxWidth = tabWidth,
				                            minHeight = maxHeight,
				                            maxHeight = maxHeight))
			}

			val positions = List(placeables.size) {
				val x = it * tabWidth
				TabPosition(offset = x.toDp(),
				            size = tabWidth.toDp())
			}

			layout(width = rowWidth,
			       height = maxHeight) {
				subcompose(SubComposeID.INDICATOR) {
					Box(modifier = Modifier
						.tabIndicator(tabPosition = positions[selectedTabIndex],
						              animationSpec = animationSpec)
						.height(maxHeight.toDp())
						.background(color = indicatorColor,
						            shape = indicatorShape))
				}.forEach {
					it.measure(Constraints.fixed(width = rowWidth,
					                             height = maxHeight))
						.placeRelative(x = 0,
						               y = 0)
				}

				placeables.forEachIndexed {idx, placeable ->
					placeable.placeRelative(x = tabWidth * idx,
					                        y = 0)
				}
			}
		}
	}
}