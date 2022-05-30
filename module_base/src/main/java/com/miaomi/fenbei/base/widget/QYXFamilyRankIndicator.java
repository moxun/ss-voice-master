package com.miaomi.fenbei.base.widget;

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

public class QYXFamilyRankIndicator extends MagicIndicator {
    private Context mContext;
    private String[] tabNames = new String[]{"家族总榜", "家族月榜"};
    //    private String indicatorColor = "#FFFFFF";
    private String clipColor = "#FFFFFF";
    private String textColor = "#8581A9";
    public QYXFamilyRankIndicator(Context context) {
        super(context);
        this.mContext = context;
    }

    public QYXFamilyRankIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
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
                clipPagerTitleView.setPadding(DensityUtil.INSTANCE.dp2px(context, 45f), 0,
                        DensityUtil.INSTANCE.dp2px(context, 45f), 0);
                clipPagerTitleView.setText(tabNames[index]);
                clipPagerTitleView.setTextSize(DensityUtil.INSTANCE.dp2px(context, 13f));
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
//                LinePagerIndicator indicator =new LinePagerIndicator(context);
//                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 30f);
//                indicator.setLineHeight(navigatorHeight);
////                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 80f));
//                indicator.setRoundRadius(DensityUtil.INSTANCE.dp2px(context, 18f));
//                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
//                indicator.setColors(Color.parseColor("#ffffff"));
//                return indicator;
                return null;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(false);
        setNavigator(commonNavigator);
    }
}