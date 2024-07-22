package com.zecuse.timepieces.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zecuse.timepieces.database.AppDao
import com.zecuse.timepieces.model.SettingsData
import com.zecuse.timepieces.model.SettingsState
import com.zecuse.timepieces.ui.theme.AppFonts
import com.zecuse.timepieces.ui.theme.changeFont
import com.zecuse.timepieces.ui.theme.defaultType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(private val dao: AppDao): ViewModel()
{
	val state = mutableStateOf(SettingsState())

	init
	{
		viewModelScope.launch {
			val settings = dao.getSettings()
				               .first() ?: SettingsData()
			state.apply {
				val spacing = settings.spacing
				val typography = when (spacing)
				{
					"mono" -> changeFont(base = state.value.typography,
					                     fontFam = AppFonts.sourceCodePro)
					else   -> defaultType
				}
				this.value = this.value.copy(theme = settings.theme,
				                             color = settings.color,
				                             spacing = spacing,
				                             typography = typography,
				                             tabsStyle = settings.tabs)
			}
		}
	}

	fun onEvent(event: SettingsEvent)
	{
		when (event)
		{
			is SettingsEvent.SetColor      ->
			{
				viewModelScope.launch {
					var settings = dao.getSettings()
						               .first() ?: SettingsData()
					settings = settings.copy(color = event.color)
					dao.updateSetting(settings)
					state.apply {this.value = this.value.copy(color = event.color)}
				}
			}
			is SettingsEvent.SetSpacing    ->
			{
				viewModelScope.launch {
					var settings = dao.getSettings()
						               .first() ?: SettingsData()
					settings = settings.copy(spacing = event.spacing)
					dao.updateSetting(settings)
					state.apply {
						val spacing = event.spacing
						val font =
							if (spacing == "mono") AppFonts.sourceCodePro else FontFamily.Default
						val typography = changeFont(base = state.value.typography,
						                            fontFam = font)
						this.value = this.value.copy(spacing = spacing,
						                             typography = typography)
					}
				}
			}
			is SettingsEvent.SetTheme      ->
			{
				viewModelScope.launch {
					var settings = dao.getSettings()
						               .first() ?: SettingsData()
					settings = settings.copy(theme = event.theme)
					dao.updateSetting(settings)
					state.apply {this.value = this.value.copy(theme = event.theme)}
				}
			}
			is SettingsEvent.SetTabs       ->
			{
				viewModelScope.launch {
					var settings = dao.getSettings()
						               .first() ?: SettingsData()
					settings = settings.copy(tabs = event.tabs)
					dao.updateSetting(settings)
					state.apply {this.value = this.value.copy(tabsStyle = event.tabs)}
				}
			}
			SettingsEvent.ToggleHandedness ->
			{
				viewModelScope.launch {
					var settings = dao.getSettings()
						               .first() ?: SettingsData()
					settings = settings.copy(leftHanded = !settings.leftHanded)
					dao.updateSetting(settings)
					state.apply {
						this.value = this.value.copy(leftHanded = !settings.leftHanded)
					}
				}
			}
		}
	}
}
