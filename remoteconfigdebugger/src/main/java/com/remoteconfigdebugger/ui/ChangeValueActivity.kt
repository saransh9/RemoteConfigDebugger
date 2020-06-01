package com.remoteconfigdebugger.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.remoteconfigdebugger.R
import com.remoteconfigdebugger.model.RemoteConfigModel
import com.remoteconfigdebugger.utils.INTENT_CONFIG_MODEL
import com.remoteconfigdebugger.utils.SharedPreferenceHelper
import kotlinx.android.synthetic.main.rcd_activity_change_value.*

internal class ChangeValueActivity : AppCompatActivity() {

    private lateinit var remoteConfigRepository: RemoteConfigRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rcd_activity_change_value)
        remoteConfigRepository =
            RemoteConfigRepositoryImpl(SharedPreferenceHelper.getInstance(this@ChangeValueActivity))

        val config = intent.extras?.getParcelable(INTENT_CONFIG_MODEL) as? RemoteConfigModel
        tv_config_name.text = config?.configName
        et_value.setText(config?.let { getRemoteValue(it) }.orEmpty())
        setClickListener(config)
    }

    private fun getRemoteValue(config: RemoteConfigModel): String {
        return if (config.changedConfigValue.isNotEmpty()) {
            config.changedConfigValue
        } else {
            config.configValue
        }
    }

    private fun setClickListener(config: RemoteConfigModel?) {
        bt_save.setOnClickListener {
            config?.let {
                remoteConfigRepository.saveValue(
                    configModel = it,
                    newValue = et_value.text.toString()
                )
            }
            Toast.makeText(
                this@ChangeValueActivity,
                R.string.rcd_value_changed_successfully,
                Toast.LENGTH_LONG
            ).show()
            finish()
        }

        bt_reset_default.setOnClickListener {
            config?.let {
                remoteConfigRepository.resetValueToDefault(it.configName)
                Toast.makeText(
                    this@ChangeValueActivity,
                    R.string.rcd_value_reset_successfully,
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }
}
