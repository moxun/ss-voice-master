package com.miaomi.fenbei.voice.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.dialog_close_exchage_pwd.*

class ExchangeClosePwdDialog(context: Context) : Dialog(context, R.style.common_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_close_exchage_pwd)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        et_pwd.initStyle(R.drawable.common_bg_pwd, 4, 25f, R.color.white, R.color.text_color_55, 16)
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

    interface OnClickListener {
        fun onRefuseClick()
        fun onAgreeClick(str:String)
    }

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener): ExchangeClosePwdDialog {
        this.onClickListener = onClickListener
        return this
    }
}