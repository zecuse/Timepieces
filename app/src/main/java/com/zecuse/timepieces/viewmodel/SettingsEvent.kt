package com.zecuse.timepieces.viewmodel

import com.zecuse.timepieces.model.SettingsState
import com.zecuse.timepieces.ui.theme.AppColor
import com.zecuse.timepieces.ui.theme.AppTheme

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
	 * Sets the [SettingsState.color] to the [color] value.
	 */
	data class SetColor(val color: AppColor): SettingsEvent

	/**
	 * Toggles between monospace and the device's system default spacing.
	 */
	data class SetSpacing(val spacing: String): SettingsEvent
}