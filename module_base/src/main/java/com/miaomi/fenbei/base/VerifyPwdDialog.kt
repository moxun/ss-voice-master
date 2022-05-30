package com.miaomi.fenbei.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.miaomi.fenbei.base.R
import kotlinx.android.synthetic.main.dialog_close_exchage_verify.*

class VerifyPwdDialog(context: Context) : Dialog(context, R.style.common_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_close_exchage_verify)
        setCancelable(false)
        setCanceledOnTouchOutside(false)


        et_pwd.initStyle(R.drawable.common_bg_pwd_room, 4, 25f, R.color.white, R.color.text_color_55, 16)
        et_pwd.setShowPwd(true)
        tv_left.setOnClickListener {
            onClickListener?.apply {
                onRefuseClick()
                dismiss()
            }
        }
        tv_right.setOnClickListener {
            onClickListener?.apply {
                onAgreeClick(et_pwd.pwdText)
                dismiss()
            }
        }
    }

    public fun setContent(content:String){
        tv_content.text = content
    }

    interface OnClickListener {
        fun onRefuseClick()
        fun onAgreeClick(str :String)
    }

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener): VerifyPwdDialog {
        this.onClickListener = onClickListener
        return this
    }
}