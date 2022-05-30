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

public class KMGiftTypeIndicator extends MagicIndicator {
    private Context mContext;
    //    private String[] tabNames = new String[]{"普通", "宝箱","贵族","表白","背包"};
    private String indicatorColor = "#FFFFFF";
    private String clipColor = "#FEAF7D";
    private String textColor = "#FFFFFF";

    public KMGiftTypeIndicator(Context context) {
        super(context);
        this.mContext = context;
    }

    public KMGiftTypeIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public void setIndicatorColor(String indicatorColor) {
        this.indicatorColor = indicatorColor;
    }

    public void setClipColor(String clipColor) {
        this.clipColor = clipColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setViewPager(final ViewPager viewPager, final String[] tabNames, final String normalColor, final String selectedColor) {
        ViewPagerHelper.bind(this, viewPager);
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        CommonNavigatorAdapter adpter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabNames.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(tabNames[index]);
                simplePagerTitleView.setTextSize(16f);
                simplePagerTitleView.setNormalColor(Color.parseColor(normalColor));
                simplePagerTitleView.setSelectedColor(Color.parseColor(selectedColor));
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
                return null;
            }
        };
        commonNavigator.setAdapter(adpter);
//        commonNavigator.setAdjustMode(true);
        setNavigator(commonNavigator);
    }
}
