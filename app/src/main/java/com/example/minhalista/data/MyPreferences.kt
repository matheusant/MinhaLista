package com.example.minhalista.data

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class MyPreferences(context: Context?) {

    companion object {
        private const val DARK_STATUS = "DARK_STATUS"
        private const val SWITCH_STATE = "STATE_STATUS"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = preferences.getInt(DARK_STATUS, 0)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()

    var switchState = preferences.getBoolean(SWITCH_STATE, false)
        set(value) = preferences.edit().putBoolean(SWITCH_STATE, value).apply()

}