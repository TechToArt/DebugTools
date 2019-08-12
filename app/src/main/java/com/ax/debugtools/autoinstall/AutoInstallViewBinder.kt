package com.ax.debugtools.autoinstall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ax.debugtools.R
import com.ax.debugtools.base.BaseViewHolder
import com.ax.debugtools.base.ItemViewBinder
import com.ax.debugtools.utils.ConfigHelper

class AutoInstallViewBinder: ItemViewBinder<AutoInstallItemBean, AutoInstallViewBinder.AutoInstallViewHolder> {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        return AutoInstallViewHolder(inflater.inflate(R.layout.title_switch_layout, parent, false))
    }

    override fun onBindViewHolder(holder: AutoInstallViewHolder, bean: AutoInstallItemBean) {
        holder.title.text = bean.title
        holder.switchBtn.isChecked = bean.isOpen
        holder.switchBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            ConfigHelper.putBoolean(buttonView.context, ConfigHelper.AUTO_INSTALL, isChecked)
        }
    }

    class AutoInstallViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val switchBtn: Switch = itemView.findViewById(R.id.tool_switch)
    }
}