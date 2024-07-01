package com.zecuse.timepieces.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.AccountCircle
import com.zecuse.timepieces.model.TabItem
import com.zecuse.timepieces.ui.theme.TimepiecesTheme

class MainActivity: ComponentActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		val tabs = listOf(
			TabItem(title = "first",
			        selectedIcon = Icons.Filled.AccountCircle,
			        unselectedIcon = Icons.Outlined.AccountCircle),
			TabItem(title = "second",
			        selectedIcon = Icons.Filled.AccountCircle,
			        unselectedIcon = Icons.Outlined.AccountCircle),
			TabItem(title = "third"),
		)
		setContent {
			TimepiecesTheme {
				TabbedRow(tabItems = tabs)
			}
		}
	}
}