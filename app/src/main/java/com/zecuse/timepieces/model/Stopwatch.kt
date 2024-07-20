package com.zecuse.timepieces.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.ArrayDeque
import java.util.Queue

data class StopwatchState(val ticking: Boolean = false,
                          val startTime: Long = 0L,
                          val elapsedTime: Long = 0L,
                          val maxLaps: Int = 50,
                          val laps: Queue<Long> = ArrayDeque(),
                          val lapCnt: Int = 0)

@Entity
data class StopwatchData(@PrimaryKey(autoGenerate = true)
                         val id: Int = 0,
                         val elapsedTime: Long = 0L,
                         val laps: Queue<Long> = ArrayDeque())

class LapsConverter
{
	@TypeConverter
	fun fromLaps(laps: Queue<Long>) = Json.encodeToString(laps)

	@TypeConverter
	fun toLaps(json: String) = Json.decodeFromString<Queue<Long>>(json)
}