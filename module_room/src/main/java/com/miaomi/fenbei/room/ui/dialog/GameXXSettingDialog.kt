package com.miaomi.fenbei.room.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.miaomi.fenbei.base.bean.BaseBean
import com.miaomi.fenbei.base.bean.MsgType
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.dialog_game_xx_setting.*

class GameXXSettingDialog(context: Context) : Dialog(context, R.style.common_dialog) {
    var selectType = 3;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setWindowAnimations(R.style.dialogAnimStyle)//添加动画
        setContentView(R.layout.dialog_game_xx_setting)
        init()
    }

    private fun init(){
        changeStatus(DataHelper.getXXType())
        fl_type_1.setOnClickListener {
            changeStatus(1)
        }
        fl_type_2.setOnClickListener {
            changeStatus(2)
        }
        fl_type_3.setOnClickListener {
            changeStatus(3)
        }
        fl_type_5.setOnClickListener {
            changeStatus(5)
        }
        tv_submit.setOnClickListener {
            DataHelper.saveXXType(selectType)
            openMortal()
        }
    }

    private fun openMortal(){
        NetService.getInstance(context!!).openMortal(ChatRoomManager.getRoomId(),DataHelper.getXXType(),object : Callback<BaseBean>(){
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                ToastUtil.error(context!!,"开启成功")
                ChatRoomManager.sendMsg(MsgType.GAME_LSP_MSG, "开启${DataHelper.getXXType()}张灵兽牌玩法", ChatRoomManager.getRoomId())
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!,msg)
            }

            override fun isAlive(): Boolean {
                return true
            }

        })
    }

    private fun changeStatus(type:Int){
        fl_type_5.isSelected = false
        fl_type_3.isSelected = false
        fl_type_2.isSelected = false
        fl_type_1.isSelected = false
        if (type == 1){
            fl_type_1.isSelected = true
        }
        if (type == 2){
            fl_type_2.isSelected = true
        }
        if (type == 3){
            fl_type_3.isSelected = true
        }
        if (type == 5){
            fl_type_5.isSelected = true
        }
        selectType = type
    }



}