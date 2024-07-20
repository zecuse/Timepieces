package com.zecuse.timepieces.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zecuse.timepieces.database.AppDao
import com.zecuse.timepieces.model.StopwatchData
import com.zecuse.timepieces.model.StopwatchState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StopwatchViewModel(private val dao: AppDao): ViewModel()
{
	val state = mutableStateOf(StopwatchState())
	val duration = 500

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
		val stopwatch = state.value
		when (event)
		{
			StopwatchEvent.ClearLaps     -> state.apply {this.value.laps.clear()}
			StopwatchEvent.LapOrReset    ->
			{
				if (stopwatch.ticking)
				{
					val lap =
						stopwatch.elapsedTime + System.currentTimeMillis() - stopwatch.startTime
					if (stopwatch.laps.count() == stopwatch.maxLaps) stopwatch.laps.poll()
					stopwatch.laps.offer(lap)
					state.apply {
						val self = this.value
						this.value = self.copy(lapCnt = self.lapCnt + 1)
					}
				}
				else
				{
					state.apply {
						this.value = this.value.copy(startTime = 0L,
						                             elapsedTime = 0L,
						                             lapCnt = 0)
					}
					viewModelScope.launch {
						delay(duration.toLong())
						onEvent(StopwatchEvent.ClearLaps)
					}
				}
			}
			StopwatchEvent.ToggleTicking ->
			{
				if (stopwatch.ticking)
				{
					val elapsed =
						System.currentTimeMillis() - stopwatch.startTime + stopwatch.elapsedTime
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
