package com.ax.debugtools.activityinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ax.debugtools.R
import com.ax.debugtools.base.ItemViewBinder

class ActivityInfoItemViewBinder : ItemViewBinder<ActivityInfoItemBean, ActivityInfoViewHolder> {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ActivityInfoViewHolder {
        return ActivityInfoViewHolder(inflater.inflate(R.layout.title_switch_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ActivityInfoViewHolder, bean: ActivityInfoItemBean) {
        holder.title.text = bean.title
        holder.switchBtn.isChecked = bean.isOpen
        holder.switchBtn.setOnCheckedChangeListener({ buttonView, isChecked ->
            if (isChecked) {
                // todo 显示Activity信息

            } else {
                // 隐藏Activity信息
            }
        })
    }
}