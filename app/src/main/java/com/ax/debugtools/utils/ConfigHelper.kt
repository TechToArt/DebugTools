package com.ax.debugtools.utils

import android.content.Context
import com.ax.debugtools.activityinfo.ActivityInfoItemBean
import java.util.*

object ConfigHelper {
    private const val CONFIG_NAME = "config"
    private const val ACTIVITY_INFO = "activity_info"

    fun getConfig(context: Context): List<Any> {
        val list = LinkedList<Any>()
        val preferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE)
        val showActivityInfo = preferences.getBoolean(ConfigHelper.ACTIVITY_INFO, false)
        list.add(ActivityInfoItemBean("显示当前activity", showActivityInfo))
        return list
    }

}