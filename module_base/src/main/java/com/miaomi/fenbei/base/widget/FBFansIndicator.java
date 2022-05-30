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

public class FBFansIndicator extends MagicIndicator {
    private Context mContext;
    private String[] tabNames = new String[]{"铁杆粉", "真爱粉", "心动粉", "路人粉"};
    private String indicatorColor = "#FFFFFF";
    private String clipColor = "#99FFFFFF";
    private String textColor = "#FFFFFF";

    public FBFansIndicator(Context context) {
        super(context);
        this.mContext = context;
    }

    public FBFansIndicator(Context context, AttributeSet attrs) {
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

    public void setViewPager(final ViewPager viewPager) {
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
                simplePagerTitleView.setMinScale(1);
                simplePagerTitleView.setText(tabNames[index]);
                simplePagerTitleView.setTextSize(14f);
                simplePagerTitleView.setNormalColor(Color.parseColor(clipColor));
                simplePagerTitleView.setSelectedColor(Color.parseColor(indicatorColor));
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
                LinePagerIndicator indicator =new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 3f);
                indicator.setLineHeight(navigatorHeight);
                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 12f));
//                indicator.setRoundRadius(navigatorHeight / 2);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setColors(Color.parseColor("#ED52F9"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(true);
        setNavigator(commonNavigator);
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
                simplePagerTitleView.setMinScale(1);
                simplePagerTitleView.setText(tabNames[index]);
                simplePagerTitleView.setTextSize(14f);
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
                LinePagerIndicator indicator =new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 3f);
                indicator.setLineHeight(navigatorHeight);
                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 12));
//                indicator.setRoundRadius(navigatorHeight / 2);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setColors(Color.parseColor("#ED52F9"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        setNavigator(commonNavigator);
    }
}
