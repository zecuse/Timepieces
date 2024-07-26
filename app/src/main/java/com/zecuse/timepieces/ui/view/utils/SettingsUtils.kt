package com.zecuse.timepieces.ui.view.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.zecuse.timepieces.R
import com.zecuse.timepieces.ui.theme.AppColor
import com.zecuse.timepieces.ui.theme.AppTheme
import com.zecuse.timepieces.ui.theme.ColorSchemes
import com.zecuse.timepieces.ui.theme.SchemeParam
import com.zecuse.timepieces.viewmodel.SettingsEvent
import com.zecuse.timepieces.viewmodel.SettingsViewModel

@Composable
fun CommonSettings(settings: SettingsViewModel)
{
	val theme = settings.state.value.theme
	Column {
		Row(horizontalArrangement = Arrangement.SpaceBetween) {
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
		Row(horizontalArrangement = Arrangement.SpaceBetween) {
			Text(text = "Primary Color:")
			ColorPicker(param = SchemeParam.Primary,
			            theme = theme) {

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
                modifier: Modifier = Modifier,
                onClick: () -> Unit)
{
	if (theme != AppTheme.Auto)
	{
		Icon(painter = painterResource(R.drawable.color_lens_24),
		     contentDescription = stringResource(R.string.pick_color),
		     tint = when (param)
		     {
			     SchemeParam.Primary   -> MaterialTheme.colorScheme.primary
			     SchemeParam.Secondary -> MaterialTheme.colorScheme.secondary
		     },
		     modifier = modifier
			     .clickable {onClick()}
			     .padding(top = 3.dp,
			              end = 7.dp)
			     .size(35.dp))
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
			SchemeParam.Primary -> ColorSchemes.darkPrimaryColors(color)
			SchemeParam.Secondary -> ColorSchemes.darkSecondaryColors(color)
		}
	}
	else
	{
		picked = when (param)
		{
			SchemeParam.Primary -> ColorSchemes.lightPrimaryColors(color)
			SchemeParam.Secondary -> ColorSchemes.lightSecondaryColors(color)
		}
	}
	Box(contentAlignment = Alignment.Center,
	    modifier = modifier
		    .size(65.dp)
		    .background(color = picked.first)
		    .clickable {onClick(color)}
		    .semantics {contentDescription = color.name}) {
		Text(text = color.name,
		     color = picked.second,
		     style = MaterialTheme.typography.labelLarge)
	}
}
