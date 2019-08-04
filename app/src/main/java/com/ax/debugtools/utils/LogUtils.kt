package com.ax.debugtools.utils

import android.util.Log
import java.lang.StringBuilder

object
LogUtils{
    private const val TAG = "DebugTools"
    fun i(tag: String, vararg msg: String){
        val builder = StringBuilder()
        msg.forEach {
            builder.append(it)
            builder.append(",")
        }
        Log.i("$TAG-$tag", builder.toString())
    }

    fun d(tag: String, vararg msg: String){
        val builder = StringBuilder()
        msg.forEach {
            builder.append(it)
            builder.append(",")
        }
        Log.d("$TAG-$tag", builder.toString())
    }
}