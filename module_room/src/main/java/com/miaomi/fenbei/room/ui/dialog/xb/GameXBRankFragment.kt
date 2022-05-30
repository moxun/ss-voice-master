package com.miaomi.fenbei.room.ui.dialog.xb

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_fragment_game_xb_rank.*

class GameXBRankFragment : BaseFragment(){
    override fun getLayoutId(): Int {
        return R.layout.room_fragment_game_xb_rank
    }

    override fun initView(view: View) {
        initViewPager()
    }

    private fun initViewPager() {
        val fragments: ArrayList<Fragment> = ArrayList()
        fragments.add(GameXBRankChildFragment.getInstance(GameXBRankChildFragment.TYPE_TODAY))
        fragments.add(GameXBRankChildFragment.getInstance(GameXBRankChildFragment.TYPE_YESTERDAY))
        fragments.add(GameXBRankChildFragment.getInstance(GameXBRankChildFragment.TYPE_WEEK))
        content_vp.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }
        }
        title_tab.setViewPager(content_vp)
        iv_close.setOnClickListener{
            (parentFragment as GameXBDialog).goHome()
        }
    }
}