package com.remoteconfigdebugger.provider

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

/**
 * no-op implementation
 */
class RemoteConfigDebugger private constructor(context: Context) {


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
        return FirebaseRemoteConfig.getInstance().getString(key)
    }

    fun getDouble(key: String): Double {
        return FirebaseRemoteConfig.getInstance().getDouble(key)
    }

    fun getBoolean(key: String): Boolean {
        return FirebaseRemoteConfig.getInstance().getBoolean(key)
    }

    fun getLong(key: String): Long {
        return FirebaseRemoteConfig.getInstance().getLong(key)
    }
}