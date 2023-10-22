package com.ax.debugtools

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ax.debugtools.activityinfo.ActivityInfoItemBean
import com.ax.debugtools.activityinfo.ActivityInfoItemViewBinder
import com.ax.debugtools.autoinstall.AutoInstallItemBean
import com.ax.debugtools.autoinstall.AutoInstallViewBinder
import com.ax.debugtools.base.BaseViewHolder
import com.ax.debugtools.base.ItemBean
import com.ax.debugtools.base.TypePool
import com.ax.debugtools.floatwindow.FloatWindowService
import com.ax.debugtools.utils.ConfigHelper
import com.ax.debugtools.utils.EventUtils
import com.ax.debugtools.utils.PermissionUtils
import com.jeremyliao.liveeventbus.LiveEventBus


class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 布局文件
        setContentView(R.layout.activity_main)
        val parent: ConstraintLayout = this.findViewById(R.id.parent_layout)
        val rv: RecyclerView = this.findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        val typePool = TypePool<ItemBean, BaseViewHolder>()
        // 注册功能
        typePool.register(ActivityInfoItemBean::class.java, ActivityInfoItemViewBinder())
        typePool.register(AutoInstallItemBean::class.java, AutoInstallViewBinder())
        rv.adapter = MainListAdapter(ConfigHelper.getConfig(this), typePool)

        registerEvent()

        // todo 开启辅助功能权限提醒
        // todo MVVM
    }

    private fun registerEvent() {
        // ActivityInfo悬浮窗开启关闭
        LiveEventBus.get()
            .with(EventUtils.KEY_UPDATE_FLOAT_WINDOW_SERVICE_STATE, Boolean::class.java)
            .observe(this, Observer {
                if (it) {   // 开启悬浮窗
                    // 申请浮窗权限
                    if (PermissionUtils.checkDrawOverlaysPermission(this)) {
                        startService()
                    } else {
                        PermissionUtils.requestOverlayPermission(this, REQUEST_CODE)
                    }
                } else {    // 关闭悬浮窗
                    LiveEventBus.get().with(EventUtils.KEY_STOP_FLOAT_WINDOW_SERVICE).post(null)
                    ConfigHelper.putBoolean(this, ConfigHelper.ACTIVITY_INFO, false)
                }

            })

    }

    private fun startService() {
        intent = Intent(this, FloatWindowService::class.java)
        startService(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (PermissionUtils.checkDrawOverlaysPermission(this)) {  //用户授权成功
                    startService()
                } else { //用户拒绝授权
                    Toast.makeText(application, "弹窗权限被拒绝", Toast.LENGTH_SHORT).show()
                    LiveEventBus.get().with(EventUtils.KEY_OVERLAY_PERMISSION_CHANGED)
                        .post(PackageManager.PERMISSION_GRANTED)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
