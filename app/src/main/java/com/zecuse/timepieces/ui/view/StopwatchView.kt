package com.zecuse.timepieces.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zecuse.timepieces.R
import com.zecuse.timepieces.database.FakeDao
import com.zecuse.timepieces.model.TimePoint
import com.zecuse.timepieces.ui.theme.TimepiecesTheme
import com.zecuse.timepieces.ui.view.utils.CommonSettings
import com.zecuse.timepieces.ui.view.utils.ContentSettings
import com.zecuse.timepieces.ui.view.utils.SwipeContent
import com.zecuse.timepieces.ui.view.utils.SwipeSettings
import com.zecuse.timepieces.ui.view.utils.animatePlacement
import com.zecuse.timepieces.viewmodel.SettingsViewModel
import com.zecuse.timepieces.viewmodel.StopwatchEvent
import com.zecuse.timepieces.viewmodel.StopwatchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StopwatchView(settings: SettingsViewModel,
                  stopwatch: StopwatchViewModel,
                  leftHand: Boolean,
                  landscape: Boolean = false)
{
	val pagerState = rememberPagerState {2}
	val coScope = rememberCoroutineScope()
	val pageContent: @Composable (PagerScope.(page: Int) -> Unit) = {
		when (pagerState.targetPage)
		{
			0 -> StopwatchContent(stopwatch = stopwatch,
			                      leftHand = leftHand,
			                      landscape = landscape,
			                      coScope = coScope,
			                      pagerState = pagerState)
			1 -> StopwatchSettings(settings = settings,
			                       coScope = coScope,
			                       pagerState = pagerState)
		}
	}

	if (landscape) HorizontalPager(state = pagerState) {
		pageContent(this,
		            it)
	}
	else VerticalPager(state = pagerState) {
		pageContent(this,
		            it)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StopwatchContent(stopwatch: StopwatchViewModel,
                     leftHand: Boolean,
                     landscape: Boolean,
                     coScope: CoroutineScope,
                     pagerState: PagerState)
{
	Box(contentAlignment = Alignment.Center,
	    modifier = Modifier.fillMaxSize()) {
		val hasLaps = stopwatch.state.value.lapCnt != 0
		if (landscape)
		{
			val controlAlignment = if (hasLaps)
			{
				if (leftHand) Alignment.CenterEnd
				else Alignment.CenterStart
			}
			else Alignment.Center
			val lapAlignment = if (leftHand) Alignment.CenterStart
			else Alignment.CenterEnd
			DisplayLaps(stopwatch = stopwatch,
			            modifier = Modifier.fillMaxWidth(0.5f),
			            animModifier = Modifier
				            .align(lapAlignment)
				            .animatePlacement())
			Column(verticalArrangement = Arrangement.Center,
			       horizontalAlignment = Alignment.CenterHorizontally,
			       modifier = Modifier
				       .align(controlAlignment)
				       .animatePlacement()
				       .fillMaxWidth(0.5f)) {
				DisplayTime(stopwatch = stopwatch)
				Spacer(modifier = Modifier.height(50.dp))
				Controls(stopwatch = stopwatch,
				         leftHand = leftHand)
				SwipeSettings {coScope.launch {pagerState.animateScrollToPage(ContentSettings.SETTINGS.ordinal)}}
			}
		}
		else
		{
			val alignment = if (hasLaps) Alignment.TopCenter else Alignment.Center
			val tPad = animateDpAsState(targetValue = if (hasLaps) 50.dp else 0.dp,
			                            animationSpec = tween(durationMillis = 200),
			                            label = "Top padding")
			DisplayTime(stopwatch = stopwatch,
			            modifier = Modifier
				            .align(alignment)
				            .animatePlacement()
				            .padding(top = tPad.value))
			DisplayLaps(stopwatch = stopwatch,
			            modifier = Modifier.fillMaxHeight(0.68f))
			Controls(stopwatch = stopwatch,
			         modifier = Modifier
				         .align(Alignment.BottomCenter)
				         .padding(bottom = 50.dp))
			SwipeSettings(modifier = Modifier
				.align(Alignment.BottomCenter)
				.padding(bottom = 10.dp)) {coScope.launch {pagerState.animateScrollToPage(ContentSettings.SETTINGS.ordinal)}}
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StopwatchSettings(settings: SettingsViewModel,
                      coScope: CoroutineScope,
                      pagerState: PagerState)
{
	val localFocusManager = LocalFocusManager.current
	Column(modifier = Modifier
		.fillMaxSize()
		.pointerInput(Unit) {
			detectTapGestures(onTap = {localFocusManager.clearFocus()})
		}) {
		SwipeContent(icon = R.drawable.stopwatch_filled,
		             modifier = Modifier.align(Alignment.CenterHorizontally)) {coScope.launch {pagerState.animateScrollToPage(ContentSettings.CONTENT.ordinal)}}
		CommonSettings(settings = settings)
		Column(modifier = Modifier.weight(1f)) {
			(1..3).forEach {
				Text(text = it.toString())
			}
		}
	}
}

@Composable
fun DisplayTime(stopwatch: StopwatchViewModel, modifier: Modifier = Modifier)
{
	val state = stopwatch.state.value
	LaunchedEffect(key1 = state.currentTime,
	               key2 = state.ticking) {
		if (state.ticking)
		{
			delay(10L)
			stopwatch.onEvent(StopwatchEvent.SetTime(System.currentTimeMillis() - state.startTime + state.elapsedTime))
		}
	}
	LaunchedEffect(key1 = state.startTime) {
		if (state.startTime == 0L) stopwatch.onEvent(StopwatchEvent.SetTime(0L))
	}
	val point = TimePoint(state.currentTime)
	Text(text = point.toString(),
	     color = MaterialTheme.colorScheme.primary,
	     style = MaterialTheme.typography.displayMedium,
	     modifier = modifier)
}

@Composable
fun DisplayLaps(stopwatch: StopwatchViewModel,
                modifier: Modifier = Modifier,
                animModifier: Modifier = Modifier)
{
	val laps = stopwatch.state.value.laps
	val listState = rememberLazyListState()
	AnimatedVisibility(visible = stopwatch.state.value.lapCnt != 0,
	                   enter = fadeIn(animationSpec = tween(durationMillis = stopwatch.duration)),
	                   exit = fadeOut(animationSpec = tween(durationMillis = stopwatch.duration)),
	                   modifier = animModifier) {
		LaunchedEffect(key1 = laps.count()) {
			listState.animateScrollToItem(laps.count() - 1)
		}
		LazyColumn(state = listState,
		           horizontalAlignment = Alignment.CenterHorizontally,
		           verticalArrangement = Arrangement.Top,
		           reverseLayout = true,
		           modifier = modifier) {
			val lapsList = laps.toList()
			itemsIndexed(lapsList) {idx, lap ->
				val point = TimePoint(lap)
				val diff = TimePoint(if (idx > 0) lap - lapsList[idx - 1] else 0L)
				Row(horizontalArrangement = Arrangement.Center,
				    modifier = Modifier.fillMaxWidth(0.9f)) {
					ProvideTextStyle(value = MaterialTheme.typography.titleLarge) {
						Text(text = "${if (idx + 1 < 10) "0" else ""}${idx + 1}",
						     color = MaterialTheme.colorScheme.secondary,
						     modifier = Modifier.weight(0.3f))
						Text(text = "$point",
						     color = MaterialTheme.colorScheme.primary,
						     textAlign = TextAlign.End,
						     modifier = Modifier.weight(1f))
						Text(text = "$diff",
						     color = MaterialTheme.colorScheme.primary,
						     textAlign = TextAlign.End,
						     modifier = Modifier.weight(1f))
					}
				}
			}
		}
	}
}

@Composable
fun Controls(stopwatch: StopwatchViewModel,
             modifier: Modifier = Modifier,
             leftHand: Boolean = true)
{
	val toggleTicking = {stopwatch.onEvent(StopwatchEvent.ToggleTicking)}
	val lapOrReset = {stopwatch.onEvent(StopwatchEvent.LapOrReset)}
	val state = stopwatch.state.value
	val duration = 250
	val buttonWidth = 100
	val widthAnim =
		animateDpAsState(targetValue = if (state.startTime != 0L) (3 * buttonWidth).dp else buttonWidth.dp,
		                 animationSpec = tween(durationMillis = duration),
		                 label = "Space width")
	Box(modifier = modifier.width(widthAnim.value)) {
		Button(onClick = toggleTicking,
		       modifier = Modifier
			       .align(if (leftHand) Alignment.CenterStart else Alignment.CenterEnd)
			       .width(buttonWidth.dp)) {
			Text(text = if (state.ticking) stringResource(R.string.pause)
			else if (state.startTime != 0L) stringResource(R.string.resume)
			else stringResource(R.string.start),
			     fontWeight = FontWeight.SemiBold)
		}
		AnimatedVisibility(visible = state.startTime != 0L,
		                   enter = fadeIn(animationSpec = tween(durationMillis = duration)),
		                   exit = fadeOut(animationSpec = tween(durationMillis = duration)),
		                   modifier = Modifier.align(if (!leftHand) Alignment.CenterStart else Alignment.CenterEnd)) {
			Button(onClick = lapOrReset,
			       modifier = Modifier.width(buttonWidth.dp)) {
				Text(text = if (state.ticking) stringResource(R.string.lap)
				else stringResource(R.string.reset),
				     fontWeight = FontWeight.SemiBold)
			}
		}
	}
}

@Preview
@Composable
private fun StopwatchPreview()
{
	val fakeDao = FakeDao()
	val fakeSettings = SettingsViewModel(fakeDao)
	val fakeStopwatch = StopwatchViewModel(fakeDao)
	fakeStopwatch.onEvent(StopwatchEvent.ToggleTicking)
	TimepiecesTheme {
		StopwatchView(settings = fakeSettings,
		              stopwatch = fakeStopwatch,
		              leftHand = true)
	}
}