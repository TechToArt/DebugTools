package com.ax.debugtools

import android.app.Application
import com.jeremyliao.liveeventbus.LiveEventBus

class DTApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        LiveEventBus.get()
            .config()
            .supportBroadcast(this)
            .lifecycleObserverAlwaysActive(true)
            .autoClear(false)
    }
}