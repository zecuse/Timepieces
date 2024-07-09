package com.zecuse.timepieces.model

import androidx.compose.material3.Typography
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zecuse.timepieces.database.AppDatabase
import com.zecuse.timepieces.ui.theme.defaultType
import com.zecuse.timepieces.ui.theme.AppColor
import com.zecuse.timepieces.ui.theme.AppTheme
import com.zecuse.timepieces.ui.view.tabs.MyTabs
import com.zecuse.timepieces.viewmodel.SettingsViewModel

/**
 * The backing state of the [SettingsViewModel]
 *
 * @param theme The current theme of the app. See [AppTheme] for valid values.
 * @param color The current color of the app. See [AppColor] for valid values.
 * @param spacing Whether the text of the app is in monospace or the device's system font.
 * @param typography The current Typography used in the app.
 */
data class SettingsState(val theme: AppTheme = AppTheme.Dark,
                         val color: AppColor = AppColor.Green,
                         val spacing: String = "mono",
                         val typography: Typography = defaultType,
                         val tabsStyle: MyTabs = MyTabs.Icon)

/**
 * The subset of [SettingsState] values to be saved to the [AppDatabase].
 */
@Entity
data class SettingsData(@PrimaryKey(autoGenerate = true)
                        val id: Int = 0,
                        val theme: AppTheme = AppTheme.Dark,
                        val color: AppColor = AppColor.Green,
                        val spacing: String = "mono",
                        val tabs: MyTabs = MyTabs.Icon)