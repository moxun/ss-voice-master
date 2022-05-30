package com.miaomi.fenbei.room.ui.dialog.zs;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.widget.ZSRankIndicator;
import com.miaomi.fenbei.room.R;

import java.util.ArrayList;
import java.util.List;

public class ZSRankDialog extends BaseBottomDialog {

    private ViewPager mViewPager;
    private ZSRankIndicator indicator;


    @Override
    public int getLayoutRes() {
        return R.layout.room_dialog_zs_rank;
    }

    @Override
    public void bindView(View v) {
        indicator = v.findViewById(R.id.title_tab);
        v.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mViewPager = v.findViewById(R.id.content_vp);
        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(ZSRankFragment.newInstance(1));
        mFragmentList.add(ZSRankFragment.newInstance(2));
        mFragmentList.add(ZSRankFragment.newInstance(3));
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(),mFragmentList));
        indicator.setViewPager(mViewPager);
    }
}
