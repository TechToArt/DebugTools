package com.ax.debugtools.floatwindow

import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.ax.debugtools.MultifunctionAccessibilityService
import com.ax.debugtools.utils.ConfigHelper
import com.ax.debugtools.utils.EventUtils
import com.ax.debugtools.utils.PermissionUtils
import com.jeremyliao.liveeventbus.LiveEventBus


class FloatWindowService : LifecycleService() {
    private var floatWindowManager: FloatWindowManager? = null
    override fun onCreate() {
        super.onCreate()
        floatWindowManager = FloatWindowManager()
        floatWindowManager!!.createWindow(this)
        // 检查AccessibilityService权限
        if (!PermissionUtils.hasAccessibilityServicePermission(this, MultifunctionAccessibilityService::class.java)) {
            floatWindowManager?.updateView("请在系统设置中开启辅助功能/无障碍")
            PermissionUtils.requestAccessibilityServicePermission(this)
        }
        registerEvent()
        ConfigHelper.putBoolean(this, ConfigHelper.ACTIVITY_INFO, true)
    }

    private fun registerEvent() {
        LiveEventBus.get().with(EventUtils.KEY_UPDATE_ACTIVITY_INFO, String::class.java).observe(this, Observer {
            floatWindowManager?.updateView(it)
        })
        LiveEventBus.get().with(EventUtils.KEY_STOP_FLOAT_WINDOW_SERVICE).observe(this, Observer {
            stopSelf()
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        floatWindowManager?.removeWindow(applicationContext)
    }

}