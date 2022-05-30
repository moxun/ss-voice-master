package com.miaomi.fenbei.voice.ui.mine.editinfo

//import kotlinx.android.synthetic.main.activity_user_setting.authentication_tv
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.permissions.XXPermissions
import com.miaomi.fenbei.base.bean.event.LoginEvent
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.miaomi.fenbei.base.util.*
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.voice.ui.mine.ThirdUnBindActivity
import com.miaomi.fenbei.voice.ui.mine.about.AboutActivity
import com.miaomi.fenbei.voice.ui.mine.feedback.FeedbackActivity
import com.miaomi.fenbei.base.util.*
import kotlinx.android.synthetic.main.activity_user_setting.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by
 * on 2020-02-21.
 * 设置
 */
@Route(path = RouterUrl.userSetting)
class UserSettingActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_user_setting
    }

    override fun initView() {
        setBaseStatusBar(false,false)
//        send_message_st.isChecked = DataHelper.getLocalUser()!!.isSay_hello == 0





        feedback_tv.setOnClickListener { startActivity(FeedbackActivity.getIntent(this)) }

        clear_cache_tv.setOnClickListener {
            ImgUtil.clearCache(this)
            ToastUtil.suc(this,"清理成功")
//            var dialog = CommonDialog(this)
//            dialog.setTitle("清理缓存")
//                    .setContent("您确定要清除缓存内容吗？")
//                    .setLeftBt("取消") { dialog.dismiss() }
//                    .setRightBt("确定") {
//                        ImgUtil.clearCache(this)
//                        dialog.dismiss()}
//                    .show()
        }

        about_tv.setOnClickListener { startActivity(AboutActivity
                .getIntent(this, intent.getStringExtra("customServiceQq"),
                        intent.getStringExtra("familyEntryQq"))); }

        update_tv.setOnClickListener { VersionUtil.checkVersion(this,true) }

//        young_model_tv.setOnClickListener { ARouter.getInstance().build(RouterUrl.adolescentModel).navigation() }

        exit_tv.setOnClickListener {
             CommonDialog(this).setTitle("友情提示")
                    .setContent("确定退出登录么？")
                    .setLeftBt("取消", null)
                    .setRightBt("确定") { EventBus.getDefault().post(LoginEvent(false)) }
                    .show()
        }

//        authentication_tv.setOnClickListener {
//            when (DataHelper.getUserInfo()!!.identify_status) {
//                0 -> ARouter.getInstance().build(RouterUrl.identityAuthentication).navigation()
//                1 -> ARouter.getInstance().build(RouterUrl.examine).navigation()
//                2 -> ARouter.getInstance().build(RouterUrl.authenticationDetail).navigation()
//            }
//        }

        if (TextUtils.isEmpty(DataHelper.getUserInfo()!!.mobile)){
            phone_bind_tv.text = "手机绑定"
        }else{
            phone_bind_tv.text = "手机换绑"
        }

        phone_bind_tv.setOnClickListener {
            if (TextUtils.isEmpty(DataHelper.getUserInfo()!!.mobile)){
                ARouter.getInstance().build(RouterUrl.phoneBind).navigation()
            }else{
                ARouter.getInstance().build(RouterUrl.phoneChange).navigation()
            }
        }
//
//        if (TextUtils.isEmpty(DataHelper.getUserInfo()!!.mobile)){
//            pwd_setting_tv.visibility = View.GONE
//        }else{
//            pwd_setting_tv.visibility = View.VISIBLE
//        }

        pwd_setting_tv.setOnClickListener {
//            if (TextUtils.isEmpty(DataHelper.getUserInfo()!!.mobile)){
//                ARouter.getInstance().build(RouterUrl.phoneBind).navigation()
//            }else{
                ARouter.getInstance().build(RouterUrl.pwdSetting).navigation()
//            }
        }

        logout_account_ll.setOnClickListener { ARouter.getInstance().build(RouterUrl.accountManage).navigation() }

        third_bind_tv.setOnClickListener { ARouter.getInstance().build(RouterUrl.thirdUnBind).navigation() }

        young_tv.setOnClickListener { ARouter.getInstance().build(RouterUrl.adolescentModel).navigation() }

        promis_tv.setOnClickListener {
            XXPermissions.gotoPermissionSettings(this@UserSettingActivity)
        }

        if (DataHelper.getUserInfo()!!.authorization == ThirdUnBindActivity.AUTHORIZATION_TYPE_WECHAT || DataHelper.getUserInfo()!!.authorization == ThirdUnBindActivity.AUTHORIZATION_TYPE_QQ) {
            third_bind_tv.visibility = View.VISIBLE
        } else{
            third_bind_tv.visibility = View.GONE
        }

    }


//    private fun mysterySwitch(type: String) {
//        NetService.getInstance(this).mysterySwitch(type, object : Callback<BaseBean>() {
//            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
//                mystery_men_st?.let { it.isChecked = !it.isChecked }
//                getUserData()
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                ToastUtil.suc(this@UserSettingActivity, msg)
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//        })
//    }


//    private fun getUserData() {
//        if (ChatRoomManager.isInRoom())
//        NetService.getInstance(this).getMineInfo("", object : Callback<MineBean>() {
//            override fun onSuccess(nextPage: Int, bean: MineBean, code: Int) {
//                updatalUserInfo(bean)
//                ChatRoomManager.sendMsg(MsgType.SWITCH_MYSTERY, "", ChatRoomManager.getRoomId())
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//        })
//    }
}