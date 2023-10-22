package com.ax.debugtools

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ax.debugtools.base.BaseViewHolder
import com.ax.debugtools.base.ItemBean
import com.ax.debugtools.base.ItemViewBinder
import com.ax.debugtools.base.TypePool

class MainListAdapter(
    private val data: List<ItemBean>,
    private val typePool: TypePool<ItemBean, BaseViewHolder>
) :
    RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemViewBinder = typePool.getItemViewByIndex(viewType)
        return itemViewBinder.onCreateViewHolder(LayoutInflater.from(parent.context), parent) as BaseViewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return typePool.indexOfClass(data[position].javaClass)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val itemViewBinder =
            typePool.getItemViewByClass(data[position].javaClass) as ItemViewBinder<ItemBean, BaseViewHolder>
        itemViewBinder.onBindViewHolder(holder, data[position])
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        typePool.getItemViewByIndex(holder.itemViewType).onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        typePool.getItemViewByIndex(holder.itemViewType).onViewDetachedFromWindow(holder)
    }
}