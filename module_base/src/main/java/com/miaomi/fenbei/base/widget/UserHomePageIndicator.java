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

public class UserHomePageIndicator  extends MagicIndicator {
    private Context mContext;
    private String[] tabNames = new String[]{"礼物墙", "动态"};
    private String indicatorColor = "#FFFFFF";
    private String clipColor = "#FD7F8F";
    private String textColor = "#FFFFFF";
    public UserHomePageIndicator(Context context) {
        super(context);
        this.mContext = context;
    }

    public UserHomePageIndicator(Context context, AttributeSet attrs) {
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
                simplePagerTitleView.setMinScale(0.87f);
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#FFFFFF"));
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
//                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 3f);
//                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 20f));
//                indicator.setRoundRadius(navigatorHeight / 2);
//                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
//                indicator.setColors(Color.parseColor("#FF8D8D"));
                return null;
            }
        };
        commonNavigator.setAdapter(adpter);
//        commonNavigator.setAdjustMode(true);
        setNavigator(commonNavigator);
    }
}
