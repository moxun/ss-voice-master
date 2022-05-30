package com.miaomi.fenbei.room

import android.app.*
import android.content.ComponentName
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.miaomi.fenbei.room.R


class RoomService : Service() {


    inner class ChatBinder : Binder() {
        val service: RoomService
            get() = this@RoomService
    }

    private val binder = ChatBinder()
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun getNotification(room_id: String): Notification {
//        val builder = Notification.Builder(this) //获取一个Notification构造器
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this).setChannelId("111111")
        } else {
            Notification.Builder(this)
        } //获取一个Notification构造器
        // 额外添加：【适配Android8.0】给NotificationManager对象设置NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            var channel = NotificationChannel("111111", "notification_name", NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
        }

        val appIntent = Intent(Intent.ACTION_VIEW)
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        appIntent.component = ComponentName(packageName, "com.miaomi.fenbei.voice.ui.main.MainActivity")
        appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)

        builder.setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_app_logo))
                .setContentTitle("山水语音") // 设置下拉列表里的标题
                .setSmallIcon(R.drawable.ic_app_logo) // 设置状态栏内的小图标
                .setContentText("正在房间ID:" + room_id + "热聊中...") // 设置上下文内容
                .setAutoCancel(false)
                .setContentIntent(PendingIntent.getActivity(this, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setWhen(System.currentTimeMillis()) // 设置该通知发生的时间
        return builder.build()
    }


    fun keepAliveNotification(): Notification {
//        val builder = Notification.Builder(this) //获取一个Notification构造器
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this).setChannelId("22222")
        } else {
            Notification.Builder(this)
        } //获取一个Notification构造器
        // 额外添加：【适配Android8.0】给NotificationManager对象设置NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            var channel = NotificationChannel("22222", "notification_name", NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
        }


        val appIntent = Intent(Intent.ACTION_VIEW)
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        appIntent.component = ComponentName(packageName, "com.miaomi.fenbei.voice.ui.main.MainActivity")
        appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)

        builder.setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_app_logo))
                .setContentTitle("山水语音") // 设置下拉列表里的标题
                .setSmallIcon(R.drawable.ic_app_logo) // 设置状态栏内的小图标
                .setContentText("点击查看详情") // 设置上下文内容
                .setAutoCancel(false)
                .setContentIntent(PendingIntent.getActivity(this, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setWhen(System.currentTimeMillis()) // 设置该通知发生的时间
        return builder.build()
    }

}
