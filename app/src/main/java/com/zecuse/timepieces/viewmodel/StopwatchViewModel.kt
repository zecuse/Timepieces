package com.zecuse.timepieces.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zecuse.timepieces.database.AppDao
import com.zecuse.timepieces.model.StopwatchData
import com.zecuse.timepieces.model.StopwatchState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StopwatchViewModel(private val dao: AppDao): ViewModel()
{
	val state = mutableStateOf(StopwatchState())

	init
	{
		viewModelScope.launch {
			val settings = dao.getStopwatch()
				               .first() ?: StopwatchData()
			state.apply {
				this.value = this.value.copy(elapsedTime = settings.elapsedTime,
				                             laps = settings.laps)
			}
		}
	}

	fun onEvent(event: StopwatchEvent)
	{
		when (event)
		{
			StopwatchEvent.ClearLaps     -> TODO()
			StopwatchEvent.ResetOrLap    -> TODO()
			StopwatchEvent.ToggleTicking -> TODO()
		}
	}
}
