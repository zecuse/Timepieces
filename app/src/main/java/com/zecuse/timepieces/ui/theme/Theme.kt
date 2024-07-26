package com.zecuse.timepieces.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.zecuse.timepieces.model.SettingsState

/**
 * Selects 1 of 12 [ColorSchemes].
 *
 * These are a subset of the colors defined in [AllColors].
 */
enum class AppColor
{
	Red, Orange, Yellow, Lime, Green, Spring, Cyan, Sky, Blue, Violet, Purple, Magenta
}

/**
 * Determines if the app is displayed in a light or dark theme or handled by the device.
 *
 * Devices supporting dynamic theming will ignore the [AppColor] value.
 */
enum class AppTheme
{
	Auto, Dark, Light
}

enum class SchemeParam
{
	None, Primary, Secondary
}

/**
 * Defines a [ColorScheme] for each of the [AppColor] values for the [AppTheme.Light] and [AppTheme.Dark] themes.
 */
object ColorSchemes
{
	private fun baseDarkColors(): ColorScheme
	{
		return darkColorScheme(background = AllColors.Grey15)
	}

	private fun baseLightColors(): ColorScheme
	{
		return lightColorScheme(background = AllColors.Grey90)
	}

	fun darkPrimaryColors(color: AppColor): Pair<Color, Color>
	{
		var primary: Color = Color.Unspecified
		var onPrimary: Color = Color.Unspecified
		when (color)
		{
			AppColor.Red     ->
			{
				primary = AllColors.Red70
				onPrimary = AllColors.Folly15
			}
			AppColor.Orange  ->
			{
				primary = AllColors.Tangelo70
				onPrimary = AllColors.Orange15
			}
			AppColor.Yellow  ->
			{
				primary = AllColors.Amber70
				onPrimary = AllColors.Yellow15
			}
			AppColor.Lime    ->
			{
				primary = AllColors.Chartreuse70
				onPrimary = AllColors.Lime15
			}
			AppColor.Green   ->
			{
				primary = AllColors.Green70
				onPrimary = AllColors.Harlequin15
			}
			AppColor.Spring  ->
			{
				primary = AllColors.Spring70
				onPrimary = AllColors.Erin15
			}
			AppColor.Cyan    ->
			{
				primary = AllColors.Aquamarine70
				onPrimary = AllColors.Cyan15
			}
			AppColor.Sky     ->
			{
				primary = AllColors.Azure70
				onPrimary = AllColors.Sky15
			}
			AppColor.Blue    ->
			{
				primary = AllColors.Blue70
				onPrimary = AllColors.Persian15
			}
			AppColor.Violet  ->
			{
				primary = AllColors.Indigo70
				onPrimary = AllColors.Violet15
			}
			AppColor.Purple  ->
			{
				primary = AllColors.Purple70
				onPrimary = AllColors.Fuchsia15
			}
			AppColor.Magenta ->
			{
				primary = AllColors.Magenta70
				onPrimary = AllColors.Rose15
			}
		}

		return Pair(primary,
		            onPrimary)
	}

	fun lightPrimaryColors(color: AppColor): Pair<Color, Color>
	{
		var primary: Color = Color.Unspecified
		var onPrimary: Color = Color.Unspecified
		when (color)
		{
			AppColor.Red     ->
			{
				primary = AllColors.Red15
				onPrimary = AllColors.Folly70
			}
			AppColor.Orange  ->
			{
				primary = AllColors.Tangelo15
				onPrimary = AllColors.Orange70
			}
			AppColor.Yellow  ->
			{
				primary = AllColors.Amber15
				onPrimary = AllColors.Yellow70
			}
			AppColor.Lime    ->
			{
				primary = AllColors.Chartreuse15
				onPrimary = AllColors.Lime70
			}
			AppColor.Green   ->
			{
				primary = AllColors.Green15
				onPrimary = AllColors.Harlequin70
			}
			AppColor.Spring  ->
			{
				primary = AllColors.Spring15
				onPrimary = AllColors.Erin70
			}
			AppColor.Cyan    ->
			{
				primary = AllColors.Aquamarine15
				onPrimary = AllColors.Cyan70
			}
			AppColor.Sky     ->
			{
				primary = AllColors.Azure15
				onPrimary = AllColors.Sky70
			}
			AppColor.Blue    ->
			{
				primary = AllColors.Blue15
				onPrimary = AllColors.Persian70
			}
			AppColor.Violet  ->
			{
				primary = AllColors.Indigo15
				onPrimary = AllColors.Violet70
			}
			AppColor.Purple  ->
			{
				primary = AllColors.Purple15
				onPrimary = AllColors.Fuchsia70
			}
			AppColor.Magenta ->
			{
				primary = AllColors.Magenta15
				onPrimary = AllColors.Rose70
			}
		}

		return Pair(primary,
		            onPrimary)
	}

	fun darkSecondaryColors(color: AppColor): Pair<Color, Color>
	{
		var secondary: Color = Color.Unspecified
		var onSecondary: Color = Color.Unspecified
		when (color)
		{
			AppColor.Red     ->
			{
				secondary = AllColors.Tangelo70
				onSecondary = AllColors.Red15
			}
			AppColor.Orange  ->
			{
				secondary = AllColors.Amber70
				onSecondary = AllColors.Orange15
			}
			AppColor.Yellow  ->
			{
				secondary = AllColors.Lime70
				onSecondary = AllColors.Yellow15
			}
			AppColor.Lime    ->
			{
				secondary = AllColors.Harlequin70
				onSecondary = AllColors.Chartreuse15
			}
			AppColor.Green   ->
			{
				secondary = AllColors.Erin70
				onSecondary = AllColors.Green15
			}
			AppColor.Spring  ->
			{
				secondary = AllColors.Aquamarine70
				onSecondary = AllColors.Spring15
			}
			AppColor.Cyan    ->
			{
				secondary = AllColors.Sky70
				onSecondary = AllColors.Cyan15
			}
			AppColor.Sky     ->
			{
				secondary = AllColors.Persian70
				onSecondary = AllColors.Azure15
			}
			AppColor.Blue    ->
			{
				secondary = AllColors.Indigo70
				onSecondary = AllColors.Blue15
			}
			AppColor.Violet  ->
			{
				secondary = AllColors.Purple70
				onSecondary = AllColors.Violet15
			}
			AppColor.Purple  ->
			{
				secondary = AllColors.Magenta70
				onSecondary = AllColors.Fuchsia15
			}
			AppColor.Magenta ->
			{
				secondary = AllColors.Folly70
				onSecondary = AllColors.Rose15
			}
		}

		return Pair(secondary,
		            onSecondary)
	}

	fun lightSecondaryColors(color: AppColor): Pair<Color, Color>
	{
		var secondary: Color = Color.Unspecified
		var onSecondary: Color = Color.Unspecified
		when (color)
		{
			AppColor.Red     ->
			{
				secondary = AllColors.Tangelo15
				onSecondary = AllColors.Red70
			}
			AppColor.Orange  ->
			{
				secondary = AllColors.Amber15
				onSecondary = AllColors.Orange70
			}
			AppColor.Yellow  ->
			{
				secondary = AllColors.Lime15
				onSecondary = AllColors.Yellow70
			}
			AppColor.Lime    ->
			{
				secondary = AllColors.Harlequin15
				onSecondary = AllColors.Chartreuse70
			}
			AppColor.Green   ->
			{
				secondary = AllColors.Erin15
				onSecondary = AllColors.Green70
			}
			AppColor.Spring  ->
			{
				secondary = AllColors.Aquamarine15
				onSecondary = AllColors.Spring70
			}
			AppColor.Cyan    ->
			{
				secondary = AllColors.Sky15
				onSecondary = AllColors.Cyan70
			}
			AppColor.Sky     ->
			{
				secondary = AllColors.Persian15
				onSecondary = AllColors.Azure70
			}
			AppColor.Blue    ->
			{
				secondary = AllColors.Indigo15
				onSecondary = AllColors.Blue70
			}
			AppColor.Violet  ->
			{
				secondary = AllColors.Purple15
				onSecondary = AllColors.Violet70
			}
			AppColor.Purple  ->
			{
				secondary = AllColors.Magenta15
				onSecondary = AllColors.Fuchsia70
			}
			AppColor.Magenta ->
			{
				secondary = AllColors.Folly15
				onSecondary = AllColors.Rose70
			}
		}

		return Pair(secondary,
		            onSecondary)
	}

	fun darkColors(primary: AppColor, secondary: AppColor): ColorScheme
	{
		val scheme = baseDarkColors()
		val primaryColors = darkPrimaryColors(primary)
		val secondaryColors = darkSecondaryColors(secondary)
		return scheme.copy(primary = primaryColors.first,
		                   onPrimary = primaryColors.second,
		                   secondary = secondaryColors.first,
		                   onSecondary = secondaryColors.second)
	}

	fun lightColors(primary: AppColor, secondary: AppColor): ColorScheme
	{
		val scheme = baseLightColors()
		val primaryColors = lightPrimaryColors(primary)
		val secondaryColors = lightSecondaryColors(secondary)
		return scheme.copy(primary = primaryColors.first,
		                   onPrimary = primaryColors.second,
		                   secondary = secondaryColors.first,
		                   onSecondary = secondaryColors.second)
	}
}

@Composable
fun TimepiecesTheme(settings: SettingsState = SettingsState(),
                    content: @Composable () -> Unit)
{
	val dynamicAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
	val dynamicTheme = dynamicAvailable && settings.theme == AppTheme.Auto
	val darkTheme =
		(settings.theme == AppTheme.Auto && isSystemInDarkTheme()) || settings.theme == AppTheme.Dark
	val colorScheme = when
	{
		dynamicTheme && darkTheme  -> dynamicDarkColorScheme(LocalContext.current)
		dynamicTheme && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
		darkTheme                  -> ColorSchemes.darkColors(settings.primaryColor,
		                                                      settings.secondaryColor)
		else                       -> ColorSchemes.lightColors(settings.primaryColor,
		                                                       settings.secondaryColor)
	}
	val contrastColors = when
	{
		settings.theme == AppTheme.Auto && isSystemInDarkTheme() -> TextContrast(high = AllColors.White,
		                                                                         med = AllColors.Grey90,
		                                                                         low = AllColors.Grey70)
		settings.theme == AppTheme.Dark                          -> TextContrast(high = AllColors.White,
		                                                                         med = AllColors.Grey90,
		                                                                         low = AllColors.Grey70)
		else                                                     -> TextContrast(high = AllColors.Black,
		                                                                         med = AllColors.Grey15,
		                                                                         low = AllColors.Grey35)
	}

	CompositionLocalProvider(LocalTextContrast provides contrastColors) {
		MaterialTheme(colorScheme = colorScheme,
		              typography = settings.typography,
		              content = content)
	}
}