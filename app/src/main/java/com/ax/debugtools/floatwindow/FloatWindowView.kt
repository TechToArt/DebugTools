package com.ax.debugtools.floatwindow

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.ax.debugtools.R

class FloatWindowView(context: Context) : LinearLayout(context) {
    private var xInView: Float = 0F
    private var yInView: Float = 0F
    private var xInScreen: Float = 0F
    private var yInScreen: Float = 0F
    private var params: WindowManager.LayoutParams? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.float_window_layout, this)
    }

    fun setParams(params: WindowManager.LayoutParams) {
        this.params = params
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                xInView = event.x
                yInView = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                xInScreen = event.rawX
                yInScreen = event.rawY
                updateViewPosition()
            }
        }
        return super.onTouchEvent(event)
    }

    private fun updateViewPosition() {
        if (params == null) {
            return
        }
        params!!.x = (xInScreen - xInView).toInt()
        params!!.y = (yInScreen - yInView).toInt()
        getWindowManager(context).updateViewLayout(this, params)
    }

    private fun getWindowManager(context: Context): WindowManager {
        return context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
}