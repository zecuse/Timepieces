package com.zecuse.timepieces.ui.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.zecuse.timepieces.R
import com.zecuse.timepieces.database.FakeDao
import com.zecuse.timepieces.model.TabItem
import com.zecuse.timepieces.ui.theme.TimepiecesTheme
import com.zecuse.timepieces.viewmodel.SettingsEvent
import com.zecuse.timepieces.viewmodel.SettingsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TabbedRowTest
{
	@get:Rule
	val rule = createAndroidComposeRule<ComponentActivity>()

	private lateinit var settings: SettingsViewModel

	@Before
	fun setUp()
	{
		val tabs = listOf(
			TabItem(title = rule.activity.getString(R.string.alarm),
			        selectedIcon = R.drawable.alarm_filled,
			        unselectedIcon = R.drawable.alarm_outline),
			TabItem(title = rule.activity.getString(R.string.clock),
			        selectedIcon = R.drawable.clock_filled,
			        unselectedIcon = R.drawable.clock_outline),
			TabItem(title = rule.activity.getString(R.string.stopwatch),
			        selectedIcon = R.drawable.stopwatch_filled,
			        unselectedIcon = R.drawable.stopwatch_outline),
			TabItem(title = rule.activity.getString(R.string.timer),
			        selectedIcon = R.drawable.hourglass_filled,
			        unselectedIcon = R.drawable.hourglass_outline),
		)
		settings = SettingsViewModel(FakeDao())
		settings.onEvent(SettingsEvent.SetTabs(MyTabs.Both))
		rule.setContent {
			TimepiecesTheme(settings = settings.state.value) {
				Box(modifier = Modifier
					.fillMaxSize()
					.background(color = MaterialTheme.colorScheme.background)) {
					TabbedRow(settings = settings,
					          tabItems = tabs)
				}
			}
		}
	}

	@Test
	fun initial()
	{
		val tabs = listOf(rule.activity.getString(R.string.alarm),
		                  rule.activity.getString(R.string.clock),
		                  rule.activity.getString(R.string.stopwatch),
		                  rule.activity.getString(R.string.timer))
		val iconText = rule.activity.getString(R.string.icon)

		tabs.forEach {
			val titleIcon = it + iconText
			val title = rule.onNodeWithContentDescription(it)
			val icon = rule.onNodeWithContentDescription(titleIcon)

			title.assertExists("Failed to find $it")
			icon.assertExists("Failed to find $titleIcon")
		}
	}

	@Test
	fun noTitles()
	{
		val tabs = listOf(rule.activity.getString(R.string.alarm),
		                  rule.activity.getString(R.string.clock),
		                  rule.activity.getString(R.string.stopwatch),
		                  rule.activity.getString(R.string.timer))
		val iconText = rule.activity.getString(R.string.icon)

		settings.onEvent(SettingsEvent.SetTabs(MyTabs.Icon))

		tabs.forEach {
			val titleIcon = it + iconText
			val title = rule.onNodeWithContentDescription(it)
			val icon = rule.onNodeWithContentDescription(titleIcon)

			title.assertDoesNotExist()
			icon.assertExists("Failed to find $titleIcon")
		}
	}

	@Test
	fun noIcons()
	{
		val tabs = listOf(rule.activity.getString(R.string.alarm),
		                  rule.activity.getString(R.string.clock),
		                  rule.activity.getString(R.string.stopwatch),
		                  rule.activity.getString(R.string.timer))
		val iconText = rule.activity.getString(R.string.icon)

		settings.onEvent(SettingsEvent.SetTabs(MyTabs.Title))

		tabs.forEach {
			val titleIcon = it + iconText
			val title = rule.onNodeWithContentDescription(it)
			val icon = rule.onNodeWithContentDescription(titleIcon)

			title.assertExists("Failed to find $it")
			icon.assertDoesNotExist()
		}
	}

	@Test
	fun tapAlarm_AlarmSelected()
	{
		val isSelected = rule.activity.getString(R.string.is_selected)
		val unSelected = rule.activity.getString(R.string.is_not_selected)
		val testing = rule.activity.getString(R.string.alarm)
		val tabs = listOf(testing,
		                  rule.activity.getString(R.string.clock),
		                  rule.activity.getString(R.string.stopwatch),
		                  rule.activity.getString(R.string.timer))
		val iconText = rule.activity.getString(R.string.icon)
		val click = rule.onNodeWithContentDescription(testing + iconText)

		click.performClick()

		tabs.forEach {
			val titleIcon = it + iconText
			val state =
				rule.onNode(hasStateDescription(if (it == testing) titleIcon + isSelected else titleIcon + unSelected))

			state.assertExists("Failed to find $titleIcon in '${if (it == testing) isSelected else unSelected}' state")
		}
	}

	@Test
	fun tapClock_ClockSelected()
	{
		val isSelected = rule.activity.getString(R.string.is_selected)
		val unSelected = rule.activity.getString(R.string.is_not_selected)
		val testing = rule.activity.getString(R.string.clock)
		val tabs = listOf(rule.activity.getString(R.string.alarm),
		                  testing,
		                  rule.activity.getString(R.string.stopwatch),
		                  rule.activity.getString(R.string.timer))
		val iconText = rule.activity.getString(R.string.icon)
		val click = rule.onNodeWithContentDescription(testing + iconText)

		click.performClick()

		tabs.forEach {
			val titleIcon = it + iconText
			val state =
				rule.onNode(hasStateDescription(if (it == testing) titleIcon + isSelected else titleIcon + unSelected))

			state.assertExists("Failed to find $titleIcon in '${if (it == testing) isSelected else unSelected}' state")
		}
	}

	@Test
	fun tapStopwatch_StopwatchSelected()
	{
		val isSelected = rule.activity.getString(R.string.is_selected)
		val unSelected = rule.activity.getString(R.string.is_not_selected)
		val testing = rule.activity.getString(R.string.stopwatch)
		val tabs = listOf(rule.activity.getString(R.string.alarm),
		                  rule.activity.getString(R.string.clock),
		                  testing,
		                  rule.activity.getString(R.string.timer))
		val iconText = rule.activity.getString(R.string.icon)
		val click = rule.onNodeWithContentDescription(testing + iconText)

		click.performClick()

		tabs.forEach {
			val titleIcon = it + iconText
			val state =
				rule.onNode(hasStateDescription(if (it == testing) titleIcon + isSelected else titleIcon + unSelected))

			state.assertExists("Failed to find $titleIcon in '${if (it == testing) isSelected else unSelected}' state")
		}
	}

	@Test
	fun tapTimer_TimerSelected()
	{
		val isSelected = rule.activity.getString(R.string.is_selected)
		val unSelected = rule.activity.getString(R.string.is_not_selected)
		val testing = rule.activity.getString(R.string.timer)
		val tabs = listOf(rule.activity.getString(R.string.alarm),
		                  rule.activity.getString(R.string.clock),
		                  rule.activity.getString(R.string.stopwatch),
		                  testing)
		val iconText = rule.activity.getString(R.string.icon)
		val click = rule.onNodeWithContentDescription(testing + iconText)

		click.performClick()

		tabs.forEach {
			val titleIcon = it + iconText
			val state =
				rule.onNode(hasStateDescription(if (it == testing) titleIcon + isSelected else titleIcon + unSelected))

			state.assertExists("Failed to find $titleIcon in '${if (it == testing) isSelected else unSelected}' state")
		}
	}
}