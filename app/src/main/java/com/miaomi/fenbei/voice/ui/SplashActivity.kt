package com.miaomi.fenbei.voice.ui

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.CommonLib
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.LoginHelper
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.voice.ui.login.UserAgreementDialog

@Route(path = "/app/splash")
class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        setBaseStatusBar(false, false)
        if (DataHelper.getIsFirstOpenApp()) {
            UserAgreementDialog(this).setOnClickListener(object : UserAgreementDialog.OnClickListener {
                override fun onRefuseClick() {
                    finish()
                }

                override fun onAgreeClick() {
                    DataHelper.saveIsFirstOpenApp(false)
                    startLogin()

                }
            }).show()
        } else {
            startLogin()
        }
    }


    private fun startLogin() {
//        AudioPlayer.getInstance().openAssetMusics(this,"voice_splash.mp3")
        LoginHelper.INSTANCE.checkLogin(CommonLib.mContext, true, object : LoginHelper.Callback {
            override fun onSuc() {
                ARouter.getInstance().build("/main/main")
                        .navigation(this@SplashActivity, object : NavCallback() {
                            override fun onArrival(postcard: Postcard?) {
                                finish()
                            }
                        })
            }

            override fun onFail(msg: String) {
                ARouter.getInstance().build("/login/login")
                        .navigation(this@SplashActivity, object : NavCallback() {
                            override fun onArrival(postcard: Postcard?) {
                                finish()
                            }
                        })
            }
        })
    }
}
