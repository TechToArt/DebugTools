package com.ax.debugtools.floatwindow

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.ax.debugtools.R
import com.ax.debugtools.utils.EventUtils
import com.jeremyliao.liveeventbus.LiveEventBus


class FloatWindowManager {
    private var floatWindowView: FloatWindowView? = null
    private var windowManager: WindowManager? = null
    fun createWindow(context: Context) {
        val windowManager = getWindowManager(context)
        floatWindowView = FloatWindowView(context, object : FloatWindowView.Callback {
            override fun onDoubleTap() {
                LiveEventBus.get().with(EventUtils.KEY_STOP_FLOAT_WINDOW_SERVICE).post(null)
            }
        })
        val params = WindowManager.LayoutParams()
        params.format = PixelFormat.RGBA_8888
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.TOP or Gravity.LEFT
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        floatWindowView!!.setParams(params)

        windowManager.addView(floatWindowView, params)
    }

    fun removeWindow(context: Context) {
        if (floatWindowView != null) {
            val windowManager = getWindowManager(context)
            windowManager.removeView(floatWindowView)
            floatWindowView = null
        }
    }

    fun updateView(text: String) {
        floatWindowView?.findViewById<TextView>(R.id.activity_info)?.text = text
    }

    private fun getWindowManager(context: Context): WindowManager {
        if (windowManager == null) {
            windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
        return windowManager as WindowManager
    }
}