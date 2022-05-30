package com.miaomi.fenbei.base.util

import android.content.Context
import android.os.Environment
import com.miaomi.fenbei.base.R
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.bean.VersionBean
import constacne.UiType
import model.UiConfig
import model.UpdateConfig
import update.UpdateAppUtils

object VersionUtil {

    fun checkVersion(context: Context,isNeedTip:Boolean){
        NetService.getInstance(context).checkVersion(context.packageName, object : Callback<VersionBean>() {
            override fun onSuccess(nextPage: Int, bean: VersionBean, code: Int) {
                if (bean.code > AppUtils.versionCode) {
                    // ui配置
                    val uiConfig = UiConfig().apply {
                        uiType = UiType.CUSTOM
                        cancelBtnText = ""
                        customLayoutId = R.layout.view_update_dialog_km
                    }
                    // 更新配置
                    val updateConfig = UpdateConfig().apply {
                        notifyImgRes = R.drawable.ic_app_logo
                        alwaysShow = isNeedTip
                        force = (bean.status == 1)
                        checkWifi = true
                        needCheckMd5 = true
                        isShowNotification = true
                        apkSavePath = Environment.getExternalStorageDirectory().absolutePath +"/teprinciple"
                        apkSaveName = "fbapp-" +bean.code+ "-"+System.currentTimeMillis()
                    }
                    UpdateAppUtils
                            .getInstance()
                            .apkUrl(bean.download_url)
                            .updateTitle("更新提示")
                            .updateContent(bean.content)
                            .updateConfig(updateConfig)
                            .uiConfig(uiConfig)
                            .update()
                }else{
                    if (isNeedTip){
                        ToastUtil.suc(context, "最新版本哦～")
                    }
                }
            }
            override fun onError(msg: String, throwable: Throwable, code: Int) {}
            override fun isAlive(): Boolean {
                return true
            }
        })
    }
}