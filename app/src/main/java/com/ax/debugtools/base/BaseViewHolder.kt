package com.ax.debugtools.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open fun onAttachedToWindow() {
    }

    open fun onDetachedFromWindow() {
    }
}