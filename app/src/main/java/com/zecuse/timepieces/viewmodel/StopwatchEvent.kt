package com.zecuse.timepieces.viewmodel

sealed interface StopwatchEvent
{
	data object ToggleTicking: StopwatchEvent
	data object ResetOrLap: StopwatchEvent
	data object ClearLaps: StopwatchEvent
}