package com.miaomi.fenbei.room.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.widget.KMRankIndicator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class RoomRankTypeFragment extends BaseFragment {

    private ViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private KMRankIndicator mTablayout;
    private FrameLayout toolbarFl;
//    private RoomRankListFragment mlRankListFragment;
//    private RoomRankListFragment gxRankListFragment;
    private String[] tabGXNames = new String[]{"日榜", "周榜","月榜"};
    private String[] tabMLNames = new String[]{"昨日","上周","日榜", "周榜","月榜"};
    public final static String RANK_TYPE = "rank_type";
    public final static int RANK_TYPE_ML = 0;
    public final static int RANK_TYPE_GX = 1;
    public final static int TIME_TYPE_DAY = 0;
    public final static int TIME_TYPE_WEEK = 1;
    public final static int TIME_TYPE_ALL = 2;
    public final static int TIME_TYPE_PRE_DAY = 3;
    public final static int TIME_TYPE_PRE_WEEK = 4;
    private int rankType;

    public static RoomRankTypeFragment newInstance(int type){
        RoomRankTypeFragment fragment = new RoomRankTypeFragment();
        Bundle args = new Bundle();
        args.putInt(RANK_TYPE,type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.room_fragment_rank_type;
    }

    @Override
    public void initView(@NotNull View view) {
        rankType = getArguments().getInt(RANK_TYPE,0);
        mTablayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.vp_rank);
        toolbarFl = view.findViewById(R.id.fl_toolbar);
        if (rankType == RANK_TYPE_ML){
            toolbarFl.setSelected(false);
            mTablayout.setSelected(false);
            mFragmentList.add(RoomRankListFragment.newInstance(rankType,TIME_TYPE_PRE_DAY));
            mFragmentList.add(RoomRankListFragment.newInstance(rankType,TIME_TYPE_PRE_WEEK));
            mFragmentList.add(RoomRankListFragment.newInstance(rankType,TIME_TYPE_DAY));
            mFragmentList.add(RoomRankListFragment.newInstance(rankType,TIME_TYPE_WEEK));
            mFragmentList.add(RoomRankListFragment.newInstance(rankType,TIME_TYPE_ALL));
            mViewPager.setOffscreenPageLimit(mFragmentList.size());
            mTablayout.setViewPager(mViewPager,tabMLNames,"");
            mViewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
            mViewPager.setCurrentItem(2,true);
        }
        if (rankType == RANK_TYPE_GX){
            toolbarFl.setSelected(true);
            mTablayout.setSelected(true);
            mFragmentList.add(RoomRankListFragment.newInstance(rankType,TIME_TYPE_DAY));
            mFragmentList.add(RoomRankListFragment.newInstance(rankType,TIME_TYPE_WEEK));
            mFragmentList.add(RoomRankListFragment.newInstance(rankType,TIME_TYPE_ALL));
            mTablayout.setViewPager(mViewPager,tabGXNames,"");
            mViewPager.setOffscreenPageLimit(mFragmentList.size());
            mViewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
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
            return "";
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

}
