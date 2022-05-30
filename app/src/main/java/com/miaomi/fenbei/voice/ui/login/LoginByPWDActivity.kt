package com.miaomi.fenbei.voice.ui.login

import android.text.TextUtils
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.MineBean
import com.miaomi.fenbei.base.config.BaseConfig
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.CommonLib
import com.miaomi.fenbei.base.core.dialog.LoadingDialog
import com.miaomi.fenbei.base.core.msg.MsgManager
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.DoubleClickUtil
import com.miaomi.fenbei.base.util.LoginHelper
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.activity_login_by_pwd.*

@Route(path = "/login/login")
class LoginByPWDActivity : BaseActivity() {

    private var logining = false

    override fun getLayoutId(): Int {
        return R.layout.activity_login_by_pwd
    }

    private lateinit var loadingDialog: LoadingDialog

    override fun initView() {
        setBaseStatusBar(useThemestatusBarColor = false, useStatusBarColor = false)
        loadingDialog = LoadingDialog(this)
        loadingDialog.setOnShowListener {
            logining = true
        }
        loadingDialog.setOnDismissListener {
            logining = false
        }
        phone_edit.hint = "请输入ID号码"

        login_btn.setOnClickListener {
            if (!logining) {
                loadingDialog.setCancelable(false)
                if (TextUtils.isEmpty(phone_edit.text.toString()) || TextUtils.isEmpty(pwd_edit.text.toString())) {
                    ToastUtil.error(this, "请输入ID号码或密码")
                } else {
                    if (DoubleClickUtil.isFastDoubleClick()) {
                        loadingDialog.show()
                        startLogin(BaseConfig.LOGIN_BY_PWD)
                    }
                }
            }
        }
    }


    private fun startLogin(type: Int, openId: String = "", unionId: String = "", gender: String = "", nickName: String = "", face: String = "") {
        DataHelper.saveIsFromThird(false)

        if (type == BaseConfig.LOGIN_BY_QQ || type == BaseConfig.LOGIN_BY_WX) {
            DataHelper.saveIsFromThird(true)
        }

        doLogin(face, type, openId, unionId, gender, nickName)
    }

    private fun upLoadLoginData(type: Int, id: String) {
        when (type) {
            BaseConfig.LOGIN_BY_WX -> CommonLib.onProfileSignIn("WX", id)
            BaseConfig.LOGIN_BY_QQ -> CommonLib.onProfileSignIn("QQ", id)
            BaseConfig.LOGIN_BY_PWD -> CommonLib.onProfileSignIn("PWD", id)
            else -> CommonLib.onProfileSignIn("PHONE", id)
        }
    }

    private fun doLogin(face: String, type: Int, openId: String = "", unionId: String = "", gender: String = "", nickName: String = "") {
        NetService.getInstance(this).login(face, pwd_edit.text.toString(), phone_edit.text.toString(), "", type, openId, unionId, gender, nickName, object : Callback<MineBean>() {
            override fun onSuccess(nextPage: Int, bean: MineBean, code: Int) {
                upLoadLoginData(type, bean.user_id.toString())
                getUserInfo(bean)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@LoginByPWDActivity, msg)
                loginFail()
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

    private fun getUserInfo(loginBean: MineBean) {
        DataHelper.saveIsNewUser(loginBean.isIs_new_user)
        DataHelper.saveLoginToken(loginBean.token)
        DataHelper.saveUID(loginBean.user_id)
        DataHelper.saveTimSign(loginBean.sig)
        LoginHelper.INSTANCE.loginSuc(loginBean.user_id, loginBean.token, loginBean.sig, loginBean)
        MsgManager.INSTANCE.joinBigGroup()
        ARouter.getInstance().build("/main/main")
            .withTransition(R.anim.anim_bottom_in, R.anim.anim_bottom_out)
            .navigation(this@LoginByPWDActivity, object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                    loginSuc()
                    finish()
                }
            })
    }

    private fun loginSuc() {
        loadingDialog.loginDismiss()
        login_btn.text = "登录成功"
    }

    private fun loginFail() {
        loadingDialog.loginDismiss()
        login_btn.text = "登录"
    }
}