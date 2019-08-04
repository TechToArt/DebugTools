package com.ax.debugtools

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ax.debugtools.base.ItemViewBinder
import com.ax.debugtools.base.TypePool

class MainListAdapter(private val data: List<Any>, private val typePool: TypePool) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemViewBinder = typePool.getItemViewByIndex(viewType)
        return itemViewBinder.onCreateViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return typePool.indexOfClass(data[position].javaClass)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewBinder =
            typePool.getItemViewByClass(data[position].javaClass) as ItemViewBinder<Any, RecyclerView.ViewHolder>
        itemViewBinder.onBindViewHolder(holder, data[position])
    }
}