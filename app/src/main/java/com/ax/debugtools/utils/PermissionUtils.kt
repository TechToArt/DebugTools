package com.ax.debugtools.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import androidx.annotation.RequiresApi

object PermissionUtils {
    fun <T> hasAccessibilityServicePermission(context: Context, serviceClass: Class<T>): Boolean {
        var hasPermission = 0
        try {
            hasPermission = Settings.Secure.getInt(context.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
        } catch (e: Settings.SettingNotFoundException) {
        }

        val ms: TextUtils.SimpleStringSplitter = TextUtils.SimpleStringSplitter(':')
        if (hasPermission == 1) {
            val settingValue: String? =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            if (settingValue != null) {
                ms.setString(settingValue)
                while (ms.hasNext()) {
                    val accessibilityService: String = ms.next()
                    if (accessibilityService.contains(serviceClass.simpleName)) {
                        return true
                    }
                }
            }
        }
        return false
    }
    fun requestAccessibilityServicePermission(context: Context) {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestPermission(activity: Activity, requestCode: Int) {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$activity.packageName"))
        activity.startActivityForResult(intent, requestCode)
    }

    fun checkDrawOverlaysPermission(context: Context): Boolean {
        return when {
            Build.VERSION.SDK_INT >= 23 -> Settings.canDrawOverlays(context)
            else -> true
        }
    }

}