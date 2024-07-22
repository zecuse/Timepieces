package com.zecuse.timepieces.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.zecuse.timepieces.database.AppDatabase
import com.zecuse.timepieces.viewmodel.StopwatchViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.ArrayDeque
import java.util.Queue

/**
 * The backing state of the [StopwatchViewModel].
 *
 * @param ticking Whether the stopwatch is ticking.
 * @param startTime The time at which the stopwatch last began ticking at.
 * @param elapsedTime The total accrued time the stopwatch has ticked for.
 * @param currentTime The time to display on the UI.
 * @param maxLaps The total amount of times to keep in memory.
 * @param laps All of the times recorded by the stopwatch's lap function.
 * @param lapCnt How many laps that have currently been recorded.
 */
data class StopwatchState(val ticking: Boolean = false,
                          val startTime: Long = 0L,
                          val elapsedTime: Long = 0L,
                          val currentTime: Long = 0L,
                          val maxLaps: Int = 50,
                          val laps: Queue<Long> = ArrayDeque(),
                          val lapCnt: Int = 0)

/**
 * The subset of [StopwatchState] values to be saved to the [AppDatabase].
 */
@Entity
data class StopwatchData(@PrimaryKey(autoGenerate = true)
                         val id: Int = 0,
                         val elapsedTime: Long = 0L,
                         val laps: Queue<Long> = ArrayDeque())

/**
 * Type converters for the Room-based [AppDatabase] and the current [Queue] of [StopwatchState.laps].
 */
class LapsConverter
{
	@TypeConverter
	fun fromLaps(laps: Queue<Long>) = Json.encodeToString(laps)

	@TypeConverter
	fun toLaps(json: String) = Json.decodeFromString<Queue<Long>>(json)
}