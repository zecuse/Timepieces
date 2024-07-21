package com.zecuse.timepieces.viewmodel

sealed interface StopwatchEvent
{
	data object ToggleTicking: StopwatchEvent
	data object LapOrReset: StopwatchEvent
	data object ClearLaps: StopwatchEvent
	data class SetTime(val time: Long): StopwatchEvent
}