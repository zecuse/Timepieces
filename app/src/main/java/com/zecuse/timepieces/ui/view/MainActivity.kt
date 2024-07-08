package com.zecuse.timepieces.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.room.Room
import com.zecuse.timepieces.R
import com.zecuse.timepieces.database.AppDatabase
import com.zecuse.timepieces.database.SettingsFactory
import com.zecuse.timepieces.model.TabItem
import com.zecuse.timepieces.ui.theme.TimepiecesTheme
import com.zecuse.timepieces.viewmodel.SettingsViewModel

class MainActivity: ComponentActivity()
{
	private val db by lazy {
		Room.databaseBuilder(context = applicationContext,
		                     klass = AppDatabase::class.java,
		                     name = "settings.db")
			.build()
	}

	private val settingsModel by viewModels<SettingsViewModel>(factoryProducer = {SettingsFactory(db)})

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		val tabs = listOf(
			TabItem(title = getString(R.string.alarm),
			        selectedIcon = R.drawable.alarm_filled,
			        unselectedIcon = R.drawable.alarm_outline),
			TabItem(title = getString(R.string.clock),
			        selectedIcon = R.drawable.clock_filled,
			        unselectedIcon = R.drawable.clock_outline),
			TabItem(title = getString(R.string.stopwatch),
			        selectedIcon = R.drawable.stopwatch_filled,
			        unselectedIcon = R.drawable.stopwatch_outline),
			TabItem(title = getString(R.string.timer),
			        selectedIcon = R.drawable.hourglass_filled,
			        unselectedIcon = R.drawable.hourglass_outline),
		)
		setContent {
			TimepiecesTheme(settingsModel.state.value) {
				TabbedRow(settings = settingsModel, tabItems = tabs)
			}
		}
	}
}