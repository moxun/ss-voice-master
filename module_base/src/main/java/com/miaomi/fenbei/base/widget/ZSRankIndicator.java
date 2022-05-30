package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.util.DensityUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

public class ZSRankIndicator extends MagicIndicator {
    private Context mContext;
    private String[] tabNames = new String[]{"今日", "昨日","总榜"};
    private String clipColor = "#C77204";
    private String textColor = "#FFFFFF";
    public ZSRankIndicator(Context context) {
        super(context);
        this.mContext = context;
    }

    public ZSRankIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }
    public void setViewPager(final ViewPager viewPager, final String[] tabs){
        ViewPagerHelper.bind(this, viewPager);
        CommonNavigator commonNavigator =new CommonNavigator(mContext);
        CommonNavigatorAdapter adpter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabs.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView =new ClipPagerTitleView(context);

                clipPagerTitleView.setText(tabs[index]);
                clipPagerTitleView.setClipColor(Color.parseColor(textColor));
                clipPagerTitleView.setTextColor(Color.parseColor(textColor));
                clipPagerTitleView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator =new LinePagerIndicator(context);
//                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 3f);
////                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 20f));
//                indicator.setRoundRadius(navigatorHeight / 2);
//                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
//                indicator.setColors(Color.parseColor("#C77204"));
                LinePagerIndicator indicator =new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 40f);
                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 20f));
                indicator.setRoundRadius(navigatorHeight / 2);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);

//                indicator.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.room_indicator_title_bg));
                indicator.setColors(Color.parseColor("#7FFF7BDE"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(true);
        setNavigator(commonNavigator);
    }

    public void setViewPager(final ViewPager viewPager){
        ViewPagerHelper.bind(this, viewPager);
        CommonNavigator commonNavigator =new CommonNavigator(mContext);
        CommonNavigatorAdapter adpter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabNames.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView =new ClipPagerTitleView(context);
                clipPagerTitleView.setText(tabNames[index]);

                clipPagerTitleView.setClipColor(Color.parseColor(textColor));
                clipPagerTitleView.setTextColor(Color.parseColor(textColor));

                clipPagerTitleView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator =new LinePagerIndicator(context);
//                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 3f);
//                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 20f));
//                indicator.setRoundRadius(navigatorHeight / 2);
//                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
//                indicator.setColors(Color.parseColor("#C77204"));

                LinePagerIndicator indicator =new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 40f);
                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 20f));
                indicator.setRoundRadius(navigatorHeight / 2);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
//                indicator.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.room_indicator_title_bg));
                indicator.setColors(Color.parseColor("#7FFF7BDE"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(true);
        setNavigator(commonNavigator);
    }
}
