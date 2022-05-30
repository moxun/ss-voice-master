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

public class ZSGiftDialog extends BaseBottomDialog {

    private ViewPager mViewPager;
    private ZSRankIndicator zsRankIndicator;

    @Override
    public int getLayoutRes() {
        return R.layout.room_dialog_zs_gift;
    }

    @Override
    public void bindView(View v) {
        v.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        zsRankIndicator = v.findViewById(R.id.title_tab);
        mViewPager = v.findViewById(R.id.content_vp);
        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(ZSGiftFragment.newInstance(0));
        mFragmentList.add(ZSGiftFragment.newInstance(1));
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(),mFragmentList));
        zsRankIndicator.setViewPager(mViewPager,new String[]{"普通礼物","幸运礼物"});
    }
}
