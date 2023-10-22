package com.ax.debugtools.activityinfo

import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.Observer
import com.ax.debugtools.R
import com.ax.debugtools.base.BaseViewHolder
import com.ax.debugtools.base.ItemViewBinder
import com.ax.debugtools.utils.EventUtils
import com.jeremyliao.liveeventbus.LiveEventBus

class ActivityInfoItemViewBinder :
    ItemViewBinder<ActivityInfoItemBean, ActivityInfoItemViewBinder.ActivityInfoViewHolder> {
    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ActivityInfoViewHolder {
        return ActivityInfoViewHolder(inflater.inflate(R.layout.title_switch_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ActivityInfoViewHolder, bean: ActivityInfoItemBean) {
        holder.title.text = bean.title
        holder.switchBtn.isChecked = bean.isOpen
        holder.switchBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            LiveEventBus.get().with(EventUtils.KEY_UPDATE_FLOAT_WINDOW_SERVICE_STATE)
                .post(isChecked)
        }

    }

    override fun onViewAttachedToWindow(holder: ActivityInfoViewHolder) {
        holder.onAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holder: ActivityInfoViewHolder) {
        holder.onDetachedFromWindow()
    }

    class ActivityInfoViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val switchBtn: Switch = itemView.findViewById(R.id.tool_switch)
        private val overlayPermissionObserver = Observer<Int> {
            if (it == PackageManager.PERMISSION_GRANTED) {
                switchBtn.isChecked = false
            }
        }

        override fun onAttachedToWindow() {
            LiveEventBus.get()
                .with(EventUtils.KEY_OVERLAY_PERMISSION_CHANGED, Int::class.java)
                .observeForever(overlayPermissionObserver)
        }

        override fun onDetachedFromWindow() {
            LiveEventBus.get()
                .with(EventUtils.KEY_OVERLAY_PERMISSION_CHANGED, Int::class.java)
                .removeObserver(overlayPermissionObserver)
        }
    }
}