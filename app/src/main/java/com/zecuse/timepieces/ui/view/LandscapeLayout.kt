package com.zecuse.timepieces.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
import com.zecuse.timepieces.ui.view.tabs.TabbedColumn
import com.zecuse.timepieces.ui.view.tabs.TitleIconTab
import com.zecuse.timepieces.ui.view.utils.HandButton
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
 *
 * @param tabItems A list of items to display in the [TabbedColumn].
 * @param pagerState The [PagerState] for the [VerticalPager].
 * This is shared with the [PortraitLayout], so they will be synced.
 * @param settings The current state of the [SettingsViewModel].
 * @param stopwatch The current state of the [StopwatchViewModel].
 * @param modifier Any modifiers to be applied to the [Row] composing the main view.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LandscapeLayout(tabItems: List<TabItem>,
                    pagerState: PagerState,
                    settings: SettingsViewModel,
                    stopwatch: StopwatchViewModel,
                    modifier: Modifier = Modifier)
{
	val leftHand = settings.state.value.leftHanded

	Row(verticalAlignment = Alignment.CenterVertically,
	    modifier = modifier.fillMaxSize()) {
		TabControls(visible = leftHand,
		            enter = slideInHorizontally(tween(durationMillis = stopwatch.duration)) {-it},
		            exit = slideOutHorizontally(tween(durationMillis = stopwatch.duration)) {-it},
		            left = true,
		            alignment = Alignment.CenterStart,
		            tabItems = tabItems,
		            pagerState = pagerState,
		            settings = settings,
		            modifier = Modifier.width(65.dp))
		VerticalPager(state = pagerState,
		              modifier = Modifier.weight(1f)) {
			when (pagerState.targetPage)
			{
				0 -> AlarmView()
				1 -> ClockView()
				2 -> StopwatchView(settings = settings,
				                   stopwatch = stopwatch,
				                   leftHand = leftHand,
				                   landscape = true)
				3 -> TimerView()
			}
		}
		TabControls(visible = !leftHand,
		            enter = slideInHorizontally(tween(durationMillis = stopwatch.duration)) {it},
		            exit = slideOutHorizontally(tween(durationMillis = stopwatch.duration)) {it},
		            left = false,
		            alignment = Alignment.CenterEnd,
		            tabItems = tabItems,
		            pagerState = pagerState,
		            settings = settings,
		            modifier = Modifier.width(65.dp))
	}
}

/**
 * Displays either the [HandButton] or the tabs for the [TabbedColumn].
 *
 * These are composed in a [Box] so they can be drawn over each other,
 * the [HandButton] below the [TabbedColumn].
 *
 * Both of those use the same transitions for their [AnimatedVisibility] composables,
 * but they have opposite visibility.
 *
 * @param visible The visibility for the [AnimatedVisibility].
 * @param enter The [EnterTransition] for the [AnimatedVisibility].
 * @param exit The [ExitTransition] for the [AnimatedVisibility].
 * @param left If the [HandButton] is a left or right hand.
 * @param alignment The alignment the [Box] will use.
 * @param tabItems Passed down to the [Tabs] composable.
 * @param pagerState Passed down to the [Tabs] composable.
 * @param settings Passed down to the [Tabs] composable.
 * @param modifier Any [Modifier]s to be applied to the [Box].
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabControls(visible: Boolean,
                enter: EnterTransition,
                exit: ExitTransition,
                left: Boolean,
                alignment: Alignment,
                tabItems: List<TabItem>,
                pagerState: PagerState,
                settings: SettingsViewModel,
                modifier: Modifier = Modifier)
{
	val switchHands = {settings.onEvent(SettingsEvent.ToggleHandedness)}
	Box(contentAlignment = alignment,
	    modifier = modifier) {
		AnimatedVisibility(visible = visible,
		                   enter = enter,
		                   exit = exit) {
			HandButton(left) {switchHands()}
		}
		AnimatedVisibility(visible = !visible,
		                   enter = enter,
		                   exit = exit) {
			Tabs(tabItems,
			     pagerState,
			     settings)
		}
	}
}

/**
 * Displays the tabs for the [TabbedColumn].
 *
 * @param tabItems A list of items to display in the [TabbedColumn].
 * @param pagerState The [PagerState] for the [VerticalPager].
 * This is shared with the [PortraitLayout], so they will be synced.
 * @param settings The current state of the [SettingsViewModel].
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(tabItems: List<TabItem>, pagerState: PagerState, settings: SettingsViewModel)
{
	val coScope = rememberCoroutineScope()
	val tabStyle = settings.state.value.tabsStyle
	TabbedColumn(selectedTabIndex = pagerState.targetPage,
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
}

@OptIn(ExperimentalFoundationApi::class)
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
		TabItem(title = "stopwatch",
		        selectedIcon = R.drawable.hourglass_filled,
		        unselectedIcon = R.drawable.hourglass_outline),
		TabItem(title = "third"),
		TabItem(title = "third"),
	)
	TimepiecesTheme {
		val pagerState = rememberPagerState {tabs.size}
		LandscapeLayout(tabItems = tabs,
		                pagerState = pagerState,
		                settings = fakeSettings,
		                stopwatch = fakeStopwatch)
	}
}
