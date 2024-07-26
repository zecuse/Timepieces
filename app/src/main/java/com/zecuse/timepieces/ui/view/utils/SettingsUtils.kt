package com.zecuse.timepieces.ui.view.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zecuse.timepieces.R
import com.zecuse.timepieces.database.FakeDao
import com.zecuse.timepieces.ui.theme.AppColor
import com.zecuse.timepieces.ui.theme.AppTheme
import com.zecuse.timepieces.ui.theme.ColorSchemes
import com.zecuse.timepieces.ui.theme.SchemeParam
import com.zecuse.timepieces.ui.theme.TimepiecesTheme
import com.zecuse.timepieces.viewmodel.SettingsEvent
import com.zecuse.timepieces.viewmodel.SettingsViewModel

@Composable
fun CommonSettings(settings: SettingsViewModel)
{
	val theme = settings.state.value.theme
	var colorPick by remember {mutableStateOf(SchemeParam.None)}
	ProvideTextStyle(value = TextStyle(color = MaterialTheme.colorScheme.primary)) {
		Column(modifier = Modifier.clickable {colorPick = SchemeParam.None}) {
			Row(horizontalArrangement = Arrangement.SpaceBetween,
			    verticalAlignment = Alignment.CenterVertically,
			    modifier = Modifier
				    .fillMaxWidth()
				    .padding(5.dp)) {
				Text(text = "Theme:")
				ThemeButton(theme = theme) {
					val newTheme = when (theme)
					{
						AppTheme.Auto  -> AppTheme.Dark
						AppTheme.Dark  -> AppTheme.Light
						AppTheme.Light -> AppTheme.Auto
					}
					settings.onEvent(SettingsEvent.SetTheme(newTheme))
				}
			}
			Box {
				Column {
					Row(horizontalArrangement = Arrangement.SpaceBetween,
					    verticalAlignment = Alignment.CenterVertically,
					    modifier = Modifier
						    .fillMaxWidth()
						    .padding(5.dp)) {
						Text(text = "Primary Color:")
						ColorPicker(param = SchemeParam.Primary,
						            theme = theme,
						            desc = stringResource(R.string.pick_primary_color)) {
							colorPick = SchemeParam.Primary
						}
					}
					Row(horizontalArrangement = Arrangement.SpaceBetween,
					    verticalAlignment = Alignment.CenterVertically,
					    modifier = Modifier
						    .fillMaxWidth()
						    .padding(5.dp)) {
						Text(text = "Secondary Color:")
						ColorPicker(param = SchemeParam.Secondary,
						            theme = theme,
						            desc = stringResource(R.string.pick_secondary_color)) {
							colorPick = SchemeParam.Secondary
						}
					}
				}
				Box(modifier = Modifier.align(Alignment.Center)) {
					ShowColors(settings = settings,
					           param = SchemeParam.Primary,
					           visible = colorPick,
					           modifier = Modifier.align(Alignment.Center)) {
						colorPick = SchemeParam.None
					}
					ShowColors(settings = settings,
					           param = SchemeParam.Secondary,
					           visible = colorPick,
					           modifier = Modifier.align(Alignment.Center)) {
						colorPick = SchemeParam.None
					}
				}
			}
		}
	}
}

@Composable
fun ThemeButton(theme: AppTheme, modifier: Modifier = Modifier, onClick: () -> Unit)
{
	val autoTheme = stringResource(R.string.auto_theme)
	val darkMode = stringResource(R.string.dark_mode)
	val lightMode = stringResource(R.string.light_mode)
	Box(modifier = modifier
		.clickable {onClick()}
		.semantics {
			stateDescription = if (theme == AppTheme.Dark) darkMode else lightMode
		}) {
		if (theme == AppTheme.Light || (theme == AppTheme.Auto && !isSystemInDarkTheme()))
		{
			Icon(painter = painterResource(R.drawable.light_mode_24),
			     tint = MaterialTheme.colorScheme.primary,
			     contentDescription = null,
			     modifier = Modifier.size(35.dp))
		}
		Icon(painter = painterResource(R.drawable.lightbulb_24),
		     contentDescription = null,
		     tint = MaterialTheme.colorScheme.primary,
		     modifier = Modifier
			     .padding(top = 5.dp)
			     .size(35.dp))
		if (theme == AppTheme.Auto)
		{
			Text(text = "A",
			     color = MaterialTheme.colorScheme.onPrimary,
			     style = MaterialTheme.typography.titleMedium,
			     modifier = Modifier
				     .padding(start = 12.dp,
				              top = 8.dp)
				     .semantics {
					     this.contentDescription = autoTheme
				     })
		}
	}
}

@Composable
fun ColorPicker(param: SchemeParam,
                theme: AppTheme,
                desc: String,
                modifier: Modifier = Modifier,
                onClick: () -> Unit)
{
	if (theme != AppTheme.Auto)
	{
		Icon(painter = painterResource(R.drawable.color_lens_24),
		     contentDescription = desc,
		     tint = when (param)
		     {
			     SchemeParam.Primary   -> MaterialTheme.colorScheme.primary
			     SchemeParam.Secondary -> MaterialTheme.colorScheme.secondary
			     else                  -> Color.Unspecified
		     },
		     modifier = modifier
			     .clickable {onClick()}
			     .size(35.dp))
	}
}

@Composable
fun ShowColors(settings: SettingsViewModel,
               param: SchemeParam,
               visible: SchemeParam,
               modifier: Modifier = Modifier,
               onClick: () -> Unit)
{
	AnimatedVisibility(visible = param == visible,
	                   enter = slideInHorizontally {it},
	                   exit = slideOutHorizontally {it},
	                   modifier = modifier) {
		LazyVerticalGrid(columns = GridCells.Fixed(6),
		                 contentPadding = PaddingValues(all = 5.dp),
		                 modifier = Modifier
			                 .widthIn(min = 0.dp,
			                          max = 400.dp)
			                 .heightIn(min = 20.dp,
			                           max = 80.dp)) {
			items(count = AppColor.entries.size) {colorIdx ->
				ColorCard(theme = settings.state.value.theme,
				          param = param,
				          color = AppColor.entries[colorIdx]) {
					settings.onEvent(SettingsEvent.SetColor(param = param,
					                                        color = it))
					onClick()
				}
			}
		}
	}
}

@Composable
fun ColorCard(theme: AppTheme,
              param: SchemeParam,
              color: AppColor,
              modifier: Modifier = Modifier,
              onClick: (AppColor) -> Unit)
{
	val picked: Pair<Color, Color>
	if (theme == AppTheme.Dark || (theme == AppTheme.Auto && isSystemInDarkTheme()))
	{
		picked = when (param)
		{
			SchemeParam.Primary   -> ColorSchemes.darkPrimaryColors(color)
			SchemeParam.Secondary -> ColorSchemes.darkSecondaryColors(color)
			else                  -> Pair(Color.Unspecified,
			                              Color.Unspecified)
		}
	}
	else
	{
		picked = when (param)
		{
			SchemeParam.Primary   -> ColorSchemes.lightPrimaryColors(color)
			SchemeParam.Secondary -> ColorSchemes.lightSecondaryColors(color)
			else                  -> Pair(Color.Unspecified,
			                              Color.Unspecified)
		}
	}
	Box(contentAlignment = Alignment.Center,
	    modifier = modifier
		    .width(65.dp)
		    .height(35.dp)
		    .background(color = picked.first)
		    .clickable {onClick(color)}
		    .semantics {contentDescription = color.name}) {
		Text(text = color.name,
		     color = picked.second,
		     style = MaterialTheme.typography.labelLarge)
	}
}

@Preview
@Composable
private fun Above()
{
	val fakeDao = FakeDao()
	val fakeSettings = SettingsViewModel(fakeDao)
	TimepiecesTheme {
		CommonSettings(settings = fakeSettings)
	}
}