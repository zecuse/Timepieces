package com.zecuse.timepieces.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zecuse.timepieces.model.SettingsData

@Database(entities = [SettingsData::class],
          version = 1)
abstract class SettingsDatabase: RoomDatabase()
{
	abstract val dao: SettingsDao
}