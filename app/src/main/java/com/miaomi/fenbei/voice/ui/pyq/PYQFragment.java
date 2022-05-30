package com.miaomi.fenbei.voice.ui.pyq;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.widget.TMFindIndicator;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.ExpressRecordActivity;
import com.miaomi.fenbei.voice.ui.HDCenterListActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PYQFragment extends BaseFragment {

    private TMFindIndicator tmFindIndicator;
    private ViewPager viewPager;
    private List<Fragment> mTabList = new ArrayList<>();
    private TextView putMsgIv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pyq;
    }

    @Override
    public void initView(@NotNull View view) {
//        ExpressRecordActivity.start(getActivity())
//        StatusBarUtil.setStatusBarTextColor(getContext(),true);
        tmFindIndicator = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.vp_find);
        putMsgIv = view.findViewById(R.id.iv_put_msg);
        mTabList.add(FindChildFragment.newInstance(FindChildFragment.FIND_ITEM_TYPE_TJ));
        mTabList.add(FindChildFragment.newInstance(FindChildFragment.FIND_ITEM_TYPE_GZ));
//        mTabList.add(FindChildFragment.newInstance(FindChildFragment.FIND_ITEM_TYPE_FJ));
        viewPager.setOffscreenPageLimit(mTabList.size());
        viewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), mTabList));
        tmFindIndicator.setViewPager(viewPager);
        putMsgIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishPyqActivity.start(getContext());
            }
        });
        view.findViewById(R.id.iv_find_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HDCenterListActivity.start(getActivity());
            }
        });
        view.findViewById(R.id.iv_find_exprees).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpressRecordActivity.start(getActivity());
            }
        });
        viewPager.setCurrentItem(0);
    }
}
