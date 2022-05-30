package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;


import com.miaomi.fenbei.base.util.DensityUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

public class QYXRankIndicator extends MagicIndicator {
    private Context mContext;
    private String[] tabNames = new String[]{"日榜", "周榜","月榜"};
//    private String indicatorColor = "#FFFFFF";
    private String clipColor = "#6090FF";
    private String textColor = "#ffffff";
    public QYXRankIndicator(Context context) {
        super(context);
        this.mContext = context;
    }

    public QYXRankIndicator(Context context, AttributeSet attrs) {
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
                float padding = 0f;
                if (tabs.length > 3) {
                    padding = 11f;
                } else {
                    padding = 28f;
                }
                clipPagerTitleView.setPadding(DensityUtil.INSTANCE.dp2px(context, padding), 0,
                        DensityUtil.INSTANCE.dp2px(context, padding), 0);
                clipPagerTitleView.setText(tabs[index]);

                clipPagerTitleView.setClipColor(Color.parseColor(clipColor));
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
                LinePagerIndicator indicator =new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 32f);
                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 80f));
                indicator.setRoundRadius(DensityUtil.INSTANCE.dp2px(context, 8f));
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setColors(Color.parseColor("#ffffff"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(false);
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
                clipPagerTitleView.setPadding(DensityUtil.INSTANCE.dp2px(context, 28f), 0,
                        DensityUtil.INSTANCE.dp2px(context, 28f), 0);
                clipPagerTitleView.setText(tabNames[index]);

                clipPagerTitleView.setClipColor(Color.parseColor(clipColor));
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
                LinePagerIndicator indicator =new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 32f);
                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 80f));
                indicator.setRoundRadius(DensityUtil.INSTANCE.dp2px(context, 8f));
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setColors(Color.parseColor("#ffffff"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(false);
        setNavigator(commonNavigator);
    }
}
