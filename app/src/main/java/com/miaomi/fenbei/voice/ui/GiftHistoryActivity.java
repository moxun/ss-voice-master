package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.example.indicatorlib.views.TabLayout;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class GiftHistoryActivity extends BaseActivity {

    TabLayout mDeltaIndicator;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ViewPager mViewPager;
    public static void start(Context context){
        Intent intent = new Intent(context,GiftHistoryActivity.class);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_gift_history;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        mDeltaIndicator = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.vp_gift);
        mFragmentList.add(GiftHistoryFragment.newInstance(GiftHistoryFragment.TYPE_GIFT_HISTORY_GET));
        mFragmentList.add(GiftHistoryFragment.newInstance(GiftHistoryFragment.TYPE_GIFT_HISTORY_SEND));
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mDeltaIndicator.setViewPager(mViewPager);
        mDeltaIndicator.setTitles("收到的礼物", "送出的礼物");
        mDeltaIndicator.setStripColor(Color.parseColor("#FFFFFF"));
        mDeltaIndicator.setActiveColor(Color.parseColor("#FFFFFF"));
        mDeltaIndicator.setInactiveColor(Color.parseColor("#999999"));
        mDeltaIndicator.setStripType(TabLayout.StripType.POINT);
        mDeltaIndicator.setStripGravity(TabLayout.StripGravity.BOTTOM);
        mDeltaIndicator.setAnimationDuration(300);
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
