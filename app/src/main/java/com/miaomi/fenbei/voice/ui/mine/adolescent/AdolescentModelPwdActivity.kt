package com.miaomi.fenbei.voice.ui.mine.adolescent

import com.alibaba.android.arouter.facade.annotation.Route
import com.miaomi.fenbei.base.bean.BaseBean
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.activity_adolescent_model_pwd.*

/**
 * Created by 
 * on 2019-09-26.
 * 青少年模式密码设置
 */
@Route(path = "/mine/adolescentModelPwd")
class AdolescentModelPwdActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_adolescent_model_pwd
    }

    override fun initView() {
        setBaseStatusBar(useThemestatusBarColor = false, useStatusBarColor = false)
        password_pt.initStyle(R.drawable.common_bg_pwd_room, 4, 25f, R.color.white, R.color.text_color_55, 16)
        password_pt.setShowPwd(true)
        if (DataHelper.getIsYoungModelSetting() == 0) {
            title_tv.text = "密码设置"
            reminder_tv.text = "开启青少年模式，需先设置独立密码"
        } else {
            title_tv.text = "关闭青少年模式"
            reminder_tv.text = "输入独立密码，关闭青少年模式"
        }
        back_iv.setOnClickListener { finish() }
        next_bt.setOnClickListener {
            youngModelSettingOrClose(password_pt.pwdText)
        }
    }

    private fun youngModelSettingOrClose(pwd: String) {
        if (pwd.length < 4) {
            return
        }
        if (DataHelper.getIsYoungModelSetting() == 0) {
            NetService.getInstance(this).youngPwdSet(pwd, object : Callback<BaseBean>() {
                override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                    ToastUtil.suc(this@AdolescentModelPwdActivity, "已开启青少年模式")
                    DataHelper.saveIsYoungModelSetting(1)
                    finish()
                }

                override fun onError(msg: String, throwable: Throwable, code: Int) {
                    ToastUtil.suc(this@AdolescentModelPwdActivity, msg)
                }

                override fun isAlive(): Boolean {
                    return isLive
                }
            })
        } else {
            NetService.getInstance(this).youngPwdClose(pwd, object : Callback<BaseBean>() {
                override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                    ToastUtil.suc(this@AdolescentModelPwdActivity, "已关闭青少年模式")
                    DataHelper.saveIsYoungModelSetting(0)
                    finish()
                }

                override fun onError(msg: String, throwable: Throwable, code: Int) {
                    ToastUtil.suc(this@AdolescentModelPwdActivity, msg)
                }

                override fun isAlive(): Boolean {
                    return isLive
                }
            })
        }
    }
}