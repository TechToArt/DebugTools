package com.ax.debugtools.floatwindow

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.ax.debugtools.utils.EventUtils
import com.jeremyliao.liveeventbus.LiveEventBus


class FloatWindowService : LifecycleService() {
    private var floatWindowManager: FloatWindowManager? = null
    override fun onCreate() {
        super.onCreate()
        floatWindowManager = FloatWindowManager()
        floatWindowManager!!.createWindow(this)
        LiveEventBus.get()
            .with(EventUtils.KEY_UPDATE_ACTIVITY_INFO, String::class.java)
            .observe(this, Observer {
                floatWindowManager?.updateView(it)
            })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        floatWindowManager?.removeWindow(applicationContext)
    }

}