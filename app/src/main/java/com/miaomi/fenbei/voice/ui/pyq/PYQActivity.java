package com.miaomi.fenbei.voice.ui.pyq;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.util.StatusBarUtil;
import com.miaomi.fenbei.base.widget.TMFindIndicator;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class PYQActivity extends BaseActivity {

    private TMFindIndicator tmFindIndicator;
    private ViewPager viewPager;
    private List<Fragment> mTabList = new ArrayList<>();
    private ImageView putMsgIv;

    public static void start(Activity context) {
        Intent intent = new Intent(context, PYQActivity.class);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_pyq;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarTextColor(this,true);
        tmFindIndicator = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.vp_find);
        putMsgIv = findViewById(R.id.iv_put_msg);
        mTabList.add(FindChildFragment.newInstance(FindChildFragment.FIND_ITEM_TYPE_GZ));
        mTabList.add(FindChildFragment.newInstance(FindChildFragment.FIND_ITEM_TYPE_TJ));
//        mTabList.add(FindChildFragment.newInstance(FindChildFragment.FIND_ITEM_TYPE_FJ));
        viewPager.setOffscreenPageLimit(mTabList.size());
        viewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), mTabList));
        tmFindIndicator.setViewPager(viewPager);
        putMsgIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishPyqActivity.start(PYQActivity.this);
            }
        });
        viewPager.setCurrentItem(1);
    }
}
