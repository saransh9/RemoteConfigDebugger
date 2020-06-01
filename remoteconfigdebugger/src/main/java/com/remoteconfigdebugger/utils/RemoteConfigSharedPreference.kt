package com.remoteconfigdebugger.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

internal class RemoteConfigSharedPreference(private val context: Context) {

    private val sharedPreference =
        context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)

    var localRemoteConfigValue: String?
        get() = sharedPreference.getString(PREFERENCE_REMOTE_CONFIG_DEBUG_VALUE, "")
        set(value) = sharedPreference.edit().putString(PREFERENCE_REMOTE_CONFIG_DEBUG_VALUE, value)
            .apply()

}
