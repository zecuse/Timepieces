package com.zecuse.timepieces.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zecuse.timepieces.R
import com.zecuse.timepieces.model.TabItem
import com.zecuse.timepieces.ui.theme.TimepiecesTheme

class MainActivity: ComponentActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		val tabs = listOf(
			TabItem(title = "alarm",
			        selectedIcon = R.drawable.alarm_filled,
			        unselectedIcon = R.drawable.alarm_outline),
			TabItem(title = "clock",
			        selectedIcon = R.drawable.clock_filled,
			        unselectedIcon = R.drawable.clock_outline),
			TabItem(title = "stopwatch",
					selectedIcon = R.drawable.stopwatch_filled,
					unselectedIcon = R.drawable.stopwatch_outline),
			TabItem(title = "timer",
					selectedIcon = R.drawable.hourglass_filled,
					unselectedIcon = R.drawable.hourglass_outline),
		)
		setContent {
			TimepiecesTheme {
				TabbedRow(tabItems = tabs)
			}
		}
	}
}