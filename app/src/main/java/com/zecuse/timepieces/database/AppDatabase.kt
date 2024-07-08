package com.zecuse.timepieces.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zecuse.timepieces.model.LapsConverter
import com.zecuse.timepieces.model.SettingsData
import com.zecuse.timepieces.model.StopwatchData
import com.zecuse.timepieces.viewmodel.SettingsViewModel
import com.zecuse.timepieces.viewmodel.StopwatchViewModel

@Database(entities = [SettingsData::class, StopwatchData::class],
          version = 1)
@TypeConverters(LapsConverter::class)
abstract class AppDatabase: RoomDatabase()
{
	abstract val dao: AppDao
}

class SettingsFactory(
	private val db: AppDatabase,
): ViewModelProvider.Factory
{
	@Suppress("UNCHECKED_CAST")
	override fun <T: ViewModel> create(modelClass: Class<T>): T
	{
		return SettingsViewModel(db.dao) as T
	}
}

class StopwatchFactory(
	private val db: AppDatabase,
): ViewModelProvider.Factory
{
	@Suppress("UNCHECKED_CAST")
	override fun <T: ViewModel> create(modelClass: Class<T>): T
	{
		return StopwatchViewModel(db.dao) as T
	}
}
