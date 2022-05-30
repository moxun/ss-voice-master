package com.miaomi.fenbei.room.ui.dialog.xy

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.util.DensityUtil.dp2px
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_dialog_xy_rank.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView

/**
 * Created by 
 * on 2019-06-05.
 */
class XYRankingFragment: BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.room_dialog_xy_rank
    }

    override fun initView(view: View) {
        content_fl.setOnClickListener {
            (parentFragment as XYDialog).dismiss()
        }
        back_iv.setOnClickListener { (parentFragment as XYDialog).goHome() }
        initViewPager()
        initIndicator()
        ViewPagerHelper.bind(title_tab, content_vp)
    }

    private fun initViewPager() {
        var fragments: ArrayList<Fragment> = ArrayList()
        fragments.add(XYChildRankingFragment.getInstance(XYChildRankingFragment.TYPE_TODAY))
        fragments.add(XYChildRankingFragment.getInstance(XYChildRankingFragment.TYPE_YESTERDAY))
        fragments.add(XYChildRankingFragment.getInstance(XYChildRankingFragment.TYPE_WEEK))
        content_vp.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }
        }
    }

    private fun initIndicator() {
        var titles: Array<String> = arrayOf("今日", "昨日", "周榜")
        var commonNavigator = CommonNavigator(context)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = titles[index]
                clipPagerTitleView.clipColor = Color.parseColor("#FFFFFF")
                clipPagerTitleView.textColor = Color.parseColor("#9086A4")

                clipPagerTitleView.setOnClickListener { content_vp.currentItem = index }
                return clipPagerTitleView
            }

            override fun getCount(): Int {
                return titles.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                val navigatorHeight = dp2px(context!!, 32f).toFloat()
                indicator.lineHeight = navigatorHeight
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 80f));
                //                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 80f));
                indicator.roundRadius = dp2px(context, 50f).toFloat()
                indicator.mode = LinePagerIndicator.MODE_MATCH_EDGE
                indicator.setColors(Color.parseColor("#57327F"))
                return indicator
            }
        }
        commonNavigator.isAdjustMode = true
        title_tab.navigator = commonNavigator
    }
}