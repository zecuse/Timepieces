package com.zecuse.timepieces.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ClockView()
{
	Box(contentAlignment = Alignment.Center,
		modifier = Modifier.fillMaxSize()) {
		Text(text = "TODO clock",
		     style = MaterialTheme.typography.displayLarge)
	}
}