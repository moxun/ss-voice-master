package com.miaomi.fenbei.voice.app

//import cc.lkme.linkaccount.LinkAccount
//import cc.lkme.linkaccount.callback.TokenResult
//import cc.lkme.linkaccount.callback.TokenResultListener
//import cc.lkme.linkaccount.LinkAccount

import android.app.ActivityManager
import android.content.ComponentCallbacks2
import android.content.Context
import androidx.multidex.MultiDexApplication
import cc.lkme.linkaccount.LinkAccount
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.common.internal.Supplier
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.miaomi.fenbei.base.config.BaseConfig
import com.miaomi.fenbei.base.core.CommonLib
import com.miaomi.fenbei.base.core.msg.MsgManager
import com.miaomi.fenbei.base.util.ChannelUtil
import com.miaomi.fenbei.base.util.DoubleClickUtil
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.RoomLib
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import org.litepal.LitePal


class App : MultiDexApplication() {

    companion object {
        const val MAX_MEM = 20 * ByteConstants.MB
        var isDebugDevelop = false
    }

    override fun onCreate() {
        super.onCreate()

        if (isAppProcess()) {
            MsgManager.INSTANCE.setContext(this)
            LitePal.initialize(this)
            Fresco.initialize(this, getConfigureCaches(this))
            CommonLib.init(this)
            RoomLib.init(this)
            ChatRoomManager.init(this)
            ARouter.init(this)
            DoubleClickUtil()
            UMConfigure.init(this, BaseConfig.UM_APPID, ChannelUtil.getAppChannel(this), UMConfigure.DEVICE_TYPE_PHONE, ChannelUtil.getAppChannel(this))
            PlatformConfig.setWeixin(BaseConfig.APPID_WX, BaseConfig.APPID_WX_APPSCREAT)
            PlatformConfig.setQQZone(BaseConfig.QQ_APPID, BaseConfig.QQ_APPSCREAT)
            PlatformConfig.setQQFileProvider(BaseConfig.BASE_FP)
//            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)

            LinkAccount.getInstance(this, "29b8710e5e5f54bbf6a4c5dfd40c3322")
            // 设置debug模式来输出日志
//            LinkAccount.getInstance().setDebug(true)
//            getLocationCity()
            CrashReport.initCrashReport(this, "52fee2df72", false)
        }
    }

    private fun getConfigureCaches(context: Context): ImagePipelineConfig {
        val bitmapCacheParams = MemoryCacheParams(MAX_MEM, Int.MAX_VALUE, MAX_MEM, Int.MAX_VALUE, Int.MAX_VALUE)
        val mSupplierMemoryCacheParams: Supplier<MemoryCacheParams> = Supplier { bitmapCacheParams }
        var builder = ImagePipelineConfig.newBuilder(context)
        builder.setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
        return builder.build()
    }


    private fun isAppProcess(): Boolean {
        val processName = getProcessNick()
        return processName.equals(this.packageName, true)
    }

    private fun getProcessNick(): String {
        val processId = android.os.Process.myPid()
        var processName = ""
        val manager = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val iterator = manager.runningAppProcesses.iterator()
        while (iterator.hasNext()) {
            val processInfo = (iterator.next()) as ActivityManager.RunningAppProcessInfo
            try {
                if (processInfo.pid == processId) {
                    processName = processInfo.processName
                    return processName
                }
            } catch (e: Exception) {
//                LogD(e.getMessage())
            }
        }
        return processName

    }

    override fun onLowMemory() {
        super.onLowMemory()
        ImgUtil.clearMemory(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            ImgUtil.clearMemory(this)
        }
        ImgUtil.trimMemory(this, level)
    }


}