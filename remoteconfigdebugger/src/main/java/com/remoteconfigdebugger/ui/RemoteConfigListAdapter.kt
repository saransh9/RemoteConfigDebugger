package com.remoteconfigdebugger.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.remoteconfigdebugger.R
import com.remoteconfigdebugger.model.RemoteConfigModel


internal class RemoteConfigListAdapter(
    private var dataList: List<RemoteConfigModel>,
    private val onItemClick: (data: RemoteConfigModel) -> Unit
) : RecyclerView.Adapter<RemoteConfigListAdapter.RemoteConfigListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteConfigListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rcd_item_config, parent, false)

        return RemoteConfigListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RemoteConfigListViewHolder, position: Int) {
        holder.bindTo(
            data = dataList[position],
            onItemClick = onItemClick
        )
    }

    fun updateList(newList: List<RemoteConfigModel>) {
        dataList = newList
        notifyDataSetChanged()
    }

    class RemoteConfigListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvConfigName: TextView = itemView.findViewById(R.id.tv_remote_config_name)
        private val tvConfigValue: TextView = itemView.findViewById(R.id.tv_remote_config_value)
        private val itemLayout: ConstraintLayout = itemView.findViewById(R.id.layout_item)

        fun bindTo(
            data: RemoteConfigModel,
            onItemClick: (data: RemoteConfigModel) -> Unit
        ) {
            tvConfigName.text = data.configName
            if (data.changedConfigValue.isNotEmpty()) {
                tvConfigValue.text = data.changedConfigValue
            } else {
                tvConfigValue.text = data.configValue
            }
            itemLayout.setOnClickListener {
                onItemClick(data)
            }
        }
    }
}
