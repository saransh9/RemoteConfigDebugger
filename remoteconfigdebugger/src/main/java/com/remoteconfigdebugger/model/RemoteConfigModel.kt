package com.remoteconfigdebugger.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class RemoteConfigModel(
    @SerializedName("config_name") val configName: String,
    @SerializedName("config_value") val configValue: String,
    @SerializedName("changed_config_value") val changedConfigValue: String = ""
) : Parcelable

@Parcelize
internal data class LocalRemoteConfigModel(
    @SerializedName("local_value") var defaultValue: HashMap<String, RemoteConfigModel>
) : Parcelable
