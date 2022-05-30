package com.miaomi.fenbei.room.ui.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.base.widget.FBFansIndicator;
import com.miaomi.fenbei.room.R;

import java.util.ArrayList;
import java.util.List;

public class RadioAnchorRankFansDialog extends BaseBottomDialog {
    private FBFansIndicator fbFansIndicator;
    private ViewPager viewPager;
    private List<Fragment> mList = new ArrayList<>();
    private String[] tabNames = new String[]{"热力总榜", "热力周榜","粉丝榜 "};
    private String anchorId;
    private ImageView fansRuleIv;

    public RadioAnchorRankFansDialog(String anchorId) {
        this.anchorId = anchorId;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_radio_anchor_fans;
    }

    @Override
    public void bindView(View v) {
        mList.add(new RadioAnchorRankFragment(RadioAnchorRankFragment.RANK_FANS_TYPE_ALL,anchorId));
        mList.add(new RadioAnchorRankFragment(RadioAnchorRankFragment.RANK_FANS_TYPE_Z,anchorId));
        mList.add(new RadioAnchorRankFragment(RadioAnchorRankFragment.RANK_FANS_TYPE_R,anchorId));
        fbFansIndicator = v.findViewById(R.id.fans_indicator);
        viewPager = v.findViewById(R.id.view_pager);
        fansRuleIv = v.findViewById(R.id.iv_fans_rule);
        viewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(),mList));
        fbFansIndicator.setViewPager(viewPager,tabNames,"#99FFFFFF","#FFFFFF");
        fansRuleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(getContext(),"http://decibel-web.cnciyin.com/fensrule","粉丝说明");
            }
        });
    }
}
