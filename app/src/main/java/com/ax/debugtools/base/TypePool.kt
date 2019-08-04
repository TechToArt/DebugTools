package com.ax.debugtools.base

import androidx.collection.ArrayMap

class TypePool {
    private val itemViewBinders: ArrayMap<Class<*>, ItemViewBinder<*, *>> = ArrayMap()

    fun <T> register(clazz: Class<T>, itemViewBinder: ItemViewBinder<T, *>) {
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

    fun getItemViewByIndex(index: Int):ItemViewBinder<*, *> {
        return itemViewBinders.valueAt(index)
    }

    fun getItemViewByClass(clazz: Class<*>):ItemViewBinder<*, *>? {
        return itemViewBinders[clazz]
    }
}