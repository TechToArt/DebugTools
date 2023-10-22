package com.ax.debugtools.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils

object PermissionUtils {
    fun <T> hasAccessibilityServicePermission(context: Context, serviceClass: Class<T>): Boolean {
        var hasPermission = 0
        try {
            hasPermission = Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Settings.SettingNotFoundException) {
        }

        val ms: TextUtils.SimpleStringSplitter = TextUtils.SimpleStringSplitter(':')
        if (hasPermission == 1) {
            val settingValue: String? =
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
                )
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
        val intent = Intent(
            Settings.ACTION_ACCESSIBILITY_SETTINGS,
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    fun requestOverlayPermission(activity: Activity, requestCode: Int) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        )
        if (Build.VERSION.SDK_INT < 30) {
            //当SDK30(Android11)以下时，直接跳第二个设置页面
            intent.data = Uri.fromParts("package", activity.packageName, null)
        }
        activity.startActivityForResult(intent, requestCode)
    }

    fun checkDrawOverlaysPermission(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

}