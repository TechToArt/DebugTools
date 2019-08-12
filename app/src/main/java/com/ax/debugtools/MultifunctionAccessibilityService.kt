package com.ax.debugtools

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
import android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
import android.view.accessibility.AccessibilityNodeInfo
import com.ax.debugtools.utils.ConfigHelper
import com.ax.debugtools.utils.EventUtils
import com.ax.debugtools.utils.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus

class MultifunctionAccessibilityService : AccessibilityService() {
    override fun onCreate() {
        super.onCreate()
        LogUtils.i("oncreate", "oncreate")
    }

    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        LogUtils.i("onAccessibilityEvent", event.toString())
        if (event == null) {
            return
        }
        // 获取当前应用信息
        if (event.eventType == TYPE_WINDOW_STATE_CHANGED) {
            LogUtils.i("App info", event.packageName.toString(), event.className.toString())
            val info = event.packageName.toString() + "\n" + event.className.toString()
            LiveEventBus.get()
                .with(EventUtils.KEY_UPDATE_ACTIVITY_INFO)
                .post(info)
        }
        if (event.packageName == "android" && event.eventType == TYPE_WINDOW_CONTENT_CHANGED) {
            if (ConfigHelper.getBoolean(this, ConfigHelper.AUTO_INSTALL)) {
                // 自动点击安装, 目前VIVO上部分机型测试可用
                clickViewByText(this.rootInActiveWindow, "安装", 0)
            }
        }
    }

    private fun clickViewByText(nodeInfo: AccessibilityNodeInfo, text: String, position: Int) {
        val nodeInfo: AccessibilityNodeInfo? = findAccessibilityNodeInfosByText(nodeInfo, text, position)
        clickNode(nodeInfo)
    }

    private fun clickViewById(nodeInfo: AccessibilityNodeInfo, id: String, position: Int) {
        val nodeInfo: AccessibilityNodeInfo? = findAccessibilityNodeInfosByViewId(nodeInfo, id, position)
        clickNode(nodeInfo)
    }

    private fun clickNode(nodeInfo: AccessibilityNodeInfo?) {
        if (nodeInfo == null) {
            return
        }
        if (nodeInfo.isClickable) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            LogUtils.i("click", "点击事件发生")
        }
    }

    private fun findAccessibilityNodeInfosByText(nodeInfo: AccessibilityNodeInfo, text: String, position: Int): AccessibilityNodeInfo? {
        val targetList: List<AccessibilityNodeInfo> = nodeInfo.findAccessibilityNodeInfosByText(text)
        if (position >= 0 && targetList.size > position) {
            LogUtils.i("list", targetList.size.toString())
            return targetList[position]
        }
        return null
    }

    private fun findAccessibilityNodeInfosByViewId(nodeInfo: AccessibilityNodeInfo, id: String, position: Int): AccessibilityNodeInfo? {
        val targetList: List<AccessibilityNodeInfo> = nodeInfo.findAccessibilityNodeInfosByViewId(id)
        if (position >= 0 && targetList.size > position) {
            return targetList[position]
        }
        return null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        LogUtils.i("onServiceConnected", "onServiceConnected")
    }
}