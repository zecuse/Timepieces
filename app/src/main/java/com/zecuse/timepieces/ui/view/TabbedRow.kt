package com.zecuse.timepieces.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import com.zecuse.timepieces.R
import com.zecuse.timepieces.model.TabItem
import com.zecuse.timepieces.ui.theme.TimepiecesTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabbedRow(tabItems: List<TabItem>, modifier: Modifier = Modifier)
{
	val coScope = rememberCoroutineScope()
	val pagerState = rememberPagerState {tabItems.size}

	Column(modifier = modifier.fillMaxSize()) {
		TabRow(selectedTabIndex = pagerState.targetPage) {
			tabItems.forEachIndexed {idx, item ->
				val selected = idx == pagerState.targetPage
				Tab(selected = true,
				    text = {
					    Text(text = item.title)
				    },
				    icon = {
					    if (item.selectedIcon != null && item.unselectedIcon != null)
					    {
						    val isSelected = stringResource(R.string.is_selected)
						    val unSelected = stringResource(R.string.is_not_selected)
						    Icon(imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
						         contentDescription = item.title,
						         modifier = Modifier.semantics {
							         stateDescription =
								         if (selected) item.title + isSelected else item.title + unSelected
						         })
					    }
					    else if ((item.selectedIcon == null) xor (item.unselectedIcon == null))
					    {
						    item.selectedIcon
						    ?: throw IllegalArgumentException("A selectedIcon is required")
						    throw IllegalArgumentException("An unselectedIcon is required")
					    }
				    },
				    onClick = {
					    coScope.launch {pagerState.animateScrollToPage(idx)}
				    })
			}
		}
		HorizontalPager(state = pagerState,
		                modifier = Modifier.weight(1f)) {
			Box(contentAlignment = Alignment.Center,
			    modifier = Modifier.fillMaxSize()) {
				Text(text = tabItems[pagerState.targetPage].title,
				     style = MaterialTheme.typography.displayLarge)
			}
		}
	}
}

@Preview
@Composable
private fun TabbedRowPreview()
{
	val tabs = listOf(
		TabItem(title = "first",
		        selectedIcon = Icons.Filled.AccountCircle,
		        unselectedIcon = Icons.Outlined.AccountCircle),
		TabItem(title = "second",
		        selectedIcon = Icons.Filled.AccountCircle,
		        unselectedIcon = Icons.Outlined.AccountCircle),
		TabItem(title = "third"),
	)
	TimepiecesTheme {
		TabbedRow(tabItems = tabs)
	}
}