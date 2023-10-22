package com.ax.debugtools.base

import android.view.LayoutInflater
import android.view.ViewGroup

interface ItemViewBinder<B, VH> {
    fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH
    fun onBindViewHolder(holder: VH, bean: B)
    fun onViewAttachedToWindow(holder: VH)
    fun onViewDetachedFromWindow(holder: VH)
}