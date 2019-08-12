package com.ax.debugtools.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface ItemViewBinder<B, VH : RecyclerView.ViewHolder> {
    fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: VH, bean: B)
}