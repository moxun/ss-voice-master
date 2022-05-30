package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.base.bean.MsgGiftBean
import com.miaomi.fenbei.base.bean.MsgType
import com.miaomi.fenbei.base.bean.UserInfo
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.gift.GiftManager
import com.miaomi.fenbei.gift.listener.OnGiftListener
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class GiftDialog : BaseBottomDialog() {

    override fun getLayoutRes(): Int {

        return R.layout.room_dialog_gift
    }


    private lateinit var mChatId: String



    override fun bindView(v: View) {
        mChatId = ChatRoomManager.getRoomId()


        val fragment = GiftManager.getInstance().buildGiftFragment(object :OnGiftListener{
            override fun onSendChest(bean: MsgGiftBean, toUserInfo: UserInfo) {
                ChatRoomManager.sendMsg(MsgType.GIFT, "恭喜 ${DataHelper.getUserInfo()!!.nickname} 打开 ${bean.giftChestName}，为" +
                        " ${toUserInfo.nickname} 开出 ${bean.giftNum} 个${bean.giftName} ", sendChatId = mChatId
                        , giftBean = bean, toUserInfo = toUserInfo)
            }

            override fun onSendSuccess(bean: MsgGiftBean, toUserInfo: UserInfo) {
                ChatRoomManager.sendMsg(MsgType.GIFT, bean.giftName, sendChatId = mChatId, giftBean = bean, toUserInfo = toUserInfo)
            }

            override fun onSendExpressGiftSuccess(bean: MsgGiftBean, toUserInfo: UserInfo, note:String) {
                ChatRoomManager.sendMsg(MsgType.GIFT, note, sendChatId = mChatId, giftBean = bean, toUserInfo = toUserInfo)

            }


            override fun showPayActivity() {
                dismiss()
                ARouter.getInstance().build("/app/pay").navigation()
            }



            override fun onSendFail(msg: String) {
                ToastUtil.error(v.context, msg)
            }

            override fun showPayDialog() {
                dismiss()
                if(context != null){
                    val upDialog = CommonDialog(context!!)
                    upDialog.setTitle("友情提示")
                    upDialog.setContent("余额不足")
                    upDialog.setLeftBt("取消") {
                        upDialog.dismiss()
                    }
                    upDialog.setRightBt("去充值") {
                        upDialog.dismiss()
                        ARouter.getInstance().build("/app/pay").navigation()
                    }
                    upDialog.show()
                }
            }

        })
        childFragmentManager.beginTransaction().add(R.id.content, fragment).commit()


    }



    fun show(nickName: String, userId: Int,face:String,needSelected:Boolean, mystery: Int = 0, fragmentManager: FragmentManager?) {
        GiftManager.getInstance().face = face
        GiftManager.getInstance().userId = userId
        GiftManager.getInstance().nickName = nickName
        GiftManager.getInstance().mystery = mystery
        GiftManager.getInstance().isNeedSeleted = needSelected
        GiftManager.getInstance().roomType = ChatRoomManager.mRoomType
        super.show(fragmentManager)
        GiftManager.getInstance().roomId = mChatId
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }

}