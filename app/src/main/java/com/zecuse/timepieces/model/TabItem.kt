package com.zecuse.timepieces.model

import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
	val title: String,
	val selectedIcon: ImageVector? = null,
	val unselectedIcon: ImageVector? = null
)