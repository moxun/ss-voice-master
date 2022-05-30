package com.miaomi.fenbei.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.miaomi.fenbei.base.R
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.ToastUtil
import kotlinx.android.synthetic.main.dialog_room_set_pwd.*

class RoomSetPwdDialog(context: Context,roomId:String) : Dialog(context, R.style.common_dialog) {
    var roomId:String = roomId
    var isLive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_room_set_pwd)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
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
        tv_close_pwd.setOnClickListener {
            onClickListener?.apply {
                onAgreeClick("")
                dismiss()
            }
        }
        getData()
    }

    public fun setContent(content:String){
        tv_content.text = content
    }

    interface OnClickListener {
        fun onRefuseClick()
        fun onAgreeClick(str :String)
    }

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener): RoomSetPwdDialog {
        this.onClickListener = onClickListener
        return this
    }
    fun getData(){
        NetService.getInstance(context).getRoomPwd(roomId,object : Callback<String>(){
            override fun onSuccess(nextPage: Int, bean: String, code: Int) {
                if (isLive){
                    et_pwd.setText(bean)
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context,msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

    override fun dismiss() {
        super.dismiss()
        isLive = false
    }
}