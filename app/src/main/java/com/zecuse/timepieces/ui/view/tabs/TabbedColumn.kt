package com.zecuse.timepieces.ui.view.tabs

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zecuse.timepieces.ui.theme.shapes.roundrect

@Composable
fun TabbedColumn(modifier: Modifier = Modifier,
                 selectedTabIndex: Int = 0,
                 fixedSize: Boolean = true,
                 containerColor: Color = MaterialTheme.colorScheme.background,
                 containerShape: Shape = MaterialTheme.roundrect(50.dp,
                                                                 5.dp),
                 indicatorColor: Color = MaterialTheme.colorScheme.primary,
                 indicatorShape: Shape = MaterialTheme.roundrect(50.dp,
                                                                 5.dp),
                 animationSpec: AnimationSpec<Dp> = tween(durationMillis = 250),
                 tab: @Composable () -> Unit)
{

}