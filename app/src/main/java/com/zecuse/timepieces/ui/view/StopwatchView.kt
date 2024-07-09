package com.zecuse.timepieces.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zecuse.timepieces.viewmodel.StopwatchViewModel

@Composable
fun StopwatchView(stopwatch: StopwatchViewModel)
{
	Box(contentAlignment = Alignment.Center,
	    modifier = Modifier.fillMaxSize()) {
		Text(text = "TODO stopwatch",
		     style = MaterialTheme.typography.displayLarge)
	}
}