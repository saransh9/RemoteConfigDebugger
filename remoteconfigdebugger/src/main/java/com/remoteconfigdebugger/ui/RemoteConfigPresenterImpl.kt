package com.remoteconfigdebugger.ui

import androidx.lifecycle.LiveData
import com.remoteconfigdebugger.model.RemoteConfigModel
import com.remoteconfigdebugger.utils.SharedPreferenceHelper

internal class RemoteConfigPresenterImpl(
    sharedPreferenceHelper: SharedPreferenceHelper
) : RemoteConfigPresenter {

    private var remoteConfigRepository: RemoteConfigRepository =
        RemoteConfigRepositoryImpl(sharedPreferenceHelper)

    override fun getUnchangedValueCount(): LiveData<String> {
        return remoteConfigRepository.getUnchangedValueCount()
    }

    override fun getUnchangedValues(): List<RemoteConfigModel> {
        return remoteConfigRepository.getUnchangedValues()
    }

    override fun getChangedValueCount(): LiveData<String> {
        return remoteConfigRepository.getChangedValueCount()
    }

    override fun getChangedValues(): List<RemoteConfigModel> {
        return remoteConfigRepository.getChangedValues()
    }

    override fun saveValue(configModel: RemoteConfigModel, newValue: String) {
        return remoteConfigRepository.saveValue(configModel = configModel, newValue = newValue)
    }

    override fun resetValueToDefault(key: String) {
        return remoteConfigRepository.resetValueToDefault(key)
    }

    override fun filterList(
        list: List<RemoteConfigModel>,
        filter: String?
    ): List<RemoteConfigModel> {
       return list.filter {
            it.configName.contains(filter.orEmpty())
        }
    }


}