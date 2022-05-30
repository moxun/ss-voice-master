package com.miaomi.fenbei.voice.ui.mine

import android.app.Activity
import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.RouterUrl
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.voice.ui.mine.account_manage.AccountLogoffActivity.Companion.TYPE_REMOVE_WARRANT_QQ
import com.miaomi.fenbei.voice.ui.mine.account_manage.AccountLogoffActivity.Companion.TYPE_REMOVE_WARRANT_WECHAT
import com.miaomi.fenbei.voice.ui.mine.pwd.PwdSettingActivity
import com.umeng.socialize.UMShareAPI
import kotlinx.android.synthetic.main.activity_account_third_unbind.*

@Route(path = RouterUrl.thirdUnBind)
class ThirdUnBindActivity : BaseActivity() {

    companion object {
        const val AUTHORIZATION_TYPE_WECHAT = 1
        const val AUTHORIZATION_TYPE_QQ = 2
        const val REQUEST_CODE_REMOVE_WARRANT_WECHAT = 10001
        const val REQUEST_CODE_REMOVE_WARRANT_QQ = 10002
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_account_third_unbind
    }


    override fun initView() {
        qq_logoff_layout.isEnabled = false
        if (DataHelper.getUserInfo()!!.authorization == AUTHORIZATION_TYPE_WECHAT) {
            wechat_logoff_layout.visibility = View.VISIBLE
            wechat_authorization_tv.text = "已绑定微信"
            wechat_logoff_tv.visibility = View.VISIBLE
        } else if (DataHelper.getUserInfo()!!.authorization == AUTHORIZATION_TYPE_QQ) {
            qq_logoff_layout.visibility = View.VISIBLE
            qq_authorization_tv.text = "已绑定QQ"
            qq_logoff_tv.visibility = View.VISIBLE
        }
        wechat_logoff_layout.setOnClickListener {
            removeWarrant(TYPE_REMOVE_WARRANT_WECHAT)
        }
        qq_logoff_layout.setOnClickListener {
            removeWarrant(TYPE_REMOVE_WARRANT_QQ)
        }
    }

    private fun removeWarrant(type: Int) {
        when {
            DataHelper.getUserInfo()!!.mobile.isEmpty() -> {
                CommonDialog(this)
                        .setTitle("友情提示").setContent("为确保账号安全，该操作需进行手机验证\n，请先绑定手机号码")
                        .setRightBt("确定") { ARouter.getInstance().build(RouterUrl.phoneBind).navigation(this) }
                        .setLeftBt("取消", null)
                        .show()
            }
            DataHelper.getUserInfo()!!.is_pwd == 0 -> {
                CommonDialog(this)
                        .setTitle("友情提示").setContent("请设置登录密码后，再进行该操作")
                        .setRightBt("确定") { PwdSettingActivity.start(this) }
                        .setLeftBt("取消", null)
                        .show()
            }
            else -> {
                ARouter.getInstance().build(RouterUrl.accountLogoff)
                        .withInt("type", type)
                        .navigation(this, if (type == TYPE_REMOVE_WARRANT_QQ) REQUEST_CODE_REMOVE_WARRANT_QQ else REQUEST_CODE_REMOVE_WARRANT_WECHAT)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            REQUEST_CODE_REMOVE_WARRANT_WECHAT -> {
                wechat_authorization_tv.text = "已绑定微信"
                wechat_logoff_tv.visibility = View.GONE
            }
            REQUEST_CODE_REMOVE_WARRANT_QQ -> {
                qq_authorization_tv.text = "已绑定QQ"
                qq_logoff_tv.visibility = View.GONE
            }
        }
    }
}