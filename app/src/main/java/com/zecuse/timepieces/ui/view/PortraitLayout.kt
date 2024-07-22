package com.zecuse.timepieces.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zecuse.timepieces.R
import com.zecuse.timepieces.database.FakeDao
import com.zecuse.timepieces.model.TabItem
import com.zecuse.timepieces.ui.theme.TimepiecesTheme
import com.zecuse.timepieces.ui.theme.shapes.roundrect
import com.zecuse.timepieces.ui.view.tabs.MyTabs
import com.zecuse.timepieces.ui.view.tabs.TabbedRow
import com.zecuse.timepieces.ui.view.tabs.TitleIconTab
import com.zecuse.timepieces.viewmodel.SettingsEvent
import com.zecuse.timepieces.viewmodel.SettingsViewModel
import com.zecuse.timepieces.viewmodel.StopwatchViewModel

/**
 * Split the features of the app into separate tabs of content.
 * This is drawn for a portrait orientation.
 *
 * App features include:
 * An alarm defined in [AlarmView]
 * A clock defined in [ClockView]
 * A stopwatch defined in [StopwatchView]
 * A timer defined in [TimerView]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PortraitLayout(tabItems: List<TabItem>,
                   pagerState: PagerState,
                   settings: SettingsViewModel,
                   stopwatch: StopwatchViewModel,
                   modifier: Modifier = Modifier)
{
	val tabStyle = settings.state.value.tabsStyle
	val coScope = rememberCoroutineScope()

	Column(modifier = modifier.fillMaxSize()) {
		TabbedRow(selectedTabIndex = pagerState.targetPage,
		          modifier = Modifier.padding(4.dp),
		          indicatorShape = {
			          if (tabStyle != MyTabs.Icon)
			          {
				          MaterialTheme.roundrect(offset = Offset(x = 0f,
				                                                  y = if (tabStyle == MyTabs.Both) 86f else 0f),
				                                  size = Pair(it,
				                                              25.dp),
				                                  radius = 15.dp)
			          }
			          else
			          {
				          // Deliberate empty shape.
				          MaterialTheme.roundrect(size = 0.dp,
				                                  radius = 0.dp)
			          }
		          }) {
			tabItems.forEachIndexed {idx, item ->
				TitleIconTab(tabsStyle = tabStyle,
				             idx = idx,
				             item = item,
				             coScope = coScope,
				             pagerState = pagerState)
			}
		}
		HorizontalPager(state = pagerState,
		                modifier = Modifier.weight(1f)) {
			when (pagerState.targetPage)
			{
				0 -> AlarmView()
				1 -> ClockView()
				2 -> StopwatchView(stopwatch = stopwatch,
				                   leftHand = settings.state.value.leftHanded)
				3 -> TimerView()
			}
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PortraitPreview()
{
	val fakeDao = FakeDao()
	val fakeSettings = SettingsViewModel(fakeDao)
	fakeSettings.onEvent(SettingsEvent.SetTabs(MyTabs.Both))
	val fakeStopwatch = StopwatchViewModel(fakeDao)
	val tabs = listOf(
		TabItem(title = "first",
		        selectedIcon = R.drawable.stopwatch_filled,
		        unselectedIcon = R.drawable.stopwatch_outline),
		TabItem(title = "stopwatch",
		        selectedIcon = R.drawable.hourglass_filled,
		        unselectedIcon = R.drawable.hourglass_outline),
		TabItem(title = "third"),
		TabItem(title = "third"),
	)
	TimepiecesTheme {
		val pagerState = rememberPagerState {tabs.size}
		PortraitLayout(tabItems = tabs,
		               pagerState = pagerState,
		               settings = fakeSettings,
		               stopwatch = fakeStopwatch)
	}
}