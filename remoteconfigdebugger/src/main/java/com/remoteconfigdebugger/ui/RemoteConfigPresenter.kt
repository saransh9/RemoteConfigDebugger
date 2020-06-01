package com.remoteconfigdebugger.ui

import androidx.lifecycle.LiveData
import com.remoteconfigdebugger.model.RemoteConfigModel

internal interface RemoteConfigPresenter {
    fun getUnchangedValueCount(): LiveData<String>

    fun getUnchangedValues(): List<RemoteConfigModel>

    fun getChangedValueCount(): LiveData<String>

    fun getChangedValues(): List<RemoteConfigModel>

    fun saveValue(configModel: RemoteConfigModel, newValue: String)

    fun resetValueToDefault(key: String)

    fun filterList(list: List<RemoteConfigModel>, filter: String?): List<RemoteConfigModel>
}