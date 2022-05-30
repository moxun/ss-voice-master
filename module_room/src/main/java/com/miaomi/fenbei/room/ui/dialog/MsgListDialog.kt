package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
//import com.miaomi.fenbei.chatting.ui.fragment.MakingFriendsFragment
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.imkit.ui.conversation.RoomConversationFragment
import kotlinx.android.synthetic.main.room_dialog_msg_list.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MsgListDialog(val onclickListener:View.OnClickListener) : BaseBottomDialog() {
    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_msg_list
    }

    override fun bindView(v: View) {
        initContentVp(v)
    }

    private fun initContentVp(v: View) {
        var fragments = ArrayList<Fragment>()
        var fragment = RoomConversationFragment.newInstance()
        fragment.setOnclickListener{
            onclickListener.onClick(null)
        }
        fragments.add(fragment)
//        fragments.add(MakingFriendsFragment.newInstance())

        v.content_vp.adapter =  object : FragmentPagerAdapter(childFragmentManager) {
            override fun getCount(): Int {
                return fragments.size
            }

            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }
        }
//        v.title_tab.bindViewPager(listOf("用户消息", "交友广播"), v.content_vp)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }
}
