package com.remoteconfigdebugger.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.remoteconfigdebugger.provider.RemoteConfigDebugger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RemoteConfig.init(this)

        button.setOnClickListener {
            RemoteConfig.getString()
        }
    }

    override fun onResume() {
        super.onResume()
        textView.text = "test ${RemoteConfigDebugger.getInstance().getString("test")}"
    }


}
