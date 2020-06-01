package com.remoteconfigdebugger.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.remoteconfigdebugger.model.RemoteConfigModel
import com.remoteconfigdebugger.utils.SharedPreferenceHelper

internal class RemoteConfigRepositoryImpl(
    private val sharedPreferenceHelper: SharedPreferenceHelper
) : RemoteConfigRepository {

    private val unchangedValueCount = MutableLiveData<String>()
    private val changedValueCount = MutableLiveData<String>()


    override fun getUnchangedValueCount(): LiveData<String> {
        return unchangedValueCount
    }


    override fun getUnchangedValues(): List<RemoteConfigModel> {
        val changedValues = sharedPreferenceHelper.getAllChangedValues()
        val firebaseValuesList = mutableListOf<RemoteConfigModel>()
        FirebaseRemoteConfig.getInstance().all.map {
            if (!changedValues.containsKey(it.key)) {
                firebaseValuesList.add(
                    RemoteConfigModel(
                        configName = it.key,
                        configValue = it.value.asString()
                    )
                )
            }
        }
        unchangedValueCount.value = firebaseValuesList.size.toString()
        return firebaseValuesList
    }

    override fun getChangedValueCount(): LiveData<String> {
        return changedValueCount
    }

    override fun getChangedValues(): List<RemoteConfigModel> {
        val changedValueList = sharedPreferenceHelper.getAllChangedValues().values.toList()
        changedValueCount.value = changedValueList.size.toString()
        return changedValueList
    }

    override fun saveValue(configModel: RemoteConfigModel, newValue: String) {
        sharedPreferenceHelper.setLocalValue(
            configModel.copy(
                changedConfigValue = newValue
            )
        )
    }

    override fun resetValueToDefault(key: String) {
        sharedPreferenceHelper.resetLocalValue(key)
    }
}
