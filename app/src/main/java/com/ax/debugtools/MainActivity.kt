package com.ax.debugtools

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ax.debugtools.activityinfo.ActivityInfoItemBean
import com.ax.debugtools.activityinfo.ActivityInfoItemViewBinder
import com.ax.debugtools.base.TypePool
import com.ax.debugtools.floatwindow.FloatWindowService
import com.ax.debugtools.utils.ConfigHelper
import com.ax.debugtools.utils.PermissionUtils


class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 布局文件
        setContentView(R.layout.activity_main)
        val parent: ConstraintLayout = this.findViewById(R.id.parent_layout)
        val rv: RecyclerView = this.findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        val typePool = TypePool()
        typePool.register(ActivityInfoItemBean::class.java, ActivityInfoItemViewBinder())
        rv.adapter = MainListAdapter(ConfigHelper.getConfig(this), typePool)

        if (!PermissionUtils.hasAccessibilityServicePermission(this, MultifunctionAccessibilityService::class.java)) {
            PermissionUtils.requestAccessibilityServicePermission(this)
        }

        // todo 动态申请权限
        if (checkPermission()) {
            startService()
        } else {
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermission()
            }
        }


        // todo 开启辅助功能权限提醒
        // todo MVVM
    }

    fun startService() {
        intent = Intent(this, FloatWindowService::class.java)
        startService(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (checkPermission()) {  //用户授权成功
                    startService()
                } else { //用户拒绝授权
                    Toast.makeText(application, "弹窗权限被拒绝", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun checkPermission(): Boolean {
        return when {
            Build.VERSION.SDK_INT >= 23 -> Settings.canDrawOverlays(this)
            else -> true
        }
    }

}
