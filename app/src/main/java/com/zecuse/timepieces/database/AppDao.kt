package com.zecuse.timepieces.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.zecuse.timepieces.model.SettingsData
import com.zecuse.timepieces.model.StopwatchData
import com.zecuse.timepieces.ui.theme.AppColor
import com.zecuse.timepieces.ui.theme.AppTheme
import com.zecuse.timepieces.ui.view.MyTabs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Dao
interface AppDao
{
	@Upsert
	suspend fun updateSetting(settings: SettingsData)

	@Query("SELECT * FROM SettingsData")
	fun getSettings(): Flow<SettingsData?>

	@Upsert
	suspend fun updateStopwatch(stopwatch: StopwatchData)

	@Query("SELECT * FROM StopwatchData")
	fun getStopwatch(): Flow<StopwatchData?>
}

/**
 * This exists for preview and testing purposes.
 *
 * Previews only need this to satisfy their ViewModel parameter. It does nothing.
 *
 * Tests use this as a makeshift database.
 */
class FakeDao: AppDao
{
	private var fakeSettings = HashMap<String, Any>()
	private var fakeStopwatch = HashMap<String, Any>()

	init
	{
		fakeSettings["theme"] = AppTheme.Light
		fakeSettings["color"] = AppColor.Magenta
		fakeSettings["leftHanded"] = false
		fakeSettings["spacing"] = "default"
		fakeSettings["tabs"] = MyTabs.Both

		fakeStopwatch["elapsedTime"] = 0L
		fakeStopwatch["laps"] = """{"laps":[0L]}"""
	}

	override suspend fun updateSetting(settings: SettingsData)
	{
		fakeSettings["theme"] = settings.theme
		fakeSettings["color"] = settings.color
		fakeSettings["spacing"] = settings.spacing
		fakeSettings["tabs"] = settings.tabs
	}

	override fun getSettings(): Flow<SettingsData?>
	{
		return MutableStateFlow(SettingsData(theme = fakeSettings["theme"] as AppTheme,
		                                     color = fakeSettings["color"] as AppColor,
		                                     spacing = fakeSettings["spacing"] as String,
		                                     tabs = fakeSettings["tabs"] as MyTabs))
	}

	override suspend fun updateStopwatch(stopwatch: StopwatchData)
	{
		fakeStopwatch["elapsedTime"] = stopwatch.elapsedTime
		fakeStopwatch["laps"] = Json.encodeToString(stopwatch.laps)
	}

	override fun getStopwatch(): Flow<StopwatchData?>
	{
		return MutableStateFlow(StopwatchData(elapsedTime = fakeStopwatch["elapsedTime"] as Long,
		                                      laps = Json.decodeFromString(fakeStopwatch["laps"] as String)))
	}
}
