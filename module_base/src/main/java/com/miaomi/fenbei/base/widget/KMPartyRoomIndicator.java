package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.miaomi.fenbei.base.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class KMPartyRoomIndicator extends MagicIndicator {
    private Context mContext;
//    private String[] tabNames = new String[]{"热门", "女神","男神","电台","点唱"};
    private String indicatorColor = "#FFFFFF";
    private String clipColor = "#FD7F8F";
    private String textColor = "#FFFFFF";
    public KMPartyRoomIndicator(Context context) {
        super(context);
        this.mContext = context;
    }

    public KMPartyRoomIndicator(Context context, AttributeSet attrs) {
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

    public void setViewPager(final ViewPager viewPager, final List<String> tabNames){
        ViewPagerHelper.bind(this, viewPager);
        CommonNavigator commonNavigator =new CommonNavigator(mContext);
        CommonNavigatorAdapter adpter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabNames.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(tabNames.get(index));
                simplePagerTitleView.setTextSize(16f);//设置导航的文字大小
                simplePagerTitleView.setPadding(0,0,0,0);
                simplePagerTitleView.setNormalColor(Color.parseColor("#3B3D3F"));//正常状态下的标题颜色
                simplePagerTitleView.setSelectedColor(Color.parseColor("#FF4FA5"));//选中的标题字体颜色
//                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
//                ScaleTransitionPagerTitleView simplePagerTitleView =new ScaleTransitionPagerTitleView(context);
////                simplePagerTitleView.setWidth(DensityUtil.INSTANCE.dp2px(context,100f));
////                simplePagerTitleView.setPadding(0,0,0,0);
//                simplePagerTitleView.setText(tabNames.get(index));
//                simplePagerTitleView.setTextSize(16f);
//                simplePagerTitleView.setMinScale(0.87f);
//
//                simplePagerTitleView.setNormalColor(Color.parseColor("#3B3D3F"));
//                simplePagerTitleView.setSelectedColor(Color.parseColor("#FF4FA5"));
                simplePagerTitleView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
//                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);
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
//                indicator.setColors(Color.parseColor("#FFFFFF"));
//                LinePagerIndicator indicator = new LinePagerIndicator( context );
//                float navigatorHeight = DensityUtil.INSTANCE.dp2px( context, 30 );
//                indicator.setLineHeight( navigatorHeight );

                LinePagerIndicator indicator = new LinePagerIndicator(context);
                float navigatorHeight = context.getResources().getDimension(R.dimen.dd_dp30);
                float borderWidth = UIUtil.dip2px(context, 1);
                float lineHeight = navigatorHeight - 2 * borderWidth;
                indicator.setLineHeight(lineHeight);
                indicator.setRoundRadius(lineHeight / 2);
                indicator.setYOffset(borderWidth);
                indicator.setColors(Color.parseColor("#f8eaf1"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(true);
        setNavigator(commonNavigator);
    }
}
