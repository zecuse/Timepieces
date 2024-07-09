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
			StopwatchEvent.ClearLaps     -> state.apply {this.value.laps.clear()}
			StopwatchEvent.ResetOrLap    ->
			{
				if (state.value.ticking)
				{
					val lap =
						state.value.elapsedTime + System.currentTimeMillis() - state.value.startTime
					state.value.laps.offer(lap)
				}
				else
				{
					state.apply {
						val self = this.value
						this.value = self.copy(startTime = 0L,
						                       elapsedTime = 0L)
						self.laps.clear()
					}
				}
			}
			StopwatchEvent.ToggleTicking ->
			{
				if (state.value.ticking)
				{
					val elapsed = System.currentTimeMillis() - state.value.startTime
					state.apply {
						this.value = this.value.copy(ticking = false,
						                             elapsedTime = elapsed)
					}
				}
				else
				{
					state.apply {
						this.value = this.value.copy(ticking = true,
						                             startTime = System.currentTimeMillis())
					}
				}
			}
		}
	}
}
