package com.ax.debugtools.floatwindow

import android.content.Context
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.LinearLayout
import com.ax.debugtools.R
import com.ax.debugtools.utils.LogUtils
import kotlin.math.roundToInt

class FloatWindowView(context: Context, callback: Callback) : LinearLayout(context) {
    private var xInView: Float = 0F
    private var yInView: Float = 0F
    private var xInScreen: Float = 0F
    private var yInScreen: Float = 0F
    private var params: WindowManager.LayoutParams? = null
    private var gestureDetector: GestureDetector? = null

    interface Callback {
        fun onDoubleTap()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.float_window_layout, this)
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                callback.onDoubleTap()
                return super.onDoubleTap(e)
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                LogUtils.i("Scroll", e2?.action.toString(), distanceX.toString(), distanceY.toString())
                if (e1 != null) {
                    xInView = e1.x
                    yInView = e1.y
                }
                if (e2 != null) {
                    xInScreen = e2.rawX
                    yInScreen = e2.rawY
                }
                updateViewPosition()
                return super.onScroll(e1, e2, distanceX, distanceY)
            }
        })
    }

    fun setParams(params: WindowManager.LayoutParams) {
        this.params = params
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector!!.onTouchEvent(event)
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