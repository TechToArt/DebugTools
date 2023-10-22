package com.ax.debugtools.utils

import android.content.Context
import android.content.SharedPreferences
import com.ax.debugtools.activityinfo.ActivityInfoItemBean
import com.ax.debugtools.autoinstall.AutoInstallItemBean
import com.ax.debugtools.base.ItemBean
import java.util.*

object ConfigHelper {
    private const val CONFIG_NAME = "config"
    const val ACTIVITY_INFO = "activity_info"
    const val AUTO_INSTALL = "auto_install"

    fun getConfig(context: Context): List<ItemBean> {
        val list = LinkedList<ItemBean>()
        val preferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE)
        val showActivityInfo = preferences.getBoolean(ConfigHelper.ACTIVITY_INFO, false)
        val isAutoInstall = preferences.getBoolean(ConfigHelper.AUTO_INSTALL, false)
        list.add(ActivityInfoItemBean("显示当前activity", showActivityInfo))
        list.add(AutoInstallItemBean("应用自动安装", isAutoInstall))
        return list
    }

    fun putBoolean(context: Context, key: String, value: Boolean) {
        val preferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(context: Context, key: String): Boolean {
        val preferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE)
        return preferences.getBoolean(key, false)
    }

}