package com.remoteconfigdebugger.utils

import android.content.Context
import com.google.gson.Gson
import com.remoteconfigdebugger.model.LocalRemoteConfigModel
import com.remoteconfigdebugger.model.RemoteConfigModel

internal class SharedPreferenceHelper private constructor(context: Context) {

    private val sharedPreference = RemoteConfigSharedPreference(context)
    private val gson = Gson()

    companion object {

        @Volatile
        private var INSTANCE: SharedPreferenceHelper? = null

        fun getInstance(context: Context): SharedPreferenceHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: kotlin.run {
                    INSTANCE = SharedPreferenceHelper(context)
                    INSTANCE!!
                }
            }

    }

    fun getAllChangedValues(): HashMap<String, RemoteConfigModel> {
        return if (!sharedPreference.localRemoteConfigValue.isNullOrEmpty() &&
            sharedPreference.localRemoteConfigValue != "{}"
        ) {
            gson.fromJson(
                sharedPreference.localRemoteConfigValue,
                LocalRemoteConfigModel::class.java
            ).defaultValue
        } else {
            hashMapOf()
        }
    }

    fun getLocalValue(key: String): String? {
        return if (!sharedPreference.localRemoteConfigValue.isNullOrEmpty() &&
            sharedPreference.localRemoteConfigValue != "{}"
        ) {
            gson.fromJson(
                sharedPreference.localRemoteConfigValue,
                LocalRemoteConfigModel::class.java
            ).defaultValue[key]?.changedConfigValue
        } else {
            null
        }

    }

    fun setLocalValue(configValue: RemoteConfigModel) {
        if (!sharedPreference.localRemoteConfigValue.isNullOrEmpty() &&
            sharedPreference.localRemoteConfigValue != "{}"
        ) {
            val localValue = gson.fromJson(
                sharedPreference.localRemoteConfigValue,
                LocalRemoteConfigModel::class.java
            )

            localValue.defaultValue[configValue.configName] = configValue
            sharedPreference.localRemoteConfigValue = gson.toJson(localValue)
        } else {
            sharedPreference.localRemoteConfigValue =
                gson.toJson(
                    LocalRemoteConfigModel(
                        defaultValue = hashMapOf(
                            Pair(
                                configValue.configName,
                                configValue
                            )
                        )
                    )
                )
        }
    }

    fun resetLocalValue(key: String) {
        if (!sharedPreference.localRemoteConfigValue.isNullOrEmpty() &&
            sharedPreference.localRemoteConfigValue != "{}"
        ) {
            val localValue = gson.fromJson(
                sharedPreference.localRemoteConfigValue,
                LocalRemoteConfigModel::class.java
            )

            localValue.defaultValue.remove(key)

            sharedPreference.localRemoteConfigValue = gson.toJson(localValue)
        }
    }
}
