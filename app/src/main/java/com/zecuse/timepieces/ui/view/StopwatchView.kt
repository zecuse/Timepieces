package com.zecuse.timepieces.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zecuse.timepieces.R
import com.zecuse.timepieces.database.FakeDao
import com.zecuse.timepieces.ui.theme.TimepiecesTheme
import com.zecuse.timepieces.viewmodel.StopwatchEvent
import com.zecuse.timepieces.viewmodel.StopwatchViewModel

@Composable
fun StopwatchView(stopwatch: StopwatchViewModel)
{
	Box(contentAlignment = Alignment.Center,
	    modifier = Modifier.fillMaxSize()) {
		Controls(stopwatch)
	}
}

@Composable
fun Controls(stopwatch: StopwatchViewModel, modifier: Modifier = Modifier)
{
	val toggleTicking = {stopwatch.onEvent(StopwatchEvent.ToggleTicking)}
	val lapOrReset = {stopwatch.onEvent(StopwatchEvent.ResetOrLap)}
	val state = stopwatch.state.value
	val duration = 200
	val buttonWidth = 100
	val widthAnim =
		animateDpAsState(targetValue = if (state.startTime != 0L) (3 * buttonWidth).dp else buttonWidth.dp,
		                 animationSpec = tween(durationMillis = duration),
		                 label = "Space width")
	Box(modifier = Modifier.width(widthAnim.value)) {
		Button(onClick = toggleTicking,
		       modifier = modifier
			       .align(Alignment.CenterStart)
			       .width(buttonWidth.dp)) {
			if (state.ticking) Text(text = stringResource(R.string.pause))
			else if (state.startTime != 0L) Text(text = stringResource(R.string.resume))
			else Text(text = stringResource(R.string.start))
		}
		AnimatedVisibility(visible = state.startTime != 0L,
		                   enter = fadeIn(animationSpec = tween(durationMillis = duration)),
		                   exit = fadeOut(animationSpec = tween(durationMillis = duration)),
		                   modifier = Modifier.align(Alignment.CenterEnd)) {
			Button(onClick = lapOrReset,
			       modifier = modifier.width(buttonWidth.dp)) {
				if (state.ticking) Text(text = stringResource(R.string.lap))
				else Text(text = stringResource(R.string.reset))
			}
		}
	}
}

@Preview
@Composable
private fun StopwatchPreview()
{
	val fakeDao = FakeDao()
	val fakeStopwatch = StopwatchViewModel(fakeDao)
	fakeStopwatch.onEvent(StopwatchEvent.ToggleTicking)
	TimepiecesTheme {
		StopwatchView(stopwatch = fakeStopwatch)
	}
}