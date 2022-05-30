package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.miaomi.fenbei.base.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerIndicator extends LinearLayout {
    private List<ImageView> indicatorImages = new ArrayList<>();
    private Context context;
    public ViewPagerIndicator(Context context) {
        super(context);
        this.context = context;
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void init(int size){

        indicatorImages.clear();
        removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.leftMargin = 8;
            params.rightMargin = 8;
            if (i == 0) {
                imageView.setImageResource( R.drawable.common_gray_radius);
            } else {
                imageView.setImageResource(R.drawable.common_white_radius);
            }
            indicatorImages.add(imageView);
            addView(imageView, params);
        }
    }

    public void setViewPager(ViewPager viewPager){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < indicatorImages.size(); i++) {
                    if (i == position) {
                        indicatorImages.get(i).setImageResource( R.drawable.common_gray_radius);
                    } else {
                        indicatorImages.get(i).setImageResource(R.drawable.common_white_radius);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
