package com.zecuse.timepieces.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zecuse.timepieces.R
import com.zecuse.timepieces.database.FakeDao
import com.zecuse.timepieces.ui.theme.TimepiecesTheme
import com.zecuse.timepieces.viewmodel.StopwatchEvent
import com.zecuse.timepieces.viewmodel.StopwatchViewModel
import kotlinx.coroutines.delay

@Composable
fun StopwatchView(stopwatch: StopwatchViewModel)
{
	Box(contentAlignment = Alignment.Center,
	    modifier = Modifier.fillMaxSize()) {
		DisplayTime(stopwatch = stopwatch,
		            modifier = Modifier
			            .align(Alignment.TopCenter)
			            .padding(top = 50.dp))
		Controls(stopwatch = stopwatch,
		         modifier = Modifier
			         .align(Alignment.BottomCenter)
			         .padding(bottom = 50.dp))
	}
}

@Composable
fun DisplayTime(stopwatch: StopwatchViewModel, modifier: Modifier = Modifier)
{
	val state = stopwatch.state.value
	var time by remember {
		mutableLongStateOf(0L)
	}
	LaunchedEffect(key1 = time,
	               key2 = state.ticking) {
		if (state.ticking)
		{
			delay(10L)
			time = System.currentTimeMillis() - state.startTime + state.elapsedTime
		}
	}
	LaunchedEffect(key1 = state.startTime) {
		if (state.startTime == 0L) time = 0L
	}
	val millis = (time / 10) % 100
	val milDigits = (if (millis < 10) "0" else "") + millis
	val secs = (time / 1000) % 60
	val secDigits = (if (secs < 10) "0" else "") + secs
	val mins = (time / 1000 / 60) % 60
	val minDigits = (if (mins < 10) "0" else "") + mins
	val hrs = (time / 1000 / 60 / 60) % 24
	val hrDigits = (if (hrs < 10) "0" else "") + hrs
	Text(text = "$hrDigits:$minDigits:$secDigits.$milDigits",
	     color = MaterialTheme.colorScheme.primary,
	     style = MaterialTheme.typography.displayMedium,
	     modifier = modifier)
}

@Composable
fun Controls(stopwatch: StopwatchViewModel, modifier: Modifier = Modifier)
{
	val toggleTicking = {stopwatch.onEvent(StopwatchEvent.ToggleTicking)}
	val lapOrReset = {stopwatch.onEvent(StopwatchEvent.LapOrReset)}
	val state = stopwatch.state.value
	val duration = 200
	val buttonWidth = 100
	val widthAnim =
		animateDpAsState(targetValue = if (state.startTime != 0L) (3 * buttonWidth).dp else buttonWidth.dp,
		                 animationSpec = tween(durationMillis = duration),
		                 label = "Space width")
	Box(modifier = modifier.width(widthAnim.value)) {
		BoldButton(onClick = toggleTicking,
		           modifier = Modifier
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
			BoldButton(onClick = lapOrReset,
			           modifier = Modifier.width(buttonWidth.dp)) {
				if (state.ticking) Text(text = stringResource(R.string.lap))
				else Text(text = stringResource(R.string.reset))
			}
		}
	}
}

@Composable
fun BoldButton(onClick: () -> Unit,
               modifier: Modifier = Modifier,
               content: @Composable () -> Unit)
{
	Button(onClick = onClick,
	       modifier = modifier) {
		ProvideTextStyle(value = TextStyle(fontWeight = FontWeight.SemiBold)) {
			content()
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