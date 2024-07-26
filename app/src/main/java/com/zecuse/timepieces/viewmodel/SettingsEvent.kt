package com.zecuse.timepieces.viewmodel

import com.zecuse.timepieces.model.SettingsState
import com.zecuse.timepieces.ui.theme.AppColor
import com.zecuse.timepieces.ui.theme.AppTheme
import com.zecuse.timepieces.ui.theme.SchemeParam
import com.zecuse.timepieces.ui.view.tabs.MyTabs

/**
 * All of the events that can be performed by a user on the [SettingsViewModel].
 */
sealed interface SettingsEvent
{
	/**
	 * Sets the [SettingsState.theme] to the [theme] value.
	 */
	data class SetTheme(val theme: AppTheme): SettingsEvent

	/**
	 * Sets the [param] value of the current color scheme to the [color] value.
	 */
	data class SetColor(val param: SchemeParam, val color: AppColor): SettingsEvent

	/**
	 * Toggles between monospace and the device's system default spacing.
	 */
	data class SetSpacing(val spacing: String): SettingsEvent

	/**
	 * Sets the [SettingsState.tabsStyle] to the [tabs] value.
	 */
	data class SetTabs(val tabs: MyTabs): SettingsEvent

	/**
	 * Toggles the handedness in landscape mode.
	 */
	data object ToggleHandedness: SettingsEvent
}