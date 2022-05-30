package com.miaomi.fenbei.base.widget;

import android.view.View;

public interface OnViewPagerListener {
    void onPageRelease(boolean isselected, View view);
    void onPageSelected(boolean isselected, View view);
}
