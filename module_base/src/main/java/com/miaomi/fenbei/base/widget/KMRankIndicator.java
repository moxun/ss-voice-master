package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.miaomi.fenbei.base.util.DensityUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import androidx.viewpager.widget.ViewPager;

public class KMRankIndicator extends MagicIndicator {
    private Context mContext;
    private String[] tabNames = new String[]{"日榜", "周榜","月榜"};
//    private String indicatorColor = "#FFFFFF";
    private String clipColor = "#EC579D";
    private String textColor = "#80FFFFFF";
    public KMRankIndicator(Context context) {
        super(context);
        this.mContext = context;
    }

    public KMRankIndicator(Context context, AttributeSet attrs) {
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

                clipPagerTitleView.setClipColor(Color.parseColor("#4943D2"));
                clipPagerTitleView.setTextColor(Color.parseColor("#80FFFFFF"));

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
                LinePagerIndicator indicator =new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 28f);
                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 20f));
                indicator.setRoundRadius(navigatorHeight / 2);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setColors(Color.parseColor("#FFFFFF"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(true);
        setNavigator(commonNavigator);
    }
    public void setViewPager(final ViewPager viewPager, final String[] tabs,final String clipColor){
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

                clipPagerTitleView.setClipColor(Color.parseColor("#FFFFFF"));
                clipPagerTitleView.setTextColor(Color.parseColor("#80FFFFFF"));

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
                LinePagerIndicator indicator =new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 3f);
                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 20f));
                indicator.setRoundRadius(navigatorHeight / 2);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setColors(Color.parseColor("#FFFFFF"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(true);
        setNavigator(commonNavigator);
    }
    public void setViewPager(final ViewPager viewPager, final String clipColor){
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

                clipPagerTitleView.setClipColor(Color.parseColor(clipColor));
                clipPagerTitleView.setTextColor(Color.parseColor("#80FFFFFF"));

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
                LinePagerIndicator indicator =new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 28f);
                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 20f));
                indicator.setRoundRadius(navigatorHeight / 2);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setColors(Color.parseColor("#FFFFFF"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(true);
        setNavigator(commonNavigator);
    }
}
