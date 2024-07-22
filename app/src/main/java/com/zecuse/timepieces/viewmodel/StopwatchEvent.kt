package com.zecuse.timepieces.viewmodel

import com.zecuse.timepieces.model.StopwatchState

/**
 * All of the events that the [StopwatchViewModel] will handle.
 */
sealed interface StopwatchEvent
{
	/**
	 * Toggles the state of the [StopwatchState.ticking].
	 */
	data object ToggleTicking: StopwatchEvent

	/**
	 * Either records the current time as a lap or resets everything to 0L.
	 */
	data object LapOrReset: StopwatchEvent

	/**
	 * Clears the [StopwatchState.laps].
	 * Invoked after the corresponding UI element has finished animating.
	 */
	data object ClearLaps: StopwatchEvent

	/**
	 * Sets the [StopwatchState.currentTime] to the [time].
	 * Used to preserve the displayed time across recompositions.
	 */
	data class SetTime(val time: Long): StopwatchEvent
}