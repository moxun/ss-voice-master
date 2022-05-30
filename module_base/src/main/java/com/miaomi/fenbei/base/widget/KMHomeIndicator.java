package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import androidx.viewpager.widget.ViewPager;

public class KMHomeIndicator extends MagicIndicator {
    private Context mContext;
    private String[] tabNames = new String[]{"正在玩", "女友","男友","情感","交友"};
    private String indicatorColor = "#FFFFFF";
    private String clipColor = "#FD7F8F";
    private String textColor = "#FFFFFF";
    public KMHomeIndicator(Context context) {
        super(context);
        this.mContext = context;
    }

    public KMHomeIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }
    public void setIndicatorColor(String indicatorColor){
        this.indicatorColor = indicatorColor;
    }

    public void setClipColor(String clipColor){
        this.clipColor = clipColor;
    }

    public void setTextColor(String textColor){
        this.textColor = textColor;
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
                ScaleTransitionPagerTitleView simplePagerTitleView =new ScaleTransitionPagerTitleView(context);
//                simplePagerTitleView.setWidth(DensityUtil.INSTANCE.dp2px(context,40f));
//                simplePagerTitleView.setWidth(DensityUtil.INSTANCE.dp2px(context,100f));
                simplePagerTitleView.setPadding(10,0,10,0);
                simplePagerTitleView.setText(tabNames[index]);
                simplePagerTitleView.setTextSize(18f);
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#FD7F8F"));
                simplePagerTitleView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator =new LinePagerIndicator(context);
//                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 30f);
//                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 100f));
//                indicator.setRoundRadius(navigatorHeight / 2);
//                indicator.setColors(Color.parseColor(indicatorColor));
                return null;
            }
        };
        commonNavigator.setAdapter(adpter);
//        commonNavigator.setAdjustMode(true);
        setNavigator(commonNavigator);
    }
}