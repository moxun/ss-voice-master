package com.miaomi.fenbei.voice.ui.login

import android.content.Intent
import android.graphics.Paint
import android.text.TextUtils
import android.view.View
import cc.lkme.linkaccount.LinkAccount
import cc.lkme.linkaccount.callback.AbilityType
import cc.lkme.linkaccount.callback.TokenResult
import cc.lkme.linkaccount.callback.TokenResultListener
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.BaseBean
import com.miaomi.fenbei.base.bean.MineBean
import com.miaomi.fenbei.base.bean.event.LoginEvent
import com.miaomi.fenbei.base.config.BaseConfig
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.CommonLib
import com.miaomi.fenbei.base.core.dialog.LoadingDialog
import com.miaomi.fenbei.base.core.msg.MsgManager
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.Data
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.share.callback.ThirdLoginCallback
import com.miaomi.fenbei.base.share.core.ThirdLogin
import com.miaomi.fenbei.base.util.*
import com.miaomi.fenbei.base.web.WebActivity
import com.miaomi.fenbei.voice.LinkAccountAuthUIUtil
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.base.util.CommonUtils
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.LoginHelper
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.voice.BuildConfig
import com.miaomi.fenbei.voice.app.App
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.login_activity_login.*
import org.greenrobot.eventbus.EventBus


//@Route(path = "/login/login")
class LoginActivity : BaseActivity() {

    private var logining = false
    private lateinit var loadingDialog: LoadingDialog
    private var accessCodeSucceed = false
    private var loginPhone = ""

    override fun getLayoutId(): Int {
        return R.layout.login_activity_login
    }


    override fun initView() {
        setBaseStatusBar(useThemestatusBarColor = false, useStatusBarColor = false)
        onekey_login_btn.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        onekey_login_btn.paint.isAntiAlias = true
//        ImgUtil.loadGif(this@LoginActivity, R.drawable.bg_splash_effect, iv_bg)
        // ************先初始化LinkAccount监听，再调用预登录接口*************
        LinkAccount.getInstance().setTokenResultListener(object : TokenResultListener {
            override fun onSuccess(@AbilityType resultType: Int, tokenResult: TokenResult, originResult: String) {
                runOnUiThread {

                    when (resultType) {
                        AbilityType.ABILITY_ACCESS_CODE -> {
                            accessCodeSucceed = tokenResult.resultCode == 6666
                        }
                        AbilityType.ABILITY_TOKEN -> {
                            LinkAccount.getInstance().quitAuthActivity()
                            if (accessCodeSucceed){
                                NetService.getInstance(this@LoginActivity).getUserCellPhone(getChannel(tokenResult.operatorType), tokenResult.platform, tokenResult.accessToken,object : Callback<String>(){
                                    override fun onSuccess(nextPage: Int, phone: String, code: Int) {
                                        loginPhone = phone
                                        LinkAccount.getInstance().getMobileCode(5000)

                                    }

                                    override fun onError(msg: String, throwable: Throwable, code: Int) {
                                        ToastUtil.error(this@LoginActivity, msg)
                                    }

                                    override fun isAlive(): Boolean {
                                        return isLive
                                    }

                                })
                            }

                        }
                        AbilityType.ABILITY_MOBILE_TOKEN -> {
                            accessCodeSucceed = tokenResult.resultCode == 6666
                            if (tokenResult.resultCode == 6666) {
                                if (!TextUtils.isEmpty(loginPhone)){
                                    NetService.getInstance(this@LoginActivity).loginOneKey(getChannel(tokenResult.operatorType), tokenResult.platform, tokenResult.accessToken, loginPhone, object : Callback<MineBean>() {
                                        override fun onSuccess(nextPage: Int, bean: MineBean, code: Int) {
                                            getUserInfo(bean)
                                        }

                                        override fun onError(msg: String, throwable: Throwable, code: Int) {
                                            ToastUtil.error(this@LoginActivity, msg)
                                            loginFail()
                                        }

                                        override fun isAlive(): Boolean {
                                            return isLive
                                        }

                                    })
                                }

                            }
                        }
                    }
                }
            }

            override fun onFailed(resultType: Int, info: String) {
                runOnUiThread {
                    when (resultType) {
                        AbilityType.ABILITY_ACCESS_CODE -> accessCodeSucceed = false
                        AbilityType.ABILITY_TOKEN -> {
                        }
                        AbilityType.ABILITY_MOBILE_TOKEN -> {
                        }
                    }
                }
            }
        })

        // 预登录
        LinkAccount.getInstance().preLogin(5000)
        LinkAccount.getInstance().setAuthUIConfig(LinkAccountAuthUIUtil.getPortraitActivity(this@LoginActivity))

        loadingDialog = LoadingDialog(this)
        loadingDialog.setOnShowListener {
            logining = true
        }
        loadingDialog.setOnDismissListener {
            logining = false
        }


        agreement_content.setOnClickListener {
            WebActivity.start(this@LoginActivity, BaseConfig.XY_YHXY, "用户协议")
        }
        agreement_conceal.setOnClickListener {
            if (!BuildConfig.DEBUG) {
                WebActivity.start(this@LoginActivity, BaseConfig.XY_YSXY, "隐私政策")
            }else{
                if (!App.isDebugDevelop){
                    App.isDebugDevelop = true
                    ToastUtil.i(this@LoginActivity, "切换成测试环境")
                    DataHelper.saveDebugDevelopBean()
                }else{
                    App.isDebugDevelop = false
                    ToastUtil.i(this@LoginActivity, "切换成正式环境")
                    DataHelper.saveReleaseDevelopBean()
                }
            }
        }
        iv_pwd_login.setOnClickListener {
            ARouter.getInstance().build("/login/loginByPWD").navigation()

        }

        iv_wx_login.setOnClickListener {
            if (!logining) {
                if (!CommonUtils.isAppInstall(this, "com.tencent.mm")) {
                    loadingDialog.loginDismiss()
                    ToastUtil.error(this@LoginActivity, "您没有安装微信客户端")
                }else{
                    loadingDialog.setCancelable(true)
                    loadingDialog.show()
                    ThirdLogin.oauthLogin(this, SHARE_MEDIA.WEIXIN, object : ThirdLoginCallback {
                        override fun onSuc(openId: String, unionId: String, gender: String, nickName: String, face: String) {
                            startLogin(BaseConfig.LOGIN_BY_WX, openId, unionId, gender, nickName, face)
                        }

                        override fun onFail(msg: String) {
                            loadingDialog.loginDismiss()
                            ToastUtil.error(this@LoginActivity, msg)
                        }

                        override fun onCancel() {
                            loadingDialog.loginDismiss()
                            ToastUtil.error(this@LoginActivity, "登录取消")
                        }
                    })
                }
            }
        }
//        ImgUtil.loadBlurImg(this@LoginActivity, R.drawable.bg_login, iv_bg, 10, 1)
        iv_qq_login.visibility = View.GONE
        iv_qq_login.setOnClickListener {
            if (!logining) {
                loadingDialog.setCancelable(false)
                loadingDialog.show()
                ThirdLogin.oauthLogin(this, SHARE_MEDIA.QQ, object : ThirdLoginCallback {
                    override fun onSuc(openId: String, unionId: String, gender: String, nickName: String, face: String) {
                        startLogin(BaseConfig.LOGIN_BY_QQ, openId, unionId, gender, nickName, face)
                    }

                    override fun onFail(msg: String) {
                        loadingDialog.loginDismiss()
                        ToastUtil.error(this@LoginActivity, msg)
                    }

                    override fun onCancel() {
                        loadingDialog.loginDismiss()
                        ToastUtil.error(this@LoginActivity, "登录取消")
                    }
                })

            }
        }

        phone_login_btn.setOnClickListener {
//            ARouter.getInstance().build("/login/loginByPhoneGetYZM").navigation()
            sendCode()
        }
        onekey_login_btn.setOnClickListener {
            if (accessCodeSucceed){
                LinkAccount.getInstance().getLoginToken(5000)
            }else{
                ToastUtil.error(this@LoginActivity,"未获取到本机号码")
            }
        }


    }

    private fun getChannel(operatorType: String): String {
        var channel = "0"
        when (operatorType) {
            "CT" -> channel = "1"
            "CU" -> channel = "2"
        }
        return channel
    }

    private fun startLogin(type: Int, openId: String = "", unionId: String = "", gender: String = "", nickName: String = "", face: String = "") {
        DataHelper.saveIsFromThird(false)

        if (type == BaseConfig.LOGIN_BY_QQ || type == BaseConfig.LOGIN_BY_WX){
            DataHelper.saveIsFromThird(true)
        }

        doLogin(face, type, openId, unionId, gender, nickName)
    }

    private fun upLoadLoginData(type: Int, id: String){
        when (type) {
            BaseConfig.LOGIN_BY_WX -> CommonLib.onProfileSignIn("WX", id)
            BaseConfig.LOGIN_BY_QQ -> CommonLib.onProfileSignIn("QQ", id)
            BaseConfig.LOGIN_BY_PWD -> CommonLib.onProfileSignIn("PWD", id)
            else -> CommonLib.onProfileSignIn("PHONE", id)
        }
    }

    private fun doLogin(face: String, type: Int, openId: String = "", unionId: String = "", gender: String = "", nickName: String = "") {

        NetService.getInstance(this).login(face, "", "", "", type, openId, unionId, gender, nickName, object : Callback<MineBean>() {
            override fun onSuccess(nextPage: Int, bean: MineBean, code: Int) {
                upLoadLoginData(type, bean.user_id.toString())
                getUserInfo(bean)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@LoginActivity, msg)
                loginFail()
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

    private fun sendCode() {
        val phone = phone_edit.text.toString()
        if (TextUtils.isEmpty(phone) || phone.length != 11) {
            return
        }

        NetService.getInstance(this).sendCode(1,phone, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                ToastUtil.suc(this@LoginActivity, "验证码发送成功")
                ARouter.getInstance().build("/login/loginByPhone").withString("phone_number",phone).navigation()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@LoginActivity, msg)
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
                .navigation(this@LoginActivity, object : NavCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        loginSuc()
                        finish()
                    }
                })
    }

    private fun loginSuc() {
        loadingDialog.loginDismiss()
    }

    private fun loginFail() {
        loadingDialog.loginDismiss()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}
