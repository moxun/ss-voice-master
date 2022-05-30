package com.miaomi.fenbei.voice.ui.mine.account_manage

import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.RouterUrl
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.activity_account_setting.*

/**
 * Created by
 * on 2020-02-21.
 * 账号管理
 */
@Route(path = RouterUrl.accountSetting)
class AccountSettingActivity: BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_account_setting
    }

    override fun initView() {
        authentication_tv.setOnClickListener {
            when (DataHelper.getUserInfo()!!.identify_status) {
                0 -> ARouter.getInstance().build(RouterUrl.identityAuthentication).navigation()
                1 -> ARouter.getInstance().build(RouterUrl.examine).navigation()
                2 -> ARouter.getInstance().build(RouterUrl.authenticationDetail).navigation()
            }
        }

        phone_bind_tv.setOnClickListener {
            if (TextUtils.isEmpty(DataHelper.getUserInfo()!!.mobile)){
                ARouter.getInstance().build(RouterUrl.phoneBind).navigation()
            }else{
                ARouter.getInstance().build(RouterUrl.phoneChange).navigation()
            }
        }

        pwd_setting_tv.setOnClickListener {
            if (TextUtils.isEmpty(DataHelper.getUserInfo()!!.mobile)){
                ARouter.getInstance().build(RouterUrl.phoneBind).navigation()
            }else{
                ARouter.getInstance().build(RouterUrl.pwdSetting).navigation()
            }
        }

        logout_account_tv.setOnClickListener { ARouter.getInstance().build(RouterUrl.accountManage).navigation() }
    }
}