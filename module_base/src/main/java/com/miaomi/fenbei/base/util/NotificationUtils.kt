package com.miaomi.fenbei.base.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.provider.Settings.EXTRA_APP_PACKAGE
import android.provider.Settings.EXTRA_CHANNEL_ID
import androidx.core.app.NotificationManagerCompat
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.umeng.commonsdk.statistics.common.DeviceConfig.getPackageName

/**
 * Created by 
 * on 2019-08-19.
 */
object NotificationUtils {

    fun checkNotifySetting(context: Context): Boolean {
        var manager = NotificationManagerCompat.from(context)
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        return manager.areNotificationsEnabled()
    }

    fun checkNotify(context: Activity) {
        if (!checkNotifySetting(context)) {
            val noticeDialog = CommonDialog(context)
            noticeDialog.setTitle("通知权限")
            noticeDialog.setContent("尚未开启通知权限，点击去开启")
            noticeDialog.setRightBt("去开启") {
                try {
                    var intent = Intent()
                    intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                    intent.putExtra(EXTRA_APP_PACKAGE, getPackageName(context))
                    intent.putExtra(EXTRA_CHANNEL_ID,  context.applicationInfo.uid)

                    //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                    intent.putExtra("app_package", getPackageName(context))
                    intent.putExtra("app_uid", context.applicationInfo.uid)

                    // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
                    //  if ("MI 6".equals(Build.MODEL)) {
                    //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    //      Uri uri = Uri.fromParts("package", getPackageName(), null);
                    //      intent.setData(uri);
                    //      // intent.setAction("com.android.settings/.SubSettings");
                    //  }
                    context.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    // 出现异常则跳转到应用设置界面： 许愿笺坚果3——OC105 API25
                    var intent = Intent()
                    //下面这种方案是直接跳转到当前应用的设置界面。
                    //https://blog.csdn.net/ysy950803/article/details/71910806
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    var uri = Uri.fromParts("package", getPackageName(context), null)
                    intent.data = uri
                    context.startActivity(intent)
                }
                noticeDialog.dismiss()
            }
            noticeDialog.show()
        }
    }
}