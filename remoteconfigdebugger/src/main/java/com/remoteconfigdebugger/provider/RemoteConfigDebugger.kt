package com.remoteconfigdebugger.provider

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.remoteconfigdebugger.utils.NotificationHandler
import com.remoteconfigdebugger.utils.SharedPreferenceHelper

/**
 * RemoteConfigDebugger methods to provide remoteConfig values
 */

class RemoteConfigDebugger private constructor(context: Context) {

    private val sharedPreferenceHelper = SharedPreferenceHelper.getInstance(context)

    init {
        NotificationHandler(context).setUpNotification()
    }

    companion object {

        @Volatile
        private var INSTANCE: RemoteConfigDebugger? = null

        fun initialiseApp(context: Context): RemoteConfigDebugger {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: kotlin.run {
                    INSTANCE = RemoteConfigDebugger(context)
                    INSTANCE!!
                }
            }
        }

        fun getInstance(): RemoteConfigDebugger {
            if (INSTANCE != null) {
                return INSTANCE!!
            } else {
                throw IllegalStateException("RemoteConfigValue class is not initialised in this process, Call initialiseApp(context) first")
            }
        }

        fun getInstance(context: Context): RemoteConfigDebugger =
            if (INSTANCE != null) {
                INSTANCE!!
            } else {
                initialiseApp(context)
            }
    }


    fun getString(key: String): String {
        val localChangedValue = sharedPreferenceHelper.getLocalValue(key)
        return if (localChangedValue != null) {
            return localChangedValue
        } else {
            FirebaseRemoteConfig.getInstance().getString(key)
        }

    }

    fun getDouble(key: String): Double {
        val localChangedValue = sharedPreferenceHelper.getLocalValue(key)
        return if (localChangedValue != null) {
            return localChangedValue.toDoubleOrNull() ?: 0.0
        } else {
            FirebaseRemoteConfig.getInstance().getDouble(key)
        }
    }

    fun getBoolean(key: String): Boolean {
        val localChangedValue = sharedPreferenceHelper.getLocalValue(key)
        return if (localChangedValue != null) {
            return localChangedValue.toBoolean()
        } else {
            FirebaseRemoteConfig.getInstance().getBoolean(key)
        }
    }

    fun getLong(key: String): Long {
        val localChangedValue = sharedPreferenceHelper.getLocalValue(key)
        return if (localChangedValue != null) {
            return localChangedValue.toLong()
        } else {
            FirebaseRemoteConfig.getInstance().getLong(key)
        }
    }
}