package com.miaomi.fenbei.room.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.Nullable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {
    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int index = getCurrentItem();
        int height = 0;
        View v = ((Fragment) getAdapter().instantiateItem(this, index)).getView();
        if (v != null) {
            v.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height = v.getMeasuredHeight();
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}