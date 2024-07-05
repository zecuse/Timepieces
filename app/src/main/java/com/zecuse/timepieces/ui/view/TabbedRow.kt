package com.zecuse.timepieces.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zecuse.timepieces.R
import com.zecuse.timepieces.model.TabItem
import com.zecuse.timepieces.ui.theme.TimepiecesTheme
import com.zecuse.timepieces.viewmodel.FakeDao
import com.zecuse.timepieces.viewmodel.SettingsEvent
import com.zecuse.timepieces.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

/**
 * Determine if the tabs should display as an icon, their title, or both.
 */
enum class MyTabs
{
	Both, Icon, Title
}

/**
 * Split the features of the app into separate tabs of content.
 *
 * App features include:
 * An alarm defined in [AlarmView]
 * A clock defined in [ClockView]
 * A stopwatch defined in [StopwatchView]
 * A timer defined in [TimerView]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabbedRow(settings: SettingsViewModel,
              tabItems: List<TabItem>,
              modifier: Modifier = Modifier)
{
	val coScope = rememberCoroutineScope()
	val pagerState = rememberPagerState {tabItems.size}

	Column(modifier = modifier.fillMaxSize()) {
		TabRow(selectedTabIndex = pagerState.targetPage) {
			tabItems.forEachIndexed {idx, item ->
				val selected = idx == pagerState.targetPage
				val title = @Composable {
					Text(text = item.title,
					     modifier = Modifier.semantics {
						     contentDescription = item.title
					     })
				}
				val icon = @Composable {
					if (item.selectedIcon != null && item.unselectedIcon != null)
					{
						val isSelected = stringResource(R.string.is_selected)
						val unSelected = stringResource(R.string.is_not_selected)
						val titleIcon = item.title + stringResource(R.string.icon)
						Icon(painter = painterResource(if (selected) item.selectedIcon else item.unselectedIcon),
						     contentDescription = titleIcon,
						     modifier = Modifier
							     .size(60.dp)
							     .semantics {
								     stateDescription =
									     if (selected) titleIcon + isSelected else titleIcon + unSelected
							     })
					}
					else if ((item.selectedIcon == null) xor (item.unselectedIcon == null))
					{
						item.selectedIcon
						?: throw IllegalArgumentException("A selectedIcon is required")
						throw IllegalArgumentException("An unselectedIcon is required")
					}
				}
				when (settings.state.value.tabs)
				{
					MyTabs.Both  -> Tab(selected = true,
					                    text = title,
					                    icon = icon,
					                    onClick = {coScope.launch {pagerState.animateScrollToPage(idx)}})
					MyTabs.Icon  -> Tab(selected = true,
					                    icon = icon,
					                    onClick = {coScope.launch {pagerState.animateScrollToPage(idx)}})
					MyTabs.Title -> Tab(selected = true,
					                    text = title,
					                    onClick = {coScope.launch {pagerState.animateScrollToPage(idx)}})
				}
			}
		}
		HorizontalPager(state = pagerState,
		                modifier = Modifier.weight(1f)) {
			when (pagerState.targetPage)
			{
				0 -> AlarmView()
				1 -> ClockView()
				2 -> StopwatchView()
				3 -> TimerView()
			}
		}
	}
}

/**
 * @suppress
 */
@Preview
@Composable
private fun TabbedRowPreview()
{
	val fake = SettingsViewModel(FakeDao())
	fake.onEvent(SettingsEvent.SetTabs(MyTabs.Both))
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
		TabbedRow(settings = fake,
		          tabItems = tabs)
	}
}