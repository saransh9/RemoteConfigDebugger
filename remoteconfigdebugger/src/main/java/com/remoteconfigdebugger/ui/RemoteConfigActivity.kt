package com.remoteconfigdebugger.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.remoteconfigdebugger.R
import com.remoteconfigdebugger.model.RemoteConfigModel
import com.remoteconfigdebugger.utils.INTENT_CONFIG_MODEL
import com.remoteconfigdebugger.utils.SharedPreferenceHelper
import kotlinx.android.synthetic.main.rcd_activity_remoteconfig.*


internal class RemoteConfigActivity : AppCompatActivity() {

    private var changedList: List<RemoteConfigModel> = mutableListOf()
    private var unchangedList: List<RemoteConfigModel> = mutableListOf()
    private lateinit var remoteConfigPresenter: RemoteConfigPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rcd_activity_remoteconfig)
        remoteConfigPresenter =
            RemoteConfigPresenterImpl(SharedPreferenceHelper.getInstance(this@RemoteConfigActivity))
        observeValues()
        loadChangedValues()
        loadUnchangedValues()
        handleSearchBar()
    }

    override fun onResume() {
        super.onResume()
        rootView.requestFocus()
        setData()
        if (searchView.query.isNullOrEmpty()) {
            updateRecyclerView(unchangedValueList = unchangedList, changedValueList = changedList)
        }
    }

    private fun updateRecyclerView(
        unchangedValueList: List<RemoteConfigModel>,
        changedValueList: List<RemoteConfigModel>
    ) {
        (rv_default_remote_config.adapter as RemoteConfigListAdapter).updateList(
            unchangedValueList
        )
        (rv_changed_remote_config.adapter as RemoteConfigListAdapter).updateList(
            changedValueList
        )
    }


    private fun setData() {
        changedList = remoteConfigPresenter.getChangedValues()
        unchangedList = remoteConfigPresenter.getUnchangedValues()
    }

    private fun handleSearchBar() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                updateValuesOnSearch(newText)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                updateValuesOnSearch(newText)
                return false
            }
        }
        )
    }

    private fun updateValuesOnSearch(query: String?) {
        val unchangedList = remoteConfigPresenter.filterList(
            list = unchangedList,
            filter = query
        )
        val changedList = remoteConfigPresenter.filterList(
            list = changedList,
            filter = query
        )
        tv_default_values.text = getString(R.string.rcd_default_name, unchangedList.size.toString())
        tv_changed_values.text = getString(R.string.rcd_changed, changedList.size.toString())

        updateRecyclerView(
            unchangedValueList = unchangedList,
            changedValueList = changedList
        )
    }

    private fun observeValues() {
        remoteConfigPresenter.getUnchangedValueCount().observe(this, Observer {
            if (searchView.query.isNullOrEmpty()) {
                tv_default_values.text = getString(R.string.rcd_default_name, it)
            }
        })

        remoteConfigPresenter.getChangedValueCount().observe(this, Observer {
            if (searchView.query.isNullOrEmpty()) {
                tv_changed_values.text = getString(R.string.rcd_changed, it)
            }
        })
    }

    private fun loadUnchangedValues() {
        rv_default_remote_config.apply {
            adapter = RemoteConfigListAdapter(
                dataList = unchangedList,
                onItemClick = {
                    startActivity(
                        Intent(
                            this@RemoteConfigActivity,
                            ChangeValueActivity::class.java
                        ).apply {
                            putExtra(INTENT_CONFIG_MODEL, it)
                        }
                    )
                }
            )
            addItemDecoration(
                DividerItemDecoration(
                    rv_default_remote_config.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun loadChangedValues() {
        rv_changed_remote_config.apply {
            adapter = RemoteConfigListAdapter(
                dataList = changedList,
                onItemClick = {
                    startActivity(
                        Intent(
                            this@RemoteConfigActivity,
                            ChangeValueActivity::class.java
                        ).apply {
                            putExtra(INTENT_CONFIG_MODEL, it)
                        }
                    )
                }
            )
            addItemDecoration(
                DividerItemDecoration(
                    rv_default_remote_config.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
