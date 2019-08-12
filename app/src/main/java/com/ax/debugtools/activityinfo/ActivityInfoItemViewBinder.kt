package com.ax.debugtools.activityinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.ax.debugtools.R
import com.ax.debugtools.base.BaseViewHolder
import com.ax.debugtools.base.ItemViewBinder
import com.ax.debugtools.utils.EventUtils
import com.jeremyliao.liveeventbus.LiveEventBus

class ActivityInfoItemViewBinder : ItemViewBinder<ActivityInfoItemBean, ActivityInfoItemViewBinder.ActivityInfoViewHolder> {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ActivityInfoViewHolder {
        return ActivityInfoViewHolder(inflater.inflate(R.layout.title_switch_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ActivityInfoViewHolder, bean: ActivityInfoItemBean) {
        holder.title.text = bean.title
        holder.switchBtn.isChecked = bean.isOpen
        holder.switchBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            LiveEventBus.get().with(EventUtils.KEY_UPDATE_FLOAT_WINDOW_SERVICE_STATE).post(isChecked)
        }
    }

    class ActivityInfoViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val switchBtn: Switch = itemView.findViewById(R.id.tool_switch)
    }
}