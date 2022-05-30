package com.miaomi.fenbei.voice.ui.mine.user_homepage.adapter;

import com.miaomi.fenbei.base.bean.PreviewBean;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.fragment.BigFragment;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ImgPagerAdapter extends FragmentPagerAdapter {

    private List<PreviewBean> mimgUrlList;

    public ImgPagerAdapter(FragmentManager fm, List<PreviewBean> mimgUrlList) {
        super(fm);
        this.mimgUrlList = mimgUrlList;
    }

    @Override
    public Fragment getItem(int position) {
        return BigFragment.createFragment(mimgUrlList.get(position));
    }

    @Override
    public int getCount() {
        return mimgUrlList.size();
    }
}
