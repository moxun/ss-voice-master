package com.miaomi.fenbei.voice.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.widget.KMRankIndicator;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RankRoomTypeFragment extends BaseFragment {

    private ViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private KMRankIndicator mTablayout;
    private FrameLayout toolbarFl;
    private String[] tabNames = new String[]{"日榜", "周榜", "月榜"};
    public final static String RANK_TYPE = "rank_type";
    public final static int RANK_TYPE_ML = 0;
    public final static int RANK_TYPE_GX = 1;
    public final static int RANK_TYPE_ROOM = 2;
    public final static int TIME_TYPE_DAY = 0;
    public final static int TIME_TYPE_WEEK = 1;
    public final static int TIME_TYPE_ALL = 2;
    private int rankType;

    public static RankRoomTypeFragment newInstance(int type) {
        RankRoomTypeFragment fragment = new RankRoomTypeFragment();
        Bundle args = new Bundle();
        args.putInt(RANK_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rank_type;
    }

    @Override
    public void initView(@NotNull View view) {
        rankType = getArguments().getInt(RANK_TYPE, 0);
        mTablayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.vp_rank);
        toolbarFl = view.findViewById(R.id.fl_toolbar);

        if (rankType == RANK_TYPE_ROOM) {
            toolbarFl.setSelected(true);
            mTablayout.setSelected(true);
        }
//        mFragmentList.add(RankRoomListFragment.newInstance(rankType, TIME_TYPE_DAY));
        mFragmentList.add(RankRoomListFragment.newInstance(rankType, TIME_TYPE_WEEK));
        mFragmentList.add(RankRoomListFragment.newInstance(rankType, TIME_TYPE_ALL));
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
        mTablayout.setViewPager(mViewPager,new String[]{"周榜","月榜"});
        if (rankType == RANK_TYPE_ML) {
            toolbarFl.setSelected(false);
        } else {
            toolbarFl.setSelected(true);
        }
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
            return tabNames[position];
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

}
