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

import androidx.viewpager.widget.ViewPager;

public class KMRoomIndicator extends MagicIndicator {
    private Context mContext;
    private String[] tabNames = new String[]{"派对", "声圈"};
    private String indicatorColor = "#ED52F9";
    private String clipColor = "#FD7F8F";
    private String textColor = "#FFFFFF";
    public KMRoomIndicator(Context context) {
        super(context);
        this.mContext = context;
    }

    public KMRoomIndicator(Context context, AttributeSet attrs) {
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
//    ,"#3B3D3F","#3B3D3F"
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
                simplePagerTitleView.setPadding(28,0,28,0);
                simplePagerTitleView.setText(tabNames[index]);
                simplePagerTitleView.setTextSize(23);
                simplePagerTitleView.setNormalColor(Color.parseColor("#8A55B0"));
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
                LinePagerIndicator indicator =new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 4f);
                indicator.setLineHeight(navigatorHeight);
                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 10f));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setRoundRadius(navigatorHeight / 2);
                indicator.setColors(Color.parseColor(indicatorColor));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
//        commonNavigator.setAdjustMode(true);
        setNavigator(commonNavigator);
    }
}
