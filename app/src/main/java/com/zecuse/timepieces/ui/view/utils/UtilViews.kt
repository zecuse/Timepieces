package com.zecuse.timepieces.ui.view.utils

import androidx.compose.material3.Button
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BoldButton(onClick: () -> Unit,
               modifier: Modifier = Modifier,
               content: @Composable () -> Unit)
{
	Button(onClick = onClick,
	       modifier = modifier) {
		ProvideTextStyle(value = TextStyle(fontWeight = FontWeight.SemiBold),
		                 content = content)
	}
}
