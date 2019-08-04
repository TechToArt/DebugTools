package com.ax.debugtools.activityinfo

import android.view.View
import android.widget.Switch
import android.widget.TextView
import com.ax.debugtools.R
import com.ax.debugtools.base.BaseViewHolder

class ActivityInfoViewHolder(itemView: View) : BaseViewHolder(itemView) {
    val title:TextView = itemView.findViewById(R.id.title)
    val switchBtn:Switch = itemView.findViewById(R.id.tool_switch)
}