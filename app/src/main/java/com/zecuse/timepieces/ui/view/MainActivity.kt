package com.zecuse.timepieces.ui.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalConfiguration
import androidx.room.Room
import com.zecuse.timepieces.R
import com.zecuse.timepieces.database.AppDatabase
import com.zecuse.timepieces.database.SettingsFactory
import com.zecuse.timepieces.database.StopwatchFactory
import com.zecuse.timepieces.model.TabItem
import com.zecuse.timepieces.ui.theme.TimepiecesTheme
import com.zecuse.timepieces.viewmodel.SettingsViewModel
import com.zecuse.timepieces.viewmodel.StopwatchViewModel

class MainActivity: ComponentActivity()
{
	private val db by lazy {
		Room.databaseBuilder(context = applicationContext,
		                     klass = AppDatabase::class.java,
		                     name = "settings.db")
			.build()
	}

	private val settingsModel by viewModels<SettingsViewModel>(factoryProducer = {SettingsFactory(db)})
	private val stopwatchModel by viewModels<StopwatchViewModel>(factoryProducer = {StopwatchFactory(db)})

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
			val configuration = LocalConfiguration.current
			TimepiecesTheme(settingsModel.state.value) {
				when (configuration.orientation)
				{
					Configuration.ORIENTATION_LANDSCAPE -> LandscapeLayout(tabItems = tabs,
					                                                       settings = settingsModel,
					                                                       stopwatch = stopwatchModel)
					else                                -> PortraitLayout(tabItems = tabs,
					                                                      settings = settingsModel,
					                                                      stopwatch = stopwatchModel)
				}
			}
		}
	}
}