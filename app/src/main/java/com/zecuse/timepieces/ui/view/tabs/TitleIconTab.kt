package com.zecuse.timepieces.ui.view.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zecuse.timepieces.R
import com.zecuse.timepieces.model.TabItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Determine if the tabs should display as with their icon, title, or both.
 */
enum class MyTabs
{
	Both, Icon, Title
}

/**
 * Creates a [Tab]-like composable intended to be used in a [TabbedColumn] or [TabbedRow].
 *
 * The title and icon of this tab are displayed according to the [tabsStyle] value.
 * If the icon is to be displayed, both a [TabItem.selectedIcon] and [TabItem.unselectedIcon]
 * icon must be non-null and reference a drawable resource.
 *
 * Selecting a tab updates the [pagerState].
 *
 * @param tabsStyle A [MyTabs] value determining what is displayed by the tab.
 * @param idx Which tab within the Tabbed* this tab is.
 * @param item The backing [TabItem] for this tab.
 * @param coScope The [CoroutineScope] used to update the [pagerState].
 * @param pagerState The [PagerState] that's associated with the Tabbed*.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TitleIconTab(tabsStyle: MyTabs,
                 idx: Int,
                 item: TabItem,
                 coScope: CoroutineScope,
                 pagerState: PagerState)
{
	val selected = idx == pagerState.targetPage
	val title = @Composable {
		Text(text = item.title,
		     fontWeight = FontWeight.SemiBold,
		     color = if (selected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.primary,
		     modifier = Modifier.semantics {
			     contentDescription = item.title
		     })
	}
	val icon = @Composable {
		if (item.selectedIcon != null && item.unselectedIcon != null)
		{
			val isSelected = stringResource(R.string.is_selected)
			val unSelected = stringResource(R.string.is_not_selected)
			val titleIcon = "${item.title} ${stringResource(R.string.icon)}"
			Icon(painter = painterResource(if (selected) item.selectedIcon else item.unselectedIcon),
			     contentDescription = titleIcon,
			     tint = MaterialTheme.colorScheme.primary,
			     modifier = Modifier
				     .size(60.dp)
				     .semantics {
					     stateDescription =
						     if (selected) "$titleIcon $isSelected" else "$titleIcon $unSelected"
				     })
		}
		else if ((item.selectedIcon == null) xor (item.unselectedIcon == null))
		{
			item.selectedIcon
			?: throw IllegalArgumentException("A selectedIcon is required")
			throw IllegalArgumentException("An unselectedIcon is required")
		}
	}
	val onClick: () -> Unit = {coScope.launch {pagerState.animateScrollToPage(idx)}}
	when (tabsStyle)
	{
		MyTabs.Both  -> MyTab(onClick = onClick,
		                      tabStyle = tabsStyle,
		                      text = title,
		                      icon = icon)
		MyTabs.Icon  -> MyTab(onClick = onClick,
		                      tabStyle = tabsStyle,
		                      icon = icon)
		MyTabs.Title -> MyTab(onClick = onClick,
		                      tabStyle = tabsStyle,
		                      text = title)
	}
}

@Composable
private fun MyTab(onClick: () -> Unit,
                  tabStyle: MyTabs,
                  modifier: Modifier = Modifier,
                  text: @Composable (() -> Unit)? = null,
                  icon: @Composable (() -> Unit)? = null)
{
	Column(verticalArrangement = Arrangement.Center,
	       horizontalAlignment = Alignment.CenterHorizontally,
	       modifier = modifier.selectable(selected = true,
	                                      role = Role.Tab,
	                                      onClick = onClick)) {
		if (icon != null)
		{
			icon()
			if (tabStyle == MyTabs.Both) Spacer(modifier = Modifier.height(4.dp))
		}
		if (text != null) text()
	}
}