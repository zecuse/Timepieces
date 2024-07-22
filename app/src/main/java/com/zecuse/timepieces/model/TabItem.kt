package com.zecuse.timepieces.model

/**
 * Small utility class containing data for a tab.
 *
 * @param title The text to be displayed on the tab.
 * @param selectedIcon The icon to be displayed when the tab is selected.
 * @param unselectedIcon The icon to be displayed when the tab is not selected.
 */
data class TabItem(
	val title: String,
	val selectedIcon: Int? = null,
	val unselectedIcon: Int? = null
)