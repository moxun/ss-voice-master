package com.miaomi.fenbei.gift.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.miaomi.fenbei.gift.fragment.ChestGiftFragment;
import com.miaomi.fenbei.gift.fragment.CommonGiftFragment;
import com.miaomi.fenbei.gift.fragment.ExpressGiftFragment;
import com.miaomi.fenbei.gift.fragment.NobleGiftFragment;
import com.miaomi.fenbei.gift.fragment.PackGiftFragment;


public class GiftViewPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private int size;
    public GiftViewPagerAdapter(FragmentManager fm,int size) {
        super(fm);
        this.fm = fm;
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return CommonGiftFragment.newInstance();
        }
        if (position == 1){
            return ChestGiftFragment.newInstance();
        }
        if (position == 2){
            return PackGiftFragment.newInstance();
        }
        return CommonGiftFragment.newInstance();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public int getCount() {
        return size;
    }
}
