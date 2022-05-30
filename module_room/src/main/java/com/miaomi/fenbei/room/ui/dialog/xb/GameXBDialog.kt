package com.miaomi.fenbei.room.ui.dialog.xb

import android.view.View
import androidx.fragment.app.Fragment
import com.example.indicatorlib.base.BaseFragmentAdapter
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_dialog_xy.*
import kotlinx.android.synthetic.main.room_dialog_xy.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class GameXBDialog : BaseBottomDialog(), GameXBFragmentOperateImp {

    companion object {
        const val PAGE_HOME = 0
        const val PAGE_RECORD = 1
        const val PAGE_RULE = 2
        const val PAGE_RANK = 3
        const val PAGE_JACKPOT = 4
        const val PAGE_BUY = 5
    }

    private lateinit var gameXBRuleFragment: GameXBRuleFragment
    private lateinit var adapter: BaseFragmentAdapter
    private var fragments = ArrayList<Fragment>()

    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_xb
    }

    override fun bindView(v: View) {
        gameXBRuleFragment = GameXBRuleFragment()
        fragments.add(GameXBHomeFragment())
        fragments.add(GameXBRecordFragment())
        fragments.add(gameXBRuleFragment)
        fragments.add(GameXBRankFragment())
        adapter = BaseFragmentAdapter(childFragmentManager, fragments)
        v.content_vp.offscreenPageLimit = fragments.size
        v.content_vp.adapter = adapter
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }

    override fun goHome() {
        content_vp.setCurrentItem(PAGE_HOME,false)
    }

    override fun goRecord() {
        content_vp.setCurrentItem(PAGE_RECORD,false)
    }

    override fun goRule(str:String) {
        content_vp.setCurrentItem(PAGE_RULE,false)
        gameXBRuleFragment.setContent(str)
    }

    override fun goRank() {
        content_vp.setCurrentItem(PAGE_RANK,false)
    }


}