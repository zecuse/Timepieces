package com.zecuse.timepieces.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zecuse.timepieces.R
import com.zecuse.timepieces.database.FakeDao
import com.zecuse.timepieces.model.TabItem
import com.zecuse.timepieces.ui.theme.TimepiecesTheme
import com.zecuse.timepieces.ui.view.tabs.MyTabs
import com.zecuse.timepieces.ui.view.tabs.TitleIconTab
import com.zecuse.timepieces.viewmodel.SettingsEvent
import com.zecuse.timepieces.viewmodel.SettingsViewModel
import com.zecuse.timepieces.viewmodel.StopwatchViewModel

/**
 * Split the features of the app into separate tabs of content.
 * This is drawn for a landscape orientation.
 *
 * App features include:
 * An alarm defined in [AlarmView]
 * A clock defined in [ClockView]
 * A stopwatch defined in [StopwatchView]
 * A timer defined in [TimerView]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LandscapeLayout(tabItems: List<TabItem>,
                    settings: SettingsViewModel,
                    stopwatch: StopwatchViewModel,
                    modifier: Modifier = Modifier)
{
	val coScope = rememberCoroutineScope()
	val pagerState = rememberPagerState {tabItems.size}

	Row(modifier = modifier.fillMaxSize()) {
		TabRow(selectedTabIndex = pagerState.targetPage) {
			tabItems.forEachIndexed {idx, item ->
				TitleIconTab(settings.state.value.tabsStyle,
				             idx,
				             item,
				             coScope,
				             pagerState)
			}
		}
		VerticalPager(state = pagerState,
		              modifier = Modifier.weight(1f)) {
			when (pagerState.targetPage)
			{
				0 -> AlarmView()
				1 -> ClockView()
				2 -> StopwatchView(stopwatch)
				3 -> TimerView()
			}
		}
	}
}

@Preview(device = "spec:width=1230dp,height=540dp,orientation=landscape")
@Composable
private fun LandscapePreview()
{
	val fakeDao = FakeDao()
	val fakeSettings = SettingsViewModel(fakeDao)
	fakeSettings.onEvent(SettingsEvent.SetTabs(MyTabs.Both))
	val fakeStopwatch = StopwatchViewModel(fakeDao)
	val tabs = listOf(
		TabItem(title = "first",
		        selectedIcon = R.drawable.stopwatch_filled,
		        unselectedIcon = R.drawable.stopwatch_outline),
		TabItem(title = "second",
		        selectedIcon = R.drawable.hourglass_filled,
		        unselectedIcon = R.drawable.hourglass_outline),
		TabItem(title = "third"),
	)
	TimepiecesTheme {
		LandscapeLayout(tabItems = tabs,
		                settings = fakeSettings,
		                stopwatch = fakeStopwatch)
	}
}
