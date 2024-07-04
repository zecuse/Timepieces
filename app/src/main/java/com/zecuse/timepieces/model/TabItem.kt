package com.zecuse.timepieces.model

data class TabItem(
	val title: String,
	val selectedIcon: Int? = null,
	val unselectedIcon: Int? = null
)