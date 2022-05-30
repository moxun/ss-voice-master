package com.miaomi.fenbei.room.ui.dialog.xy

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

/**
 * Created by 
 * on 2020/4/22.
 */

class XYDialog: BaseBottomDialog(), FragmentOperateImp {

    companion object {
        const val PAGE_HOME = 0
        const val PAGE_RECORD = 1
        const val PAGE_RULE = 2
        const val PAGE_RANK = 3
        const val PAGE_JACKPOT = 4
        const val PAGE_BUY = 5
    }

    private lateinit var adapter: BaseFragmentAdapter
    private var fragments = ArrayList<Fragment>()
    private var homefragment: XYHomeFragment = XYHomeFragment()

    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_xy
    }
    override fun bindView(v: View) {
        fragments.add(homefragment)
        fragments.add(XYRecordFragment())
        fragments.add(XYRuleFragment())
        fragments.add(XYRankingFragment())
        fragments.add(XYJackpotFragment())
        fragments.add(BugXyQFragment())
        adapter = BaseFragmentAdapter(childFragmentManager, fragments)
        v.content_vp.adapter = adapter
        v.content_vp.offscreenPageLimit = fragments.size
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }

    override fun goHome() {
        homefragment.refresh()
        content_vp.setCurrentItem(PAGE_HOME,false)
    }

    override fun goRecord() {
        content_vp.setCurrentItem(PAGE_RECORD,false)
    }

    override fun goRule() {
        content_vp.setCurrentItem(PAGE_RULE,false)
    }

    override fun goRank() {
        content_vp.setCurrentItem(PAGE_RANK,false)
    }

    override fun goJackpot() {
        content_vp.setCurrentItem(PAGE_JACKPOT,false)

    }

    override fun goBuy(url : String) {
        content_vp.setCurrentItem(PAGE_BUY,false)
        (fragments[5] as BugXyQFragment).loadGiftImg(url)
    }

}