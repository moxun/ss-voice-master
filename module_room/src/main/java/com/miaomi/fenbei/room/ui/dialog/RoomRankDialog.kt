package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.room.ui.fragment.RoomRankFragment
import me.shaohui.bottomdialog.BottomDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RoomRankDialog: BottomDialog() {

    private lateinit var fragment : RoomRankFragment


    override fun bindView(v: View?) {
        EventBus.getDefault().register(this)
        //指定财富tab
        fragment = RoomRankFragment.newInstance( ChatRoomManager.getRoomId(), 1)
        if (!fragment.isAdded) {
            childFragmentManager.beginTransaction().add(R.id.cotent_fl, fragment).commit()
        } else {
            childFragmentManager.beginTransaction().show(fragment).commit()
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_room_rank
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun hideDialog(bean: RankDialoghideBean) {
//        dialog?.let { if (it.isShowing) dismiss() }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }

    override fun dismiss() {
        super.dismiss()
        EventBus.getDefault().unregister(this)
    }
}