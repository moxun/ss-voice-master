package com.miaomi.fenbei.room.ui.dialog;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.widget.FBFansIndicator;
import com.miaomi.fenbei.room.R;

import java.util.ArrayList;
import java.util.List;

public class JoinTheFanGroupDialog extends BaseBottomDialog {
    private FBFansIndicator fbFansIndicator;
    private ViewPager viewPager;
    private List<Fragment> mList = new ArrayList<>();
    public final static int JOIN_FANS_TYPE_LR = 1;
    public final static int JOIN_FANS_TYPE_XD = 2;
    public final static int JOIN_FANS_TYPE_ZA = 3;
    public final static int JOIN_FANS_TYPE_TG = 4;
    @Override
    public int getLayoutRes() {
        return R.layout.dialog_join_fans_grounp;
    }

    @Override
    public void bindView(View v) {
        mList.add(new JoinTheFanGroupFragment(JOIN_FANS_TYPE_TG));
        mList.add(new JoinTheFanGroupFragment(JOIN_FANS_TYPE_ZA));
        mList.add(new JoinTheFanGroupFragment(JOIN_FANS_TYPE_XD));
        mList.add(new JoinTheFanGroupFragment(JOIN_FANS_TYPE_LR));
        fbFansIndicator = v.findViewById(R.id.fans_indicator);
        viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(),mList));
        fbFansIndicator.setViewPager(viewPager);
    }
}
