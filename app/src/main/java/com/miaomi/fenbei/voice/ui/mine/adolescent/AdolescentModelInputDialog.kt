package com.miaomi.fenbei.voice.ui.mine.adolescent

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.miaomi.fenbei.base.bean.BaseBean
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.user_adolescent_model_input_dialog.*

/**
 * Created by
 * on 2019-09-26.
 */
class AdolescentModelInputDialog(context: Context): Dialog(context, R.style.common_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setWindowAnimations(com.miaomi.fenbei.room.R.style.dialogAnimStyle)//添加动画
        setContentView(R.layout.user_adolescent_model_input_dialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        password_pt.initStyle(R.drawable.common_bg_pwd_room, 4, 25f, R.color.white, R.color.text_color_55, 16)
        password_pt.setShowPwd(false)
        sure_tv.setOnClickListener { pwdVerify(password_pt.pwdText) }
    }

    private fun pwdVerify(pwd: String) {
        if (pwd.length < 4) {
            return
        }
        NetService.getInstance(context).youngPwdVerify(pwd, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                ToastUtil.suc(context, "验证成功")
                dismiss()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.suc(context, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

}