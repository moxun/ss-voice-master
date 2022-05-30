package com.miaomi.fenbei.voice.ui.main.fragment;

import android.view.View;

import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.miaomi.fenbei.base.core.BaseFragment;

import com.miaomi.fenbei.voice.R;

import com.miaomi.fenbei.voice.ui.RankActivity;
import com.miaomi.fenbei.voice.ui.search.SearchActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class RoomFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_room;
    }

    @Override
    public void initView(@NotNull View view) {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(PartyRoomListFragment.newInstance());
        ViewPager mViewPager = view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), mFragmentList));
//        view.findViewById(R.id.iv_ranking).setOnClickListener(v -> RankActivity.start(getContext()));
        view.findViewById(R.id.tv_search).setOnClickListener(v -> SearchActivity.start(getContext()));
    }
}
