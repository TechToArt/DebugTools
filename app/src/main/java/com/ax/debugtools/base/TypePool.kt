package com.ax.debugtools.base

import androidx.collection.ArrayMap

class TypePool<B, VH> {
    private val itemViewBinders: ArrayMap<Class<*>, ItemViewBinder<*, *>> = ArrayMap()

    fun register(clazz: Class<out B>, itemViewBinder: ItemViewBinder<out B, out VH>) {
        itemViewBinders[clazz] = itemViewBinder
    }

    fun unregister(clazz: Class<*>) {
        itemViewBinders.remove(clazz)
    }

    fun clear() {
        itemViewBinders.clear()
    }

    fun indexOfClass(clazz: Class<*>): Int {
        return itemViewBinders.indexOfKey(clazz)
    }

    fun getItemViewByIndex(index: Int): ItemViewBinder<B, VH> {
        return itemViewBinders.valueAt(index) as ItemViewBinder<B, VH>
    }

    fun getItemViewByClass(clazz: Class<*>): ItemViewBinder<B, VH>? {
        return itemViewBinders[clazz] as ItemViewBinder<B, VH>?
    }
}